package com.ichsanalfian.mystoryapp.ui.login

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
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.ichsanalfian.mystoryapp.R
import com.ichsanalfian.mystoryapp.WelcomeActivity
import com.ichsanalfian.mystoryapp.ui.main.MainActivity
import com.ichsanalfian.mystoryapp.databinding.ActivityLoginBinding
import com.ichsanalfian.mystoryapp.model.UserModel
import com.ichsanalfian.mystoryapp.model.UserPreference
import com.ichsanalfian.mystoryapp.ui.register.RegisterActivity
import com.ichsanalfian.mystoryapp.ui.story.StoryActivity
import com.ichsanalfian.mystoryapp.utils.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels { factory }
    private lateinit var binding: ActivityLoginBinding
    private lateinit var user: UserModel
    private lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
        playAnimation()
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
//        loginViewModel = ViewModelProvider(
//            this,
//            ViewModelFactory(UserPreference.getInstance(dataStore))
//        )[LoginViewModel::class.java]
//
//        loginViewModel.getUser().observe(this, { user ->
//            this.user = user
//        })
        factory = ViewModelFactory.getInstance(this)
    }

    //    private fun setupAction() {
//        binding.loginButton.setOnClickListener {
//            val email = binding.edLoginEmail.text.toString()
//            val password = binding.edLoginPassword.text.toString()
//            when {
//                email.isEmpty() -> {
//                    binding.emailEditTextLayout.error = "Masukkan email"
//                }
//                password.isEmpty() -> {
//                    binding.passwordEditTextLayout.error = "Masukkan password"
//                }
////                email != user.email -> {
////                    binding.emailEditTextLayout.error = "Email tidak sesuai"
////                }
////                password != user.password -> {
////                    binding.passwordEditTextLayout.error = "Password tidak sesuai"
////                }
//                else -> {
//                    postText()
//                    loginViewModel.login()
//                    AlertDialog.Builder(this).apply {
//                        setTitle("Yeah!")
//                        setMessage("Anda berhasil login. Sudah tidak sabar untuk belajar ya?")
//                        setPositiveButton("Lanjut") { _, _ ->
//                            val intent = Intent(context, StoryActivity::class.java)
//                            intent.flags =
//                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                            startActivity(intent)
//                            finish()
//                        }
//                        create()
//                        show()
//                    }
//                    moveActivity()
//                }
//            }
//        }
//    }
    //edittttt
    private fun setupAction() {
        binding.apply {
            loginButton.setOnClickListener {
                if (edLoginEmail.length() == 0 && edLoginPassword.length() == 0) {
//                    edLoginEmail.error = getString(R.string.required_field)
//                    edtPassword.setError(getString(R.string.required_field), null)
                } else if (edLoginEmail.length() != 0 && edLoginPassword.length() != 0) {
                    postText()

                    loginViewModel.login()
                    moveActivity()
                }
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val message =
            ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(500)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                title,
                message,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login
            )
            startDelay = 500
        }.start()
    }

    private fun moveActivity() {
        loginViewModel.login.observe(this@LoginActivity) { response ->
            if (!response.error) {
                startActivity(Intent(this@LoginActivity, StoryActivity::class.java))
                finish()
            }
        }
    }

    private fun postText() {
        binding.apply {
            loginViewModel.postLogin(
                edLoginEmail.text.toString(),
                edLoginPassword.text.toString()
            )
        }
        loginViewModel.login.observe(this@LoginActivity) { response ->
            saveSession(
                UserModel(
                    response.loginResult?.name.toString(),
                    true,
                    AUTH_KEY + (response.loginResult?.token.toString())
                )
            )
        }
    }


    private fun saveSession(session: UserModel) {
        loginViewModel.saveSession(session)
    }

    companion object {
        private const val AUTH_KEY = "Bearer "
    }


}