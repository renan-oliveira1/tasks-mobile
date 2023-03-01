package com.example.taskmobile.presentation.ui.home.board

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.taskmobile.core.AppConstants
import com.example.taskmobile.core.UiEvent

import com.example.taskmobile.databinding.FragmentBoardsBinding
import com.example.taskmobile.presentation.ui.board.tasks.BoardTasksActivity
import com.example.taskmobile.presentation.ui.board.settings.BoardSettingsActivity
import com.example.taskmobile.presentation.ui.home.HomeActivity
import com.example.taskmobile.presentation.ui.home.board.adapter.BoardActionListener
import com.example.taskmobile.presentation.ui.home.board.adapter.BoardAdapter
import com.example.taskmobile.presentation.ui.task.create.FormTaskActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class BoardsFragment : Fragment() {
    private val binding by lazy{FragmentBoardsBinding.inflate(layoutInflater)}
    private val adapter by lazy { BoardAdapter() }
    private lateinit var listener: BoardActionListener
    private val viewModel by viewModels<BoardViewModel>()

    override fun onResume() {
        super.onResume()
        viewModel.loadBoards()
        adapter.setListener(listener)
        binding.rvBoards.adapter = adapter
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = binding.root

        observeViewModel()
        dealWithBoardListener()

        return root
    }


    private fun observeViewModel(){
        viewModel.state.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collectLatest { result ->
                when(result){
                    is UiEvent.ShowToast ->
                        Toast.makeText(context,result.text, Toast.LENGTH_LONG).show()
                    is UiEvent.ShowSnackbar -> {
                        val snackbar = Snackbar.make(binding.root, result.text, Snackbar.LENGTH_LONG)
                        snackbar.show()

                        val intent = Intent(context, HomeActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun dealWithBoardListener(){
        listener = object : BoardActionListener{
            override fun onTaskClick(id: String) {

                val sharedPreferences =
                    context?.getSharedPreferences(AppConstants.SHARED_PREFERENCES.ID_BOARD, Context.MODE_PRIVATE)
                val editor = sharedPreferences?.edit()
                editor?.putString(AppConstants.BUNDLE.IDBOARD, id)
                editor?.commit()

                val intent = Intent(context, BoardTasksActivity::class.java)
                startActivity(intent)
            }

            override fun onSettingClick(id: String) {
                val sharedPreferences =
                    context?.getSharedPreferences(AppConstants.SHARED_PREFERENCES.ID_BOARD, Context.MODE_PRIVATE)
                val editor = sharedPreferences?.edit()
                editor?.putString(AppConstants.BUNDLE.IDBOARD, id)
                editor?.commit()

                val intent = Intent(context, BoardSettingsActivity::class.java)
                startActivity(intent)
            }

        }
    }



}