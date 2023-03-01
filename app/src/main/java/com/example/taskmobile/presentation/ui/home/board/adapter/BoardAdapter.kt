package com.example.taskmobile.presentation.ui.home.board.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmobile.data.model.Board
import com.example.taskmobile.databinding.BoardItemBinding


class BoardAdapter: ListAdapter<Board, BoardAdapter.ViewHolder>(DiffCallback()) {

    private lateinit var boardActionListener: BoardActionListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BoardItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, boardActionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setListener(listener: BoardActionListener){
        boardActionListener = listener
    }


    inner class ViewHolder(
        private val binding: BoardItemBinding,
        private val listener: BoardActionListener
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Board){
            binding.tvName.text = item.name

            binding.clLayoutCard.setOnClickListener{
                item.id?.let { it1 -> listener.onTaskClick(it1) }
            }

            binding.ifbSettings.setOnClickListener {
                item.id?.let { it1 -> listener.onSettingClick(it1) }
            }
        }

    }

}

class DiffCallback : DiffUtil.ItemCallback<Board>() {
    override fun areItemsTheSame(oldItem: Board, newItem: Board) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Board, newItem: Board) = oldItem.id == newItem.id
}