package com.daya.githubuser.presentation.detail.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.daya.core.domain.model.FollowersFollowing
import com.daya.githubuser.databinding.FragmentFollowerFollowingBinding

class FollowerFollowingFragment : Fragment() {
    private var _binding : FragmentFollowerFollowingBinding? = null
    private val binding get() = _binding!!

    companion object{
        const val KEY_EXTRA_LIST_FOLLOW = "key_extra_list_follow"
    }


    private val linearLayoutManager by lazy {
        LinearLayoutManager(context)
    }

     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowerFollowingBinding.inflate(inflater,container,false)
         return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    val listFollowersFollowing : List<FollowersFollowing> = arguments?.getParcelableArrayList(KEY_EXTRA_LIST_FOLLOW) ?: emptyList()
        val followAdapter = FollowRvAdapter()
        followAdapter.submitList(listFollowersFollowing)

        binding.apply {
            rvFollow.adapter = followAdapter
            rvFollow.layoutManager = linearLayoutManager
            rvFollow.setHasFixedSize(true)
            rvFollow.addItemDecoration(
                    DividerItemDecoration(
                            rvFollow.context,
                            DividerItemDecoration.VERTICAL
                    )
            )
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

  }