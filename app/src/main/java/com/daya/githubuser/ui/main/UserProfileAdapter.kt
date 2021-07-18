package com.daya.githubuser.ui.main

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daya.githubuser.R
import com.daya.githubuser.data.profile.general.GeneralBio
import com.daya.githubuser.databinding.ItemUserBinding
import com.daya.githubuser.xtension.avatarForMainApp
import com.daya.githubuser.xtension.isValidUrl
import com.daya.githubuser.xtension.trimLocationName

open class UserProfileAdapter(private val itemClick : (GeneralBio) -> Unit) : ListAdapter<GeneralBio, UserProfileAdapter.UserProfileViewHolder>(userBioDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserProfileViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserProfileViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,itemClick)
    }

    inner class UserProfileViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GeneralBio, itemClick: (GeneralBio) ->Unit) {
            val avatar = if (item.avatar.isValidUrl()) item.avatar else item.avatar.avatarForMainApp()

            Glide.with(itemView)
                    .load(avatar)
                    .dontAnimate()
                    .placeholder(android.R.color.darker_gray)
                    .into(binding.profileImage)

            binding.tvUserName.text = item.username
            binding.tvName.text = item.name
            binding.tvLocation.text = item.location.trimLocationName()
            binding.tvRepo.text = itemView.context.getString(R.string.repo,item.repoCount)

            val spanText = SpannableStringBuilder()
                .append("following: ")
                .bold { append("${item.followingCount}") }
                .append(", follower: ")
                .bold { append("${item.followerCount}") }

            binding.tvFollow.text = spanText

            itemView.setOnClickListener {
                itemClick(item)
            }
        }
    }
}

val userBioDiffUtil = object : DiffUtil.ItemCallback<GeneralBio>(){
    override fun areItemsTheSame(oldItem: GeneralBio, newItem: GeneralBio)= oldItem.username == newItem.username
    override fun areContentsTheSame(oldItem: GeneralBio, newItem: GeneralBio): Boolean = oldItem == newItem
}
