package com.daya.githubuser.presentation.detail.follow

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.daya.githubuser.domain.model.FollowersFollowing

class FollowFragmentStateAdapter(
    activity: FragmentActivity,
    private val followersUrl: String,
    private val followingUrl: String,
    private val listFollowers: List<FollowersFollowing>,
    private val listFollowings: List<FollowersFollowing>
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FollowerFollowingFragment.newInstance(followerUrl = followersUrl, listFollow = listFollowers)
            else -> FollowerFollowingFragment.newInstance(followingUrl =followingUrl, listFollow = listFollowings)
        }
    }
}