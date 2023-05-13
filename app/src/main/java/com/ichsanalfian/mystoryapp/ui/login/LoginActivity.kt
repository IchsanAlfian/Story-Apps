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
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.ichsanalfian.mystoryapp.databinding.ActivityLoginBinding
import com.ichsanalfian.mystoryapp.model.UserModel
import com.ichsanalfian.mystoryapp.ui.story.StoryActivity
import com.ichsanalfian.mystoryapp.utils.ViewModelFactory



private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels { factory }
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userModel: UserModel
    private lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        factory = ViewModelFactory.getInstance(this)
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

//    private fun setupViewModel() {
//        factory = ViewModelFactory.getInstance(this)
//    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            loginViewModel.loginRequest(
                binding.edLoginEmail.text.toString(),
                binding.edLoginPassword.text.toString()
            )
            loginViewModel.login.observe(this@LoginActivity) {
                saveUser(
                    UserModel(
                        it.loginResult?.name.toString(),
                        true,
                        "Bearer " + (it.loginResult?.token.toString())
                    )
                )
            }
            loginViewModel.userLogin()
            loginViewModel.login.observe(this@LoginActivity) {login ->
                if(login.error){
                    Toast.makeText(this, login.error.toString(), Toast.LENGTH_SHORT).show()
                }
                else {
                    AlertDialog.Builder(this).apply {
                        setTitle("Yeah!")
                        setMessage("Anda berhasil login. Mari bagikan momen indah anda")
                        setPositiveButton("Lanjut") { _, _ ->
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(Intent(this@LoginActivity, StoryActivity::class.java))
                            finish()

                        }
                        create()
                        show()
                    }

                }
            }
        }
    }
//    edittttt
//    private fun setupAction() {
//        binding.apply {
//            loginButton.setOnClickListener {
//                if (edLoginEmail.length() == 0 && edLoginPassword.length() == 0) {
////                    edLoginEmail.error = getString(R.string.required_field)
////                    edtPassword.setError(getString(R.string.required_field), null)
//                } else if (edLoginEmail.length() != 0 && edLoginPassword.length() != 0) {
//                    //input login
//                    loginViewModel.loginRequest(
//                        binding.edLoginEmail.text.toString(),
//                        binding.edLoginPassword.text.toString()
//                    )
//                    loginViewModel.login.observe(this@LoginActivity) {
//                        saveUser(
//                            UserModel(
//                                it.loginResult?.name.toString(),
//                                true,
//                                "Bearer " + (it.loginResult?.token.toString())
//                            )
//                        )
//                    }
//
//                    loginViewModel.userLogin()
//                    //move activity
//                    loginViewModel.login.observe(this@LoginActivity) {
//                        if (!it.error) {
//                            startActivity(Intent(this@LoginActivity, StoryActivity::class.java))
//                            finish()
//                        }
//                    }
//                }
//            }
//        }
//    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

//        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
//       val message =
//           ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(500)
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

                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login
            )
            startDelay = 500
        }.start()
    }

    private fun saveUser(uModel: UserModel) {
        loginViewModel.saveUser(uModel)
    }


}