package com.ichsanalfian.mystoryapp.ui.story

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ichsanalfian.mystoryapp.databinding.ItemStoryBinding
import com.ichsanalfian.mystoryapp.response.ListStoryItem

class StoryAdapter(private val listStory: List<ListStoryItem>) :
    RecyclerView.Adapter<StoryAdapter.ViewHolder>() {
    interface OnItemClickCallback {
        fun onItemClicked(data: ListStoryItem)
    }

    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListStoryItem) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(story)
            }
            binding.apply {
                tvItemName.text = story.name
                Glide.with(itemView.context)
                    .load(story.photoUrl)
                    .centerCrop()
                    .into(ivItemPhoto)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val userBinding =
            ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(userBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(listStory[position])

    }

    override fun getItemCount() = listStory.size
}