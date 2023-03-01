package com.example.taskmobile.presentation.ui.home.board.dialog

import android.content.Context
import android.content.DialogInterface
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.taskmobile.R
import com.example.taskmobile.databinding.DialogBoardCreateBinding
import com.example.taskmobile.presentation.ui.home.HomeActivity
import com.example.taskmobile.presentation.ui.home.board.BoardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddDialogFragment: DialogFragment() {

    private val binding by lazy {
        DialogBoardCreateBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<BoardViewModel>()
    private lateinit var addDialogListener: AddDialogListener

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            addDialogListener = activity as AddDialogListener
        }catch (e: java.lang.ClassCastException){
            Log.e("siela", "onAttach: ClassCastException: ${e.message}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btnListener()
    }

    private fun btnListener(){
        binding.btnSave.setOnClickListener{
            val name = binding.tilName.editText?.text.toString()
            viewModel.createBoard(name)
            addDialogListener.applyReload()
            dismiss()
        }

        binding.btnCancel.setOnClickListener { dismiss() }
    }

    public interface AddDialogListener{
        fun applyReload()
    }

}