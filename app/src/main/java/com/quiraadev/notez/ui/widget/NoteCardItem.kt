@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.quiraadev.notez.ui.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Sort
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quiraadev.notez.data.local.model.Note
import com.quiraadev.notez.presentation.common.NoteState
import com.quiraadev.notez.presentation.common.NotesEvent

@Composable
fun AvailableNotesContent(
    state: NoteState,
    onEvent: (NotesEvent) -> Unit,
    onNoteClick: (Note) -> Unit,
) {
    return LazyColumn {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(modifier = Modifier) {
                    androidx.compose.material.IconButton(
                        modifier = Modifier,
                        onClick = {
                            onEvent(NotesEvent.DeleteAllNote)
                        }) {
                        Icon(
                            imageVector = Icons.Rounded.DeleteForever,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    modifier = Modifier,
                    onClick = {
                        onEvent(NotesEvent.SortNotes)
                    }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.Sort,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
        items(state.notes.size) { index ->
            NoteCardItem(
                state = state,
                index = index,
                onEvent = onEvent,
                onNoteClick = onNoteClick
            )
        }
    }
}

@Composable
fun NoteCardItem(
    state: NoteState,
    index: Int,
    onEvent: (NotesEvent) -> Unit,
    onNoteClick: (Note) -> Unit
) {
    return OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        onClick = {
            onNoteClick(state.notes[index])
        },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
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
                    color = MaterialTheme.colorScheme.outline
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
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}