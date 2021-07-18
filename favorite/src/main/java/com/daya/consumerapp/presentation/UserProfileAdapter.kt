package com.daya.consumerapp.presentation

import android.content.Context
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daya.consumerapp.R
import com.daya.consumerapp.data.general.GeneralBio
import com.daya.consumerapp.databinding.ItemUserBinding
import com.daya.consumerapp.utils.isValidUrl
import com.daya.consumerapp.utils.trimLocationName

open class UserProfileAdapter : ListAdapter<GeneralBio, UserProfileAdapter.UserProfileViewHolder>(userBioDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserProfileViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserProfileViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class UserProfileViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GeneralBio) {
            val avatar = if (item.avatar.isValidUrl()) item.avatar else item.avatar.avatarForConsumerApp(itemView.context)
            Glide.with(itemView)
                .load(avatar)
                .dontAnimate()
                .placeholder(android.R.color.darker_gray)
                .into(binding.profileImage)

            binding.tvUserName.text = item.username
            binding.tvName.text = item.name
            binding.tvLocation.text = item.location.trimLocationName()
            binding.tvRepo.text = itemView.context.getString(R.string.repo, item.repoCount)

            val spanText = SpannableStringBuilder()
                .append("following: ")
                .bold { append("${item.followingCount}") }
                .append(", follower: ")
                .bold { append("${item.followerCount}") }

            binding.tvFollow.text = spanText
        }
    }

    private fun String.avatarForConsumerApp(context: Context): Int {
        val array = this.split("-")
        val resDrawString = array.last()

        val sanitizeAvaName = resDrawString.removePrefix("@drawable/")
        return context.resources.getIdentifier(sanitizeAvaName, "drawable", context.packageName)
    }

}

val userBioDiffUtil = object : DiffUtil.ItemCallback<GeneralBio>(){
    override fun areItemsTheSame(oldItem: GeneralBio, newItem: GeneralBio)= oldItem.username == newItem.username
    override fun areContentsTheSame(oldItem: GeneralBio, newItem: GeneralBio): Boolean = oldItem == newItem
}
