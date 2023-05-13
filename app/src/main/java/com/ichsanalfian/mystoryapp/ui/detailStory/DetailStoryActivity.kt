package com.ichsanalfian.mystoryapp.ui.detailStory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.ichsanalfian.mystoryapp.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvDetailName.text = intent.getStringExtra(EXTRA_NAME)
        binding.detailDescription.text = intent.getStringExtra(EXTRA_DESC)
        Glide.with(this)
            .load(intent.getStringExtra(EXTRA_PHOTO))
            .centerCrop()
            .into(binding.ivDetailPhoto)

    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PHOTO = "extra_photo"
        const val EXTRA_DESC = "extra_desc"
    }
}