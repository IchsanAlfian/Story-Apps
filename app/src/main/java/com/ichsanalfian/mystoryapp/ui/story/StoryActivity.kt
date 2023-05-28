package com.ichsanalfian.mystoryapp.ui.story

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
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
import com.ichsanalfian.mystoryapp.paging.LoadingStateAdapter
import com.ichsanalfian.mystoryapp.ui.addStory.AddStoryActivity
import com.ichsanalfian.mystoryapp.ui.welcome.WelcomeActivity
import com.ichsanalfian.mystoryapp.utils.ViewModelFactory

@Suppress("DEPRECATION")
class StoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryBinding
    private lateinit var factory: ViewModelFactory
    private lateinit var adapter: StoryAdapter

    private val storyViewModel: StoryViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModelAndAdapter()
        setupView()
        setTitle(adapter.itemCount.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_story, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupView() {
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
            R.id.button_setting -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }

            R.id.action_logout -> {
                storyViewModel.userLogout()
                Toast.makeText(this, getString(R.string.msg_logout), Toast.LENGTH_SHORT).show()
                true
            }
            else -> true
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.msg_exit))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.msg_yes)) { _, _ -> super.onBackPressed()
                finishAffinity()  }
            .setNegativeButton(getString(R.string.msg_no), null)
            .show()
    }

    private fun setupViewModelAndAdapter() {
        factory = ViewModelFactory.getInstance(this)
        storyViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        adapter = StoryAdapter()
        binding.rvStory.layoutManager = LinearLayoutManager(this@StoryActivity)
        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter{
                adapter.retry()
            }
        )
        storyViewModel.getUser().observe(this@StoryActivity) { data ->
            if (data.isLogin) {
                storyViewModel.getAllStory.observe(this@StoryActivity) {
                    adapter.submitData(lifecycle, it)

                }
            } else {
                startActivity(Intent(this@StoryActivity, WelcomeActivity::class.java))
                finish()
            }
        }
        storyViewModel.story.observe(this@StoryActivity) {
            if (it == null) {
                Toast.makeText(this, getString(R.string.mgs_dataNull), Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.storyProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}