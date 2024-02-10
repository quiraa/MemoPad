package com.quiraadev.notez.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material.icons.rounded.Sort
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.quiraadev.notez.presentation.events.NotesEvent
import com.quiraadev.notez.presentation.states.NoteState
import com.quiraadev.notez.ui.navigator.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    noteState: NoteState,
    navController: NavHostController,
    onEvent: (NotesEvent) -> Unit
) {
    return Scaffold(
        topBar = {
            Surface(shadowElevation = 4.dp) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "Notes")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            onEvent(NotesEvent.DeleteAllNote)
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.DeleteForever,
                                contentDescription = null,
                                tint = Color.Red
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            onEvent(NotesEvent.SortNotes)
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Sort,
                                contentDescription = null,
                                tint = Color.Blue
                            )
                        }
                    }
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    noteState.title.value = ""
                    noteState.content.value = ""
                    Screen.push(navController, Screen.AddNoteScreen.route)
                },
                containerColor = Color.Blue,
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null, tint = Color.White)
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it), contentPadding = PaddingValues(vertical = 8.dp)) {
            items(noteState.notes.size) { index ->
                NoteCardItem(state = noteState, index = index, onEvent = onEvent)
            }
        }
    }
}

@Composable
fun NoteCardItem(
    state: NoteState,
    index: Int,
    onEvent: (NotesEvent) -> Unit
) {
    return Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = state.notes[index].title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = state.notes[index].content,
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = state.notes[index].dateCreated,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            IconButton(
                onClick = {
                    onEvent(NotesEvent.DeleteNote(state.notes[index]))
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "Delete Note",
                    tint = Color.Red
                )
            }
        }
    }
}