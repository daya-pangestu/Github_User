package com.daya.githubuser.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daya.core.domain.model.GeneralBio
import com.daya.githubuser.databinding.ItemSearchBinding
import com.daya.githubuser.presentation.main.userBioDiffUtil

class SuggestionAdapter(private val itemClick: (GeneralBio) -> Unit) : ListAdapter<GeneralBio,SuggestionAdapter.SuggestionViewHolder>(userBioDiffUtil){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SuggestionViewHolder(binding,itemClick)
    }

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class SuggestionViewHolder(
        private val binding: ItemSearchBinding,
        itemClick: (GeneralBio) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                itemClick(getItem(bindingAdapterPosition))
            }
        }

        fun bind(item: GeneralBio) {
            Glide.with(itemView)
                .load(item.avatar)
                .dontAnimate()
                .placeholder(android.R.color.darker_gray)
                .into(binding.profileImage)
            binding.tvUserName.text = item.username
        }
    }
}
