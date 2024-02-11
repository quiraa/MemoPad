package com.quiraadev.notez.presentation.common

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.quiraadev.notez.data.local.model.Todo

data class TodoState(
    val todos : List<Todo> = emptyList(),
    val id : Int = 0,
    val task : MutableState<String> = mutableStateOf(""),
    val isImportant : MutableState<Boolean> = mutableStateOf(false),
)