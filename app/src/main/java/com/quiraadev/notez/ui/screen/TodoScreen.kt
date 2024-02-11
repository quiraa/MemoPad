package com.quiraadev.notez.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.quiraadev.notez.presentation.common.TodoEvent
import com.quiraadev.notez.presentation.common.TodoState
import com.quiraadev.notez.presentation.viewmodel.TodoViewModel
import com.quiraadev.notez.ui.widget.AvailableTodosContent
import com.quiraadev.notez.ui.widget.EmptyState
import com.quiraadev.notez.ui.widget.TodoDialog

@Composable
fun TodoScreen(
    state: TodoState,
    todoViewModel: TodoViewModel,
    onEvent: (TodoEvent) -> Unit,
) {
    var isDialogShown by remember { mutableStateOf(false) }

    return Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    state.task.value = ""
                    state.isImportant.value = false
                    todoViewModel.setSelectedTodo(null)
                    isDialogShown = true
                },
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        if (isDialogShown) {
            TodoDialog(
                state = state,
                onDismiss = {
                    isDialogShown = false
                },
                onTodoEvent = onEvent,
                todoViewModel = todoViewModel
            )
        }
        Column(modifier = Modifier.padding(it)) {
            when (state.todos.isEmpty()) {
                true -> EmptyState()
                false -> AvailableTodosContent(
                    state = state,
                    onEvent = onEvent,
                    onEditTodo = { todo ->
                        todoViewModel.setSelectedTodo(todo)
                        isDialogShown = true
                    },
                )
            }
        }
    }
}