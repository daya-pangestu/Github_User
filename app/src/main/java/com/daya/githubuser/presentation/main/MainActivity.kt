package com.daya.githubuser.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import ca.allanwang.kau.searchview.bindSearchView
import ca.allanwang.kau.utils.toast
import com.daya.core.data.Resource
import com.daya.githubuser.R
import com.daya.githubuser.databinding.ActivityMainBinding
import com.daya.githubuser.presentation.settings.SettingsActivity
import com.daya.githubuser.presentation.search.SearchActivity
import com.daya.core.utils.capitalized
import com.daya.githubuser.presentation.detail.DetailActivity
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var skeleton : Skeleton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userAdapter = UserProfileAdapter{ bio ->
            val intentToDetail = Intent(this, DetailActivity::class.java).also {
                it.putExtra(DetailActivity.KEY_USER_EXTRA, bio)
            }
            startActivity(intentToDetail)
        }

        binding.apply {
            rvUsers.adapter = userAdapter
            rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUsers.setHasFixedSize(true)
            rvUsers.addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )

            rvUsers.addOnScrollListener(object :RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) { // Scrolling up
                        binding.extendedFAB.shrink()
                    } else {// Scrolling down
                        binding.extendedFAB.extend()
                    }
                }
            })
        }

        mainViewModel.getListBioLiveData.observe(this, {
            when (it) {
                is Resource.Loading -> {
                    if (::skeleton.isInitialized) {
                        skeleton.showSkeleton()
                    } else {
                        skeleton = binding.rvUsers.applySkeleton(R.layout.item_user,10).apply {
                            showSkeleton()
                        }
                    }
                }
                is Resource.Success -> {
                    lifecycleScope.launch {
                        delay(500)
                        if (::skeleton.isInitialized) skeleton.showOriginal()
                        userAdapter.submitList(it.data)
                    }
                }
                is Resource.Error -> {
                    if (::skeleton.isInitialized) skeleton.showOriginal()
                    toast(it.exceptionMessage.toString(), Toast.LENGTH_SHORT)
                }
            }
        })

        binding.extendedFAB.apply {
            setOnClickListener {
                toast("fab cliked")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.main_menu_search -> {
                val intentToSearch = Intent(this, SearchActivity::class.java)
                startActivity(intentToSearch)

                true
            }
            R.id.main_menu_settings -> {
                val intentToSearch = Intent(this, SettingsActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                }
                startActivity(intentToSearch)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        binding.rvUsers.clearOnScrollListeners()
        super.onDestroy()
    }
}