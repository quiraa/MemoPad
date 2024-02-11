package com.quiraadev.notez.presentation.common

import com.quiraadev.notez.data.local.model.Todo

sealed interface TodoEvent {
    data class DoneTodo(val todo: Todo) : TodoEvent
    data object DeleteAllTodo : TodoEvent
    data class SaveTodo(
        val title: String,
        val isImportant: Boolean,
    ) : TodoEvent
}