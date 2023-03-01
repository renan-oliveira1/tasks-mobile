package com.example.taskmobile.presentation.ui.board.tasks

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.taskmobile.R
import com.example.taskmobile.core.AppConstants
import com.example.taskmobile.core.UiEvent
import com.example.taskmobile.data.model.BoardTask
import com.example.taskmobile.data.model.BoardTaskStatus
import com.example.taskmobile.databinding.ActivityBoardTasksBinding
import com.example.taskmobile.presentation.ui.board.tasks.adapter.BoardTaskActionListener
import com.example.taskmobile.presentation.ui.board.tasks.adapter.BoardTaskAdapter
import com.example.taskmobile.presentation.ui.board.tasks.dialog.BoardTaskDialogFragment
import com.example.taskmobile.presentation.ui.board.form.FormBoardTaskActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class BoardTasksActivity : AppCompatActivity() {
    private val binding by lazy{ActivityBoardTasksBinding.inflate(layoutInflater)}
    private val viewModel by viewModels<BoardTasksViewModel>()
    private val adapter by lazy{ BoardTaskAdapter() }
    private lateinit var listener: BoardTaskActionListener

    private var idBoard: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadDataFromActivity()
        listenerBottomNav()

        observerTasks()
        dealWithTaskListener()
        btnListeners()

    }

    override fun onResume() {
        super.onResume()
        binding.rvBoardsTasks.adapter = adapter
        adapter.setListener(listener)
        viewModel.loadTasks(idBoard, BoardTaskStatus.UNASSIGNED)
    }

    private fun btnListeners(){
        binding.fabAddBoard.setOnClickListener{
            val intent = Intent(applicationContext, FormBoardTaskActivity::class.java)
            startActivity(intent)
        }
    }

    private fun listenerBottomNav(){

        binding.bnv.setOnItemSelectedListener {
            when(it.itemId){
                R.id.unassiged -> viewModel.loadTasks(idBoard, BoardTaskStatus.UNASSIGNED)
                R.id.started -> viewModel.loadTasks(idBoard, BoardTaskStatus.STARTED)
                R.id.completed ->  viewModel.loadTasks(idBoard, BoardTaskStatus.COMPLETED)
            }
            true
        }

    }

    private fun loadDataFromActivity(){
        val sharedPreferences = applicationContext.getSharedPreferences(AppConstants.SHARED_PREFERENCES.ID_BOARD, Context.MODE_PRIVATE)
        idBoard = sharedPreferences.getString(AppConstants.BUNDLE.IDBOARD, null).toString()
    }

    private fun observerTasks(){
        viewModel.state.observe(this){
            adapter.submitList(it)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collectLatest { result ->
                when(result){
                    is UiEvent.ShowSnackbar -> {
                        val snackbar = Snackbar.make(binding.root, result.text, Snackbar.LENGTH_LONG)
                        snackbar.setAction("UNDO", View.OnClickListener {
                            viewModel.undoDelete()
                        })

                        snackbar.show()
                    }
                    is UiEvent.ShowToast ->
                        Toast.makeText(applicationContext,result.text, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun dealWithTaskListener(){
        listener = object : BoardTaskActionListener {
            override fun onTaskClick(task: BoardTask) {
                val bundle = Bundle()
                bundle.putSerializable(AppConstants.BUNDLE.BOARD_TASK, task)

                val dialog = BoardTaskDialogFragment()
                dialog.arguments = bundle
                dialog.show(supportFragmentManager, dialog.tag)
            }

            override fun onCompleteClick(task: BoardTask) {

                if(task.status == BoardTaskStatus.UNASSIGNED)
                    task.id?.let { viewModel.updateStatusTask(it, BoardTaskStatus.STARTED, idBoard) }

                if(task.status == BoardTaskStatus.STARTED)
                    task.id?.let { viewModel.updateStatusTask(it, BoardTaskStatus.COMPLETED, idBoard) }


            }

            override fun onEditClick(id: String) {
                val bundle = Bundle()
                bundle.putString(AppConstants.BUNDLE.IDTASK, id)
                bundle.putString(AppConstants.BUNDLE.IDBOARD, idBoard)
                val intent = Intent(applicationContext, FormBoardTaskActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDeleteClick(task: BoardTask) {
                viewModel.deleteTask(task)
            }

            override fun onUndoClick(task: BoardTask) {
                if(task.status == BoardTaskStatus.STARTED)
                    task.id?.let { viewModel.updateStatusTask(it, BoardTaskStatus.UNASSIGNED, idBoard) }

                if(task.status == BoardTaskStatus.COMPLETED)
                    task.id?.let { viewModel.updateStatusTask(it, BoardTaskStatus.STARTED, idBoard) }
            }

        }
    }
}