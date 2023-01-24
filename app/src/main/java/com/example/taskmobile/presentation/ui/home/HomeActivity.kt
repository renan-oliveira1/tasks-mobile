package com.example.taskmobile.presentation.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.taskmobile.R
import com.example.taskmobile.databinding.ActivityHomeBinding
import com.example.taskmobile.presentation.ui.home.board.BoardsFragment
import com.example.taskmobile.presentation.ui.home.task.TasksFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val binding by lazy{ActivityHomeBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        listenerBottomNavBar()

    }

    private fun listenerBottomNavBar() {
        binding.bnv.setOnItemSelectedListener {
            when(it.itemId){
                R.id.tasks -> replaceFragment(TasksFragment())
                R.id.boards -> replaceFragment(BoardsFragment())

            }
            true

        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl, fragment)
        fragmentTransaction.commit()
    }
}