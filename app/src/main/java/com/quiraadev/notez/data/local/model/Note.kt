package com.quiraadev.notez.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("tbl_note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "Untitled",
    val content: String = "Empty Notes",
    val dateCreated: String,
)