package com.daya.githubuser.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ca.allanwang.kau.utils.toast
import com.daya.githubuser.R
import com.daya.core.data.Resource
import com.daya.githubuser.databinding.FragmentFavoriteBinding
import com.daya.githubuser.presentation.detail.DetailActivity
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {
    private var _binding : FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by activityViewModels<MainViewModel>()

    private lateinit var skeleton : Skeleton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userAdapter = UserProfileAdapter{ bio ->
            val intentToDetail = Intent(activity, DetailActivity::class.java).also {
                it.putExtra(DetailActivity.KEY_USER_EXTRA, bio)
            }
            startActivity(intentToDetail)
        }

        binding.apply {
            rvFav.adapter = userAdapter
            rvFav.layoutManager = LinearLayoutManager(context)
            rvFav.setHasFixedSize(true)
            rvFav.addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        mainViewModel.getListFavoriteLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    if (::skeleton.isInitialized) {
                        skeleton.showSkeleton()
                    } else {
                        skeleton = binding.rvFav.applySkeleton(R.layout.item_user).apply {
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
                    context?.toast(it.exceptionMessage.toString(), Toast.LENGTH_SHORT)
                }
            }
        })

    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}