package com.daya.githubuser.ui.main

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
import com.daya.githubuser.data.Resource
import com.daya.githubuser.databinding.FragmentForYouBinding
import com.daya.githubuser.ui.detail.DetailActivity
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * this fragment used for "for you" and "favorite"
* */
class ForYouFragment : Fragment() {

    private var _binding : FragmentForYouBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by activityViewModels<MainViewModel>()

    private lateinit var skeleton : Skeleton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentForYouBinding.inflate(inflater, container, false)
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
            rvUsers.adapter = userAdapter
            rvUsers.layoutManager = LinearLayoutManager(context)
            rvUsers.setHasFixedSize(true)
            rvUsers.addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        mainViewModel.getListBioLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    if (::skeleton.isInitialized) {
                        skeleton.showSkeleton()
                    } else {
                        skeleton = binding.rvUsers.applySkeleton(R.layout.item_user).apply {
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