package com.example.taskmobile.presentation.ui.home.task.adapter

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmobile.data.model.Board
import com.example.taskmobile.data.model.Task
import com.example.taskmobile.databinding.BoardItemBinding
import com.example.taskmobile.databinding.TaskItemBinding

class TasksAdapter: ListAdapter<Task, TasksAdapter.ViewHolder>(DiffCallback())  {

    private lateinit var taskActionListener: TaskActionListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TaskItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding, taskActionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun attachListener(listener: TaskActionListener){
        taskActionListener = listener
    }




    inner class ViewHolder(
        private val binding: TaskItemBinding,
        private val listener: TaskActionListener
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Task){
            binding.apply {
                tvName.text = item.name
                tvDateInfo.text = item.date

                if(item.complete == true){
                    ifbComplete.visibility = View.INVISIBLE
                    ifbEdit.visibility = View.INVISIBLE
                }
                if(item.complete == false){
                    ifbComplete.visibility = View.VISIBLE
                    ifbEdit.visibility = View.VISIBLE
                }

                ifbRemove.setOnClickListener {
                    item.id?.let { it1 -> listener.onDeleteClick(it1) }
                }

                clLayoutCard.setOnClickListener{
                    listener.onTaskClick(item)
                }

                ifbComplete.setOnClickListener { item.id?.let { it1 -> listener.onCompleteClick(it1) } }

                ifbEdit.setOnClickListener {
                    item.id?.let { it1 -> listener.onEditClick(it1) }
                }

            }

        }

    }
}

class DiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
}