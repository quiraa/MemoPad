package com.quiraadev.notez.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("tbl_note")
data class Note(
    val title: String,
    val content: String,
    val dateCreated: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)