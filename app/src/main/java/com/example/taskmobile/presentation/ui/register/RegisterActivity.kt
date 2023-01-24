package com.example.taskmobile.presentation.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.taskmobile.databinding.ActivityRegisterBinding
import com.example.taskmobile.presentation.ui.login.LoginActivity
import com.example.taskmobile.presentation.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

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
        viewModel.status.observe(this){
            if(it){
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }else{
                val message = Toast.makeText(this, "Erro ao cadastrar usuário!!", Toast.LENGTH_SHORT)
                message.show()
            }
        }
    }
}