package com.ichsanalfian.mystoryapp.ui.story

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ichsanalfian.mystoryapp.R
import com.ichsanalfian.mystoryapp.databinding.ActivityStoryBinding
import com.ichsanalfian.mystoryapp.ui.addStory.AddStoryActivity
import com.ichsanalfian.mystoryapp.ui.login.LoginActivity
import com.ichsanalfian.mystoryapp.utils.ViewModelFactory

@Suppress("DEPRECATION")
class StoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryBinding
    private lateinit var factory: ViewModelFactory
    private val storyViewModel: StoryViewModel by viewModels { factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        setupViewModelAndAdapter()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_story, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupView() {
        supportActionBar?.title = "My Story App"
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
                finish()
                true
            }
            R.id.action_logout -> {
                storyViewModel.userLogout()
                Toast.makeText(this, "Anda telah Logout", Toast.LENGTH_SHORT).show()
                true
            }
            else -> true
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage("Are you sure you want to exit?")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ -> super.onBackPressed() }
            .setNegativeButton("No", null)
            .show()
    }

    private fun setupViewModelAndAdapter() {
        factory = ViewModelFactory.getInstance(this)
        storyViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        storyViewModel.getUser().observe(this@StoryActivity) { data ->
            if (data.isLogin) {
                storyViewModel.getAllStory(data.token)
            } else {
                startActivity(Intent(this@StoryActivity, LoginActivity::class.java))
                finish()
            }
        }
        storyViewModel.story.observe(this@StoryActivity) {
            if (it == null) {
                Toast.makeText(this, "Data Kosong", Toast.LENGTH_SHORT).show()
            } else {
                binding.rvStory.adapter = StoryAdapter(it.listStory)
            }
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.storyProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}