package com.example.taskmobile.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Task(
    @SerializedName("id") var id: String?,
    @SerializedName("name") var name: String,
    @SerializedName("description") var description: String,
    @SerializedName("date") var date: String,
    @SerializedName("complete") var complete: Boolean?,
    @SerializedName("user_id") var user: String?
) : Serializable {
    constructor(name: String, description: String, date: String) : this(null, name, description, date, false, null)
    constructor(id: String, name: String, description: String, date: String) : this(id, name, description, date, false, null)
}
