package com.ichsanalfian.mystoryapp.ui.story

import android.content.Intent
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ichsanalfian.mystoryapp.databinding.ItemStoryBinding
import com.ichsanalfian.mystoryapp.response.ListStoryItem
import com.ichsanalfian.mystoryapp.ui.detailStory.DetailStoryActivity
import com.ichsanalfian.mystoryapp.ui.detailStory.DetailStoryActivity.Companion.EXTRA_DESC

import com.ichsanalfian.mystoryapp.ui.detailStory.DetailStoryActivity.Companion.EXTRA_NAME
import com.ichsanalfian.mystoryapp.ui.detailStory.DetailStoryActivity.Companion.EXTRA_PHOTO

class StoryAdapter(private val listStory: List<ListStoryItem>) :
    RecyclerView.Adapter<StoryAdapter.ViewHolder>() {
    interface OnItemClickCallback {
        fun onItemClicked(data: ListStoryItem)
    }

    private var onItemClickCallback: OnItemClickCallback? = null
//    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
//        this.onItemClickCallback = onItemClickCallback
//    }

    inner class ViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(storyUser: ListStoryItem) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(storyUser)
            }
            binding.apply {
                tvItemName.text = storyUser.name
                Glide.with(itemView.context)
                    .load(storyUser.photoUrl)
                    .centerCrop()
                    .into(ivItemPhoto)
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailStoryActivity::class.java)
                intent.putExtra(EXTRA_NAME, storyUser.name)
                intent.putExtra(EXTRA_PHOTO, storyUser.photoUrl)
                intent.putExtra(EXTRA_DESC, storyUser.description)
                itemView.context.startActivity(intent)
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