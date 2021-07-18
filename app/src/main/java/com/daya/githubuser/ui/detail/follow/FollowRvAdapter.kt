package com.daya.githubuser.ui.detail.follow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daya.githubuser.data.profile.general.FollowersFollowing
import com.daya.githubuser.databinding.ItemFollowBinding


class FollowRvAdapter : ListAdapter<FollowersFollowing, FollowRvAdapter.FollowRvHolder>(followDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowRvHolder {
        val binding = ItemFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowRvHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowRvHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class FollowRvHolder(private val binding: ItemFollowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FollowersFollowing) {
            binding.tvUserName.text = item.followUsername
            Glide.with(itemView)
                    .load(item.avatarUrl)
                    .dontAnimate()
                    .into(binding.profileImage)
        }
    }
}

val followDiffUtil = object : DiffUtil.ItemCallback<FollowersFollowing>() {
    override fun areItemsTheSame(oldItem: FollowersFollowing, newItem: FollowersFollowing) = oldItem.followUsername == newItem.followUsername
    override fun areContentsTheSame(oldItem: FollowersFollowing, newItem: FollowersFollowing) = oldItem == newItem

}