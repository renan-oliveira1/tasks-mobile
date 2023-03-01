package com.example.taskmobile.data.model

import com.google.gson.annotations.SerializedName

data class BoardTask(
    @SerializedName("id") var id: String?,
    @SerializedName("name") var name: String,
    @SerializedName("description") var description: String,
    @SerializedName("status") var status: BoardTaskStatus,
    @SerializedName("board_id") var board: String?
): java.io.Serializable {
    constructor(name: String, description: String, board: String) : this(null, name, description, BoardTaskStatus.UNASSIGNED, board)
}