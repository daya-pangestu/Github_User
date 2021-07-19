package com.daya.githubuser.favorite.presentation

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.daya.core.data.Resource
import com.daya.core.di.dfm.FavoriteModuleDependencies
import com.daya.githubuser.R
import com.daya.githubuser.favorite.databinding.ActivityFavoriteBinding
import com.daya.githubuser.favorite.di.DaggerFavoriteComponent
import com.daya.githubuser.favorite.presentation.factory.ViewModelFactory
import com.daya.githubuser.presentation.detail.DetailActivity.Companion.KEY_USER_EXTRA
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteBinding

    private lateinit var skeleton : Skeleton

    @Inject
    lateinit var factory : ViewModelFactory

    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    this@FavoriteActivity,
                    FavoriteModuleDependencies::class.java
                )
            ).build()
            .inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply{
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.favorite)
        }

        val userAdapter = UserProfileAdapter {bio ->
            val intent = Intent(this,Class.forName("com.daya.githubuser.presentation.detail.DetailActivity")).also {
                it.putExtra(KEY_USER_EXTRA,bio)
            }
            startActivity(intent)
        }

        binding.rvUsersFav.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    this@FavoriteActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        favoriteViewModel.getListFavoriteLiveData.observe(this, {
            when (it) {
                is Resource.Loading -> {
                    if (::skeleton.isInitialized) {
                        skeleton.showSkeleton()
                    } else {
                        skeleton = binding.rvUsersFav.applySkeleton(R.layout.item_user).apply {
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
                    Toast.makeText(this,it.exceptionMessage.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else ->super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}