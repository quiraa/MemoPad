package com.quiraadev.notez.presentation.common

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.quiraadev.notez.data.local.model.Note

data class NoteState (
    val notes : List<Note> = emptyList(),
    val id: Int = 0,
    val title: MutableState<String> = mutableStateOf(""),
    val content: MutableState<String> = mutableStateOf(""),
)