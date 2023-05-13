package com.ichsanalfian.mystoryapp.ui.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.ichsanalfian.mystoryapp.ui.welcome.WelcomeActivity
import com.ichsanalfian.mystoryapp.databinding.ActivityMainBinding
import com.ichsanalfian.mystoryapp.utils.ViewModelFactory


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
//    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
//    private lateinit var factory: ViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val factory = ViewModelFactory.getInstance(this)
        val mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        setupView()
        setupViewModel()
        setupAction()
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
//        mainViewModel = ViewModelProvider(
//            this,
//            ViewModelFactory(UserPreference.getInstance(dataStore))
//        )[MainViewModel::class.java]

//        mainViewModel.getUser().observe(this, { user ->
//            if (user.isLogin){
//                binding.nameTextView.text = getString(R.string.greeting, user.name)
//            } else {
//                startActivity(Intent(this, WelcomeActivity::class.java))
//                finish()
//            }
//        })
//        factory = ViewModelFactory.getInstance(this)
        startActivity(Intent(this, WelcomeActivity::class.java))
        finish()
    }

    private fun setupAction() {
//        binding.logoutButton.setOnClickListener {
//            mainViewModel.logout()
//        }
    }

}