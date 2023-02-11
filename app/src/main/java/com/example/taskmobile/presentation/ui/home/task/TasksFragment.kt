package com.example.taskmobile.presentation.ui.home.task

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.taskmobile.core.AppConstans
import com.example.taskmobile.data.model.Task
import com.example.taskmobile.data.model.UpdateTaskStatusModel
import com.example.taskmobile.databinding.FragmentTasksBinding
import com.example.taskmobile.presentation.ui.home.task.adapter.TaskActionListener
import com.example.taskmobile.presentation.ui.home.task.adapter.TasksAdapter
import com.example.taskmobile.presentation.ui.task.create.FormTaskActivity
import com.example.taskmobile.presentation.ui.task.create.dialog.InfoTaskDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksFragment : Fragment() {

    private val binding by lazy { FragmentTasksBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<TasksViewModel>()
    private val adapter by lazy { TasksAdapter() }
    private lateinit var itemListener: TaskActionListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  binding.root

        observeLoadTasks()
        dealWithActionsTaskListener()

        return root
    }

    override fun onResume() {
        super.onResume()
        adapter.attachListener(itemListener)
        viewModel.loadTasks()
        binding.rvBoards.adapter = adapter
    }

    private fun observeLoadTasks(){
        viewModel.state.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }

    private fun dealWithActionsTaskListener(){
        itemListener = object : TaskActionListener{
            override fun onCompleteClick(id: String) {
                val updateTaskStatusModel = UpdateTaskStatusModel(id, true)
                viewModel.updateStatusTask(updateTaskStatusModel)
            }

            override fun onEditClick(id: String) {
                val intent = Intent(context, FormTaskActivity::class.java)
                val bundle = Bundle()
                bundle.putString(AppConstans.BUNDLE.IDTASK, id)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDeleteClick(id: String) {
                viewModel.deleteTask(id)
                val snackbar = Snackbar.make(binding.root, "Task deleted!!", Snackbar.LENGTH_LONG)
                snackbar.setAction("UNDO", View.OnClickListener {
                    viewModel.undoDelete()
                })
                snackbar.show()
            }

            override fun onTaskClick(task: Task) {
                val bundle = Bundle()
                bundle.putSerializable(AppConstans.BUNDLE.TASK, task)

                val dialog = InfoTaskDialogFragment()
                dialog.arguments = bundle
                dialog.show(parentFragmentManager, dialog.tag)
            }

        }
    }



}