package com.quiraadev.notez.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("tbl_todo")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val todoId: Int = 0,
    val task: String,
    val isImportant: Boolean = false
)
