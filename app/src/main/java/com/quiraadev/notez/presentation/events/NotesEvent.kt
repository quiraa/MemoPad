package com.quiraadev.notez.presentation.events

import com.quiraadev.notez.data.local.model.Note

sealed interface NotesEvent {
    data object SortNotes : NotesEvent

    data class DeleteNote(val note: Note) : NotesEvent

    data object DeleteAllNote : NotesEvent

    data class SaveNote(
        val title: String,
        val content: String
    ) : NotesEvent

    data class UpdateNote(
        val title: String,
        val content: String
    ) : NotesEvent
}