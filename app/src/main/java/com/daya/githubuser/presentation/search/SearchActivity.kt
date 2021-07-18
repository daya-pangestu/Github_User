package com.daya.githubuser.presentation.search

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daya.githubuser.R
import com.daya.core.data.Resource
import com.daya.core.utils.toast
import com.daya.githubuser.databinding.ActivitySearchBinding
import com.daya.githubuser.presentation.detail.DetailActivity
import com.daya.githubuser.presentation.detail.DetailActivity.Companion.KEY_USER_EXTRA
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.paulrybitskyi.persistentsearchview.utils.KeyboardManagingUtil.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySearchBinding

    private val searchViewModel: SearchViewModel by viewModels()

    private val linearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    private lateinit var skeleton : Skeleton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val suggestionAdapter = SuggestionAdapter{ bio ->
            val intentToDetail = Intent(this, DetailActivity::class.java)
            intentToDetail.putExtra(KEY_USER_EXTRA,bio)
            startActivity(intentToDetail)
        }

        binding.apply {
            searchView.setOnLeftBtnClickListener {
                onBackPressed()
            }

            searchView.isVoiceInputButtonEnabled = false
            searchView.setOnClearInputBtnClickListener {
                searchViewModel.submitQuery("")

            }
            searchView.setOnSearchConfirmedListener { _, query ->
                searchViewModel.submitQuery(query)
                hideKeyboard(binding.root)
            }

            searchView.setOnLeftBtnClickListener {
                onBackPressed()
            }

            searchView.setOnSearchQueryChangeListener { _, _, newQuery ->
                searchViewModel.submitQuery(newQuery)
            }

            rvSuggestion.adapter = suggestionAdapter
            rvSuggestion.layoutManager = linearLayoutManager
            rvSuggestion.setHasFixedSize(true)
            rvSuggestion.addItemDecoration(
                DividerItemDecoration(
                    rvSuggestion.context,
                    DividerItemDecoration.VERTICAL
                )
            )
            rvSuggestion.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    when (newState) {
                        RecyclerView.SCROLL_STATE_DRAGGING, RecyclerView.SCROLL_STATE_SETTLING -> {
                            hideKeyboard(binding.root)
                        }
                    }
                }
            })
        }

        searchViewModel.observerSearchResult().observe(this, {
            when (it) {
                is Resource.Loading -> {
                    if (::skeleton.isInitialized) {
                        skeleton.showSkeleton()
                    } else {
                        skeleton = binding.rvSuggestion.applySkeleton(R.layout.item_search,10).apply {
                            showSkeleton()
                        }
                    }
                }
                is Resource.Success -> {
                    lifecycleScope.launch {
                        delay(500)
                        if (::skeleton.isInitialized) skeleton.showOriginal()
                        suggestionAdapter.submitList(it.data)
                    }
                }
                is Resource.Error -> {
                    searchViewModel.submitQuery("") // clear it first, there is a bug, if text didn't change and user tap submit, it wont re-trigger search
                    if (::skeleton.isInitialized) skeleton.showOriginal()
                    toast(it.exceptionMessage.toString())
                }
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}