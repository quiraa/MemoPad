@file:OptIn(ExperimentalMaterial3Api::class)

package com.quiraadev.notez.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.quiraadev.notez.presentation.common.TodoEvent
import com.quiraadev.notez.presentation.common.TodoState
import com.quiraadev.notez.presentation.viewmodel.TodoViewModel

@Composable
fun TodoDialog(
    state: TodoState,
    onDismiss: () -> Unit,
    onTodoEvent: (TodoEvent) -> Unit,
    todoViewModel: TodoViewModel
) {

    return Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Column(
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraLarge)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            TodoDialogBody(
                state = state,
                onEvent = onTodoEvent,
                onDismiss = onDismiss,
                todoViewModel = todoViewModel
            )
        }
    }
}

@Composable
fun TodoDialogBody(
    state: TodoState,
    onEvent: (TodoEvent) -> Unit,
    onDismiss: () -> Unit,
    todoViewModel: TodoViewModel
) {

    val selectedTodo by todoViewModel.selectedTodo.collectAsState()

    val currentTask = selectedTodo?.task ?: state.task.value
    val currentImportant = selectedTodo?.isImportant ?: state.isImportant.value

    LaunchedEffect(key1 = selectedTodo) {
        if (selectedTodo != null) {
            state.task.value = selectedTodo!!.task
            state.isImportant.value = selectedTodo!!.isImportant
        }
    }

    val onSaveClicked: () -> Unit = {
        if (selectedTodo != null) {
            todoViewModel.updateTodo(
                task = state.task.value,
                isImportant = state.isImportant.value,
            )
        } else {
            onEvent(
                TodoEvent.SaveTodo(
                    title = currentTask,
                    isImportant = currentImportant,
                )
            )
        }
    }

    var isDatePickerShown by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Todo",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.W400
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.task.value,
            onValueChange = {
                state.task.value = it
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Task")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Mark as Important",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.W500,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(8.dp))
            Checkbox(
                checked = state.isImportant.value,
                onCheckedChange = { isChecked ->
                    state.isImportant.value = isChecked
                },
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
            TextButton(onClick = {
                onDismiss()
                onSaveClicked()
            }
            ) { Text(text = "Save") }
        }
    }
}
