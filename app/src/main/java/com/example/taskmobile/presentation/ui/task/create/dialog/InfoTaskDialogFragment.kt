package com.example.taskmobile.presentation.ui.task.create.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.taskmobile.core.AppConstans
import com.example.taskmobile.data.model.Task
import com.example.taskmobile.databinding.DialogTaskInfoBinding

class InfoTaskDialogFragment: DialogFragment() {
    private val binding by lazy { DialogTaskInfoBinding.inflate(layoutInflater) }

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

        val task: Task = arguments?.getSerializable(AppConstans.BUNDLE.TASK) as Task
        binding.tvName.text = task.name
        binding.tvDate.text = task.date
        binding.tvDescription.text = task.description

        binding.ivBack.setOnClickListener {
            dismiss()
        }

    }
}