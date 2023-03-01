package com.example.taskmobile.presentation.ui.board.settings.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmobile.core.AppConstants
import com.example.taskmobile.data.model.AddUserBoardModel
import com.example.taskmobile.data.model.BoardTask
import com.example.taskmobile.data.model.BoardTaskStatus
import com.example.taskmobile.data.model.UserModel
import com.example.taskmobile.databinding.BoardTaskItemBinding
import com.example.taskmobile.databinding.SettingsItemBinding
import com.example.taskmobile.presentation.ui.board.tasks.adapter.BoardTaskActionListener

class BoardSettingsAdapter: ListAdapter<UserModel, BoardSettingsAdapter.ViewHolder>(DiffCallback()) {

    private lateinit var boardUserListener: BoardUserListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SettingsItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, boardUserListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setListener(listener: BoardUserListener){
        boardUserListener = listener
    }


    inner class ViewHolder(
        private val binding: SettingsItemBinding,
        private val listener: BoardUserListener

    ): RecyclerView.ViewHolder(binding.root){

        fun bind(item: UserModel){
            binding.apply {

                if(item.userFilter == USER_FILTER.IN || item.userFilter == null){
                    ifbAdd.visibility = View.INVISIBLE
                    ifbRemove.visibility = View.VISIBLE
                }

                if(item.userFilter == USER_FILTER.OUT){
                    ifbAdd.visibility = View.VISIBLE
                    ifbRemove.visibility = View.INVISIBLE
                }

                tvName.text = item.username
                tvEmail.text = item.email

                ifbRemove.setOnClickListener{
                    item.id?.let { it1 ->
                        listener.removeUser(
                            it1
                        )
                    }
                }

                ifbAdd.setOnClickListener {
                    item.id?.let { it1 -> listener.addUser(it1) }
                }
            }


        }

    }

}

class DiffCallback : DiffUtil.ItemCallback<UserModel>() {
    override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel) = oldItem == newItem
    override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel) = oldItem.id == newItem.id
}