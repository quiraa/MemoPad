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
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.quiraadev.notez.presentation.common.NoteState
import com.quiraadev.notez.presentation.common.NotesEvent
import com.quiraadev.notez.presentation.viewmodel.NoteViewModel
import com.quiraadev.notez.ui.navigator.Screen
import com.quiraadev.notez.ui.widget.AvailableNotesContent
import com.quiraadev.notez.ui.widget.EmptyState


@Composable
fun NoteScreen(
    state: NoteState,
    navController: NavHostController,
    onEvent: (NotesEvent) -> Unit,
    noteViewModel: NoteViewModel,
) {
    return Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    state.title.value = ""
                    state.content.value = ""
                    noteViewModel.setSelectedNote(null)
                    Screen.push(navController, Screen.AddNoteScreen.route)
                },
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            when (state.notes.isEmpty()) {
                true -> EmptyState()
                false -> AvailableNotesContent(
                    state = state,
                    onEvent = onEvent,
                    onNoteClick = { note ->
                        noteViewModel.setSelectedNote(note)
                        Screen.push(navController, Screen.AddNoteScreen.route)
                    },
                )
            }
        }
    }
}