package com.ichsanalfian.mystoryapp.ui.story

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ichsanalfian.mystoryapp.R
import com.ichsanalfian.mystoryapp.WelcomeActivity
import com.ichsanalfian.mystoryapp.databinding.ActivityMainBinding
import com.ichsanalfian.mystoryapp.databinding.ActivityRegisterBinding
import com.ichsanalfian.mystoryapp.databinding.ActivityStoryBinding
import com.ichsanalfian.mystoryapp.ui.addStory.AddStoryActivity
import com.ichsanalfian.mystoryapp.ui.login.LoginActivity
import com.ichsanalfian.mystoryapp.ui.register.RegisterViewModel
import com.ichsanalfian.mystoryapp.utils.ViewModelFactory

class StoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryBinding
    private lateinit var factory: ViewModelFactory
    private var userToken = ""
    private val storyViewModel: StoryViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        setupViewModelAndAdapter()
//        setupAdapter()
//        setupAction()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_story, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupView() {
//        @Suppress("DEPRECATION")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        }
//        supportActionBar?.hide()
        supportActionBar?.title = "My Story App"

        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //edit coba coba
        val layoutManager = LinearLayoutManager(this)

        binding.rvStory.layoutManager = layoutManager
        binding.rvStory.setHasFixedSize(true)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvStory.addItemDecoration(itemDecoration)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.button_add -> {
                val intent = Intent(this, AddStoryActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_logout-> {
                storyViewModel.userLogout()
                Toast.makeText(this, "logout ini", Toast.LENGTH_SHORT).show()
                true
            }
            else -> true
        }
    }

    private fun setupViewModelAndAdapter() {
        factory = ViewModelFactory.getInstance(this)
        storyViewModel.getUser().observe(this@StoryActivity) { data ->
            if (data.isLogin) {
                storyViewModel.getAllStory(data.token)
            } else {
                startActivity(Intent(this@StoryActivity, LoginActivity::class.java))
                finish()
            }
        }
        storyViewModel.story.observe(this@StoryActivity) {
            //edit ifnya nanti
            binding.rvStory.adapter = StoryAdapter(it.listStory)
//            if (it != null) {
//                binding.rvStory.adapter = StoryAdapter(it.listStory)
//            }

        }

    }
//    private fun setupAdapter() {
//        storyViewModel.story.observe(this@StoryActivity) {
//            //edit ifnya nanti
//            binding.rvStory.adapter = StoryAdapter(it.listStory)
////            if (it != null) {
////                binding.rvStory.adapter = StoryAdapter(it.listStory)
////            }
//
//        }
//    }
}