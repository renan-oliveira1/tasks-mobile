package com.example.taskmobile.presentation.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.taskmobile.databinding.ActivityLoginBinding
import com.example.taskmobile.presentation.ui.home.HomeActivity
import com.example.taskmobile.presentation.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

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
        viewModel.status.observe(this){
            if(it){
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }else{
                val message = Toast.makeText(this, "Erro ao efetuar login!!", Toast.LENGTH_SHORT)
                message.show()
            }
        }
    }
}