package com.example.taskmobile.presentation.ui.home.board

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.taskmobile.R
import com.example.taskmobile.databinding.FragmentBoardsBinding
import com.example.taskmobile.presentation.ui.home.board.adapter.BoardAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BoardsFragment : Fragment() {
    private val binding by lazy{FragmentBoardsBinding.inflate(layoutInflater)}
    private val adapter by lazy { BoardAdapter() }
    private val viewModel by viewModels<BoardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onResume() {
        super.onResume()
        viewModel.loadBoards()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = binding.root

        binding.rvBoards.adapter = adapter

        loadBoards()

        return root
    }

    private fun loadBoards(){
        viewModel.state.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }
}