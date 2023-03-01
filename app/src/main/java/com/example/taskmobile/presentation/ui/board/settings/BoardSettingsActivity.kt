package com.example.taskmobile.presentation.ui.board.settings

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.taskmobile.R
import com.example.taskmobile.core.AppConstants
import com.example.taskmobile.core.UiEvent
import com.example.taskmobile.data.model.AddUserBoardModel
import com.example.taskmobile.data.model.BoardTaskStatus
import com.example.taskmobile.databinding.ActivityBoardSettingsBinding
import com.example.taskmobile.presentation.ui.board.settings.adapter.BoardSettingsAdapter
import com.example.taskmobile.presentation.ui.board.settings.adapter.BoardUserListener
import com.example.taskmobile.presentation.ui.board.settings.adapter.USER_FILTER
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class BoardSettingsActivity : AppCompatActivity() {

    private val binding by lazy { ActivityBoardSettingsBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<BoardSettingsViewModel>()
    private val adapter by lazy { BoardSettingsAdapter() }
    private lateinit var listener: BoardUserListener
    private lateinit var idBoard: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadIdBoard()
        observeViewModel()

        dealWithListener()
        listenerBottomNav()
    }

    override fun onResume() {
        super.onResume()
        binding.rvUsers.adapter = adapter
        adapter.setListener(listener)
        viewModel.loadBoardManagers(idBoard)
    }

    private fun loadIdBoard(){
        val sharedPreferences = applicationContext.getSharedPreferences(AppConstants.SHARED_PREFERENCES.ID_BOARD, Context.MODE_PRIVATE)
        idBoard = sharedPreferences.getString(AppConstants.BUNDLE.IDBOARD, null).toString()
    }

    private fun observeViewModel(){
        viewModel.state.observe(this){
            adapter.submitList(it)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collectLatest { result ->
                when(result){
                    is UiEvent.ShowSnackbar -> {
                        val snackbar = Snackbar.make(binding.root, result.text, Snackbar.LENGTH_LONG)

                        snackbar.show()
                    }
                    is UiEvent.ShowToast ->
                        Toast.makeText(applicationContext,result.text, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun listenerBottomNav(){

        binding.bnv.setOnItemSelectedListener {
            when(it.itemId){
                R.id.managers -> viewModel.loadBoardManagers(idBoard)
                R.id.users -> viewModel.loadUsers(idBoard)
            }
            true
        }

    }

    fun dealWithListener(){
        listener = object : BoardUserListener{

            override fun addUser(idUser: String) {
                viewModel.addUserToBoard(idBoard, idUser)
            }

            override fun removeUser(idUser: String) {
                viewModel.removeUserBoard(idBoard, idUser)
            }

        }
    }



}