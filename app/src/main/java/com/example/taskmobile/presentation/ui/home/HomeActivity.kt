package com.example.taskmobile.presentation.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.taskmobile.R
import com.example.taskmobile.databinding.ActivityHomeBinding
import com.example.taskmobile.presentation.ui.home.board.dialog.AddDialogFragment
import com.example.taskmobile.presentation.ui.home.board.BoardsFragment
import com.example.taskmobile.presentation.ui.home.task.TasksFragment
import com.example.taskmobile.presentation.ui.task.create.FormTaskActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), AddDialogFragment.AddDialogListener{

    private val binding by lazy{ActivityHomeBinding.inflate(layoutInflater)}
    private var selectedScreens: HomeScreens = HomeScreens.TASKS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initializeTasksHomePage()

        listenerBottomNavBar()
        btnListener()

    }

    private fun initializeTasksHomePage(){
        selectFragment(TasksFragment(), HomeScreens.TASKS)
    }

    private fun listenerBottomNavBar() {
        binding.bnv.setOnItemSelectedListener {
            when(it.itemId){
                R.id.tasks -> selectFragment(TasksFragment(), HomeScreens.TASKS)
                R.id.boards -> selectFragment(BoardsFragment(), HomeScreens.BOARDS)
            }
            true

        }
    }

    private fun selectFragment(fragment: Fragment, screen: HomeScreens){
        selectedScreens = screen
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl, fragment)
        fragmentTransaction.commit()
    }

    private fun btnListener(){
        binding.fabAddBoard.setOnClickListener {
            when(selectedScreens){
                HomeScreens.BOARDS -> openAddBoardDialog()
                HomeScreens.TASKS -> openAddTaskActivity()
            }
        }
    }

    private fun openAddTaskActivity() {
        val intent = Intent(this, FormTaskActivity::class.java)
        startActivity(intent)
    }

    private fun openAddBoardDialog(){
        val dialog = AddDialogFragment()
        dialog.show(supportFragmentManager, dialog.tag)
    }

    override fun applyReload() {
        val fragment = BoardsFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fl, fragment, "TAG")
        fragmentTransaction.commit()
        Toast.makeText(baseContext, "Board criado!!", Toast.LENGTH_SHORT).show()
    }


}