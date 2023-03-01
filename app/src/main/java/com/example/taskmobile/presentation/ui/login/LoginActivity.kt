package com.example.taskmobile.presentation.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.taskmobile.core.UiEvent
import com.example.taskmobile.databinding.ActivityLoginBinding
import com.example.taskmobile.presentation.ui.home.HomeActivity
import com.example.taskmobile.presentation.ui.register.RegisterActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        btnListener()
        observeLoginStatus()

    }


    private fun btnListener(){
        binding.bLogin.setOnClickListener{
            login()
        }

        binding.tvMessageRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun login(){
        val email = binding.tilEmail.editText?.text.toString()
        val password = binding.tilPassword.editText?.text.toString()
        viewModel.login(email, password)
    }

    private fun observeLoginStatus(){
        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collectLatest { result ->
                when(result){
                    is UiEvent.ShowToast ->
                        Toast.makeText(applicationContext,result.text, Toast.LENGTH_LONG).show()
                    is UiEvent.ShowSnackbar -> {
                        val snackbar = Snackbar.make(binding.root, result.text, Snackbar.LENGTH_LONG)
                        snackbar.show()

                        val intent = Intent(applicationContext, HomeActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}