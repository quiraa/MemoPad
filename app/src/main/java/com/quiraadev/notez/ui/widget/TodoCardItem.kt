package com.quiraadev.notez.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material.icons.rounded.ModeEdit
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.quiraadev.notez.data.local.model.Todo
import com.quiraadev.notez.presentation.common.TodoEvent
import com.quiraadev.notez.presentation.common.TodoState

@Composable
fun AvailableTodosContent(
    state: TodoState,
    onEvent: (TodoEvent) -> Unit,
    onEditTodo: (Todo) -> Unit
) {
    return LazyColumn {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(modifier = Modifier) {
                    IconButton(
                        modifier = Modifier,
                        onClick = {
                            onEvent(TodoEvent.DeleteAllTodo)
                        }) {
                        Icon(
                            imageVector = Icons.Rounded.DeleteForever,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
        items(state.todos.size) { index ->
            TodoCardItem(state = state, index = index, onEvent = onEvent, onEditTodo = onEditTodo)
        }
    }
}

@Composable
fun TodoCardItem(
    state: TodoState,
    index: Int,
    onEvent: (TodoEvent) -> Unit,
    onEditTodo: (Todo) -> Unit
) {
    return OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = {
                    onEvent(TodoEvent.DoneTodo(state.todos[index]))
                },
                modifier = Modifier.clip(MaterialTheme.shapes.medium).background(MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Icon(imageVector = Icons.Rounded.Check, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = state.todos[index].task, style = MaterialTheme.typography.bodyLarge)
            }
            Spacer(modifier = Modifier.width(16.dp))
            if (state.todos[index].isImportant) {
                IconButton(onClick = { null }) {
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.surfaceTint
                    )
                }
            }
            IconButton(onClick = {
                onEditTodo(state.todos[index])
            }) {
                Icon(
                    imageVector = Icons.Rounded.ModeEdit,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}