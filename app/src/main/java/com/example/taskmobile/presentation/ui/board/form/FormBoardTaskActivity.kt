package com.example.taskmobile.presentation.ui.board.form

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.taskmobile.core.AppConstants
import com.example.taskmobile.core.UiEvent
import com.example.taskmobile.data.model.SendBoardTask
import com.example.taskmobile.databinding.ActivityFormBoardTaskBinding
import com.example.taskmobile.presentation.ui.board.tasks.BoardTasksActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FormBoardTaskActivity : AppCompatActivity() {

    private val binding by lazy{ActivityFormBoardTaskBinding.inflate(layoutInflater)}
    private val viewModel by viewModels<FormBoardTaskViewModel>()

    private var editTask: String? = null
    private lateinit var idBoard: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadDataFromSharedPreferences()

        loadDataBundle()
        observeLoadedTask()
        btnListener()

        observerResult()


    }

    private fun loadDataBundle(){
        val bundle = intent.extras
        if(bundle != null){
            editTask = bundle.getString(AppConstants.BUNDLE.IDTASK).toString()
            viewModel.loadTask(editTask!!)
        }

    }

    private fun observeLoadedTask(){
        viewModel.editTask.observe(this){
            binding.tilName.editText?.setText(it.name)
            binding.tilDescription.editText?.setText((it.description))
        }
    }

    private fun btnListener(){
        binding.btnCancel.setOnClickListener {
            val intent = Intent(this, BoardTasksActivity::class.java)
            startActivity(intent)
        }

        binding.btnSave.setOnClickListener {
            verifyInputs()
        }

    }

    private fun observerResult(){
        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collectLatest { result ->
                when(result){
                    is UiEvent.ShowSnackbar -> {
                        val snackbar = Snackbar.make(binding.root, result.text, Snackbar.LENGTH_LONG)

                        snackbar.show()
                    }
                    is UiEvent.ShowToast -> {
                        Toast.makeText(applicationContext, result.text, Toast.LENGTH_LONG).show()
                        val intent = Intent(applicationContext, BoardTasksActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun verifyInputs() {
        var next = true

        if(binding.tilName.editText?.text?.isEmpty() == true){
            binding.tilName.error = "Nome não pode ser vazio!!"
            next = false
        }else{
            binding.tilName.isErrorEnabled = false
        }

        if(binding.tilDescription.editText?.text?.isEmpty() == true){
            binding.tilDescription.error = "Descrição não pode ser vazia!!"
            next = false
        }
        else{
            binding.tilDescription.isErrorEnabled = false
        }

        if(!next){
            Toast.makeText(this, "Campos inválidos!!", Toast.LENGTH_LONG).show()
            return
        }

        sendTask()
    }

    private fun loadDataFromSharedPreferences(){
        val sharedPreferences = applicationContext.getSharedPreferences(AppConstants.SHARED_PREFERENCES.ID_BOARD, Context.MODE_PRIVATE)
        idBoard = sharedPreferences.getString(AppConstants.BUNDLE.IDBOARD, null).toString()
    }

    private fun sendTask() {
        val task = SendBoardTask(
            name = binding.tilName.editText?.text.toString(),
            description = binding.tilDescription.editText?.text.toString(),
            board = idBoard
        )

        if(editTask != null){
            task.id = editTask
            viewModel.updateTask(task)
        }else{
            viewModel.createTask(task)
        }
    }
}