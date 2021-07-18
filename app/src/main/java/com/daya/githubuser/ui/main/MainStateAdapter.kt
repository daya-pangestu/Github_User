package com.daya.githubuser.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainStateAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int =2
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ForYouFragment()
            else -> FavoriteFragment()
        }
    }
}