package com.example.taskmobile.presentation.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.taskmobile.core.UiEvent
import com.example.taskmobile.databinding.ActivityRegisterBinding
import com.example.taskmobile.presentation.ui.login.LoginActivity
import com.example.taskmobile.presentation.ui.home.HomeActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private val binding by lazy{ActivityRegisterBinding.inflate(layoutInflater)}
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        buttonListeners()
        observeRegisterStatus()
    }

    private fun buttonListeners(){
        binding.bLogin.setOnClickListener{
            register()
        }

        binding.bBack.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun register(){
        viewModel.register(
            binding.tilUsername.editText?.text.toString(),
            binding.tilEmail.editText?.text.toString(),
            binding.tilPassword.editText?.text.toString()
        )
    }

    private fun observeRegisterStatus(){
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