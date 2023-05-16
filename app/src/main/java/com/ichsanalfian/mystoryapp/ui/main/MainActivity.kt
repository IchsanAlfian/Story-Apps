package com.ichsanalfian.mystoryapp.ui.main

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import com.ichsanalfian.mystoryapp.ui.welcome.WelcomeActivity
import com.ichsanalfian.mystoryapp.databinding.ActivityMainBinding
import com.ichsanalfian.mystoryapp.ui.story.StoryActivity
import com.ichsanalfian.mystoryapp.utils.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var factory: ViewModelFactory
    private val mainViewModel: MainViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupViewModel()
    }
    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)
        mainViewModel.getUser().observe(this@MainActivity) { data ->
            if (data.isLogin) {
                startActivity(Intent(this@MainActivity, StoryActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@MainActivity, WelcomeActivity::class.java))
                finish()
            }
        }
    }
}