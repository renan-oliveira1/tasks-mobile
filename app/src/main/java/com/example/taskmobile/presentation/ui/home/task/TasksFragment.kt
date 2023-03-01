package com.example.taskmobile.presentation.ui.home.task

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.taskmobile.core.AppConstants
import com.example.taskmobile.core.UiEvent
import com.example.taskmobile.data.model.Task
import com.example.taskmobile.data.model.UpdateTaskStatusModel
import com.example.taskmobile.databinding.FragmentTasksBinding
import com.example.taskmobile.presentation.ui.home.HomeActivity
import com.example.taskmobile.presentation.ui.home.task.adapter.TaskActionListener
import com.example.taskmobile.presentation.ui.home.task.adapter.TasksAdapter
import com.example.taskmobile.presentation.ui.task.create.FormTaskActivity
import com.example.taskmobile.presentation.ui.task.create.dialog.InfoTaskDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class TasksFragment : Fragment() {

    private val binding by lazy { FragmentTasksBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<TasksViewModel>()
    private val adapter by lazy { TasksAdapter() }
    private lateinit var itemListener: TaskActionListener
    private var taskFilter: Int = AppConstants.TASK_FILTER.DONE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  binding.root

        observeLoadTasks()
        dealWithActionsTaskListener()

        spinnerListener()


        return root
    }

    override fun onResume() {
        super.onResume()
        adapter.attachListener(itemListener)
        viewModel.loadTasks(taskFilter)
        binding.rvBoards.adapter = adapter
    }

    private fun spinnerListener(){
        binding.spinner.onItemSelectedListener = object: OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                taskFilter = position
                viewModel.loadTasks(taskFilter)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun observeLoadTasks(){
        viewModel.state.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collectLatest { result ->
                when(result){
                    is UiEvent.ShowToast ->
                        Toast.makeText(context, result.text, Toast.LENGTH_LONG).show()
                    is UiEvent.ShowSnackbar -> {
                        val snackbar = Snackbar.make(binding.root, result.text, Snackbar.LENGTH_LONG)
                        snackbar.show()
                    }
                }
            }
        }
    }

    private fun dealWithActionsTaskListener(){
        itemListener = object : TaskActionListener{
            override fun onCompleteClick(id: String) {
                val updateTaskStatusModel = UpdateTaskStatusModel(id, true)
                viewModel.updateStatusTask(updateTaskStatusModel, taskFilter)
            }

            override fun onEditClick(id: String) {
                val intent = Intent(context, FormTaskActivity::class.java)
                val bundle = Bundle()
                bundle.putString(AppConstants.BUNDLE.IDTASK, id)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDeleteClick(id: String) {
                viewModel.deleteTask(id, taskFilter)
                val snackbar = Snackbar.make(binding.root, "Task deleted!!", Snackbar.LENGTH_LONG)
                snackbar.setAction("UNDO", View.OnClickListener {
                    viewModel.undoDelete(taskFilter)
                })
                snackbar.show()
            }

            override fun onTaskClick(task: Task) {
                val bundle = Bundle()
                bundle.putSerializable(AppConstants.BUNDLE.TASK, task)

                val dialog = InfoTaskDialogFragment()
                dialog.arguments = bundle
                dialog.show(parentFragmentManager, dialog.tag)
            }

        }
    }



}