package com.example.taskmobile.presentation.ui.home.board

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.Toast
import androidx.fragment.app.viewModels

import com.example.taskmobile.databinding.FragmentBoardsBinding
import com.example.taskmobile.presentation.ui.home.board.adapter.BoardAdapter
import com.example.taskmobile.presentation.ui.home.board.dialog.AddDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BoardsFragment : Fragment() {
    private val binding by lazy{FragmentBoardsBinding.inflate(layoutInflater)}
    private val adapter by lazy { BoardAdapter() }
    private val viewModel by viewModels<BoardViewModel>()

    override fun onResume() {
        super.onResume()
        viewModel.loadBoards()

        binding.rvBoards.adapter = adapter
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = binding.root

        observeLoadBoards()

        return root
    }


    private fun observeLoadBoards(){
        viewModel.state.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }

}