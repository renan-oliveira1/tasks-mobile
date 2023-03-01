package com.example.taskmobile.presentation.ui.board.tasks.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.taskmobile.core.AppConstants
import com.example.taskmobile.data.model.BoardTask
import com.example.taskmobile.databinding.DialogBoardTaskBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardTaskDialogFragment: DialogFragment()  {
    private val binding by lazy {
        DialogBoardTaskBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.ivBack.setOnClickListener {
            dismiss()
        }

        val task = arguments?.getSerializable(AppConstants.BUNDLE.BOARD_TASK) as BoardTask
        binding.tvName.text = task.name
        binding.tvDescription.text = task.description

    }


}