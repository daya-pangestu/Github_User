package com.daya.githubuser.presentation.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.daya.core.data.Resource
import com.daya.core.domain.model.GeneralBio
import com.daya.core.utils.avatarForMainApp
import com.daya.core.utils.isValidUrl
import com.daya.core.utils.toast
import com.daya.githubuser.R
import com.daya.githubuser.databinding.ActivityDetailBinding
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.createSkeleton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel : DetailViewModel by viewModels ()

    private val skeleton : Skeleton by lazy { binding.root.createSkeleton() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onBackPressedDispatcher.addCallback(this){ finish() }
        val bio = intent.getParcelableExtra<GeneralBio>(KEY_USER_EXTRA)
        detailViewModel.setBio(bio)

        supportActionBar?.title = bio?.username ?: getString(R.string.detail_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        detailViewModel.getDetailBioLiveData.observe(this) { resBio ->
            when (resBio) {
                is Resource.Loading -> skeleton.showSkeleton()
                is Resource.Success -> {
                    lifecycleScope.launch {
                        delay(500)
                        skeleton.showOriginal()
                        val nonNullBio = resBio.data
                        bindLayout(nonNullBio)
                        invalidateOptionsMenu()
                    }
                }

                is Resource.Error -> {
                    skeleton.showOriginal()
                    toast(resBio.exceptionMessage.toString())
                }
            }
        }

        detailViewModel.isUserFavLiveData().observe(this) { invalidateOptionsMenu() }
    }

    private fun bindLayout(generalBio: GeneralBio) {
        val avatar = if (generalBio.avatar.isValidUrl()) generalBio.avatar else generalBio.avatar.avatarForMainApp()
        Glide.with(this@DetailActivity)
            .load(avatar)
            .dontAnimate()
            .into(binding.profileImage)

        binding.tvName.text = generalBio.name
        binding.tvUserName.text = generalBio.username
        binding.tvCompany.text = generalBio.company
        binding.tvLocation.text = generalBio.location
        binding.tvFollowersCount.text = generalBio.followerCount.toString()
        binding.tvFollowingCount.text = generalBio.followingCount.toString()
        binding.tvRepoCount.text = generalBio.repoCount.toString()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu,menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            val itemFavorite = it.findItem(R.id.detail_add_favorite)
            val itemUnFavorite = it.findItem(R.id.detail_un_favorite)
            val isFavorite = detailViewModel.isUserFavLiveData().value ?: false
            itemFavorite.isVisible = isFavorite
            itemUnFavorite.isVisible = !isFavorite
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            R.id.detail_add_favorite -> {
                detailViewModel.removeUserFromFavorite()
                true
            }
            R.id.detail_un_favorite -> {
                detailViewModel.addUserToFavorite()
                true
            }
            else ->super.onOptionsItemSelected(item)
        }
    }

    companion object{
        const val KEY_USER_EXTRA = "key_user_extra"
    }

}