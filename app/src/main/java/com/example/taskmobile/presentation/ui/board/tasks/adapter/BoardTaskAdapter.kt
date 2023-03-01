package com.example.taskmobile.presentation.ui.board.tasks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmobile.data.model.Board
import com.example.taskmobile.data.model.BoardTask
import com.example.taskmobile.data.model.BoardTaskStatus
import com.example.taskmobile.databinding.BoardItemBinding
import com.example.taskmobile.databinding.BoardTaskItemBinding
import com.example.taskmobile.presentation.ui.home.board.adapter.BoardActionListener

class BoardTaskAdapter: ListAdapter<BoardTask, BoardTaskAdapter.ViewHolder>(DiffCallback()) {

    private lateinit var boardTaskActionListener: BoardTaskActionListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BoardTaskItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, boardTaskActionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setListener(listener: BoardTaskActionListener){
        boardTaskActionListener = listener
    }


    inner class ViewHolder(
        private val binding: BoardTaskItemBinding,
        private val listener: BoardTaskActionListener
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(item: BoardTask){
            binding.apply {
                tvName.text = item.name

                when(item.status){
                    BoardTaskStatus.UNASSIGNED -> {
                        ifbUndo.visibility = View.INVISIBLE
                        ifbComplete.visibility = View.VISIBLE
                    }
                    BoardTaskStatus.STARTED -> {
                        ifbUndo.visibility = View.VISIBLE
                        ifbComplete.visibility = View.VISIBLE
                    }
                    BoardTaskStatus.COMPLETED -> {
                        ifbUndo.visibility = View.VISIBLE
                        ifbComplete.visibility = View.INVISIBLE
                    }

                }

                clLayoutCard.setOnClickListener {
                    listener.onTaskClick(item)
                }

                ifbComplete.setOnClickListener{
                    listener.onCompleteClick(item)
                }

                ifbUndo.setOnClickListener{
                    listener.onUndoClick(item)
                }

                ifbEdit.setOnClickListener{
                    item.id?.let { it1 -> listener.onEditClick(it1) }
                }

                ifbRemove.setOnClickListener{
                    listener.onDeleteClick(item)
                }
            }


        }

    }

}

class DiffCallback : DiffUtil.ItemCallback<BoardTask>() {
    override fun areItemsTheSame(oldItem: BoardTask, newItem: BoardTask) = oldItem == newItem
    override fun areContentsTheSame(oldItem: BoardTask, newItem: BoardTask) = oldItem.id == newItem.id
}