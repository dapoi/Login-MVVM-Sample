package com.dapascript.loginsamplemvvm.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.dapascript.loginsamplemvvm.data.pref.DataStorePref
import com.dapascript.loginsamplemvvm.databinding.ActivityLoginBinding
import com.dapascript.loginsamplemvvm.presentation.home.HomeActivity
import com.dapascript.loginsamplemvvm.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var dataStorePref: DataStorePref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            initLogin()
        }

        initViewModel()
    }

    private fun initViewModel() {
        loginViewModel.loginState.observe(this@LoginActivity) { response ->
            binding.apply {
                when (response) {
                    is Resource.Loading -> {
                        progressBar.isVisible = true
                    }

                    is Resource.Success -> {
                        progressBar.isVisible = false
                        lifecycleScope.launch { dataStorePref.saveUserToken(response.data.token) }
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        intent.putExtra("token", response.data.token)
                        startActivity(intent)
                        finish()
                    }

                    is Resource.Failure -> {
                        progressBar.isVisible = false
                        Toast.makeText(
                            this@LoginActivity,
                            response.throwable.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun initLogin() {
        binding.apply {
            val email = emailEt.text.toString()
            val password = passwordEt.text.toString()

            when {
                email.isEmpty() -> emailEt.error = "Email is required"
                password.isEmpty() -> passwordEt.error = "Password is required"
                else -> loginViewModel.login(email, password)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        lifecycleScope.launch {
            dataStorePref.userToken.collect { token ->
                if (token.isNotEmpty()) {
                    Intent(this@LoginActivity, HomeActivity::class.java).also {
                        it.putExtra("token", token)
                        startActivity(it)
                        finish()
                    }
                }
            }
        }
    }
}