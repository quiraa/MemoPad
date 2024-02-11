@file:OptIn(ExperimentalCoroutinesApi::class)

package com.quiraadev.notez.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quiraadev.notez.data.local.dao.NoteDao
import com.quiraadev.notez.data.local.model.Note
import com.quiraadev.notez.presentation.common.NoteState
import com.quiraadev.notez.presentation.common.NotesEvent
import com.quiraadev.notez.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteDao: NoteDao
) : ViewModel() {

    private val _selectedNote = MutableStateFlow<Note?>(null)
    val selectedNote = _selectedNote.asStateFlow()

    private val isSortedByDateCreated = MutableStateFlow(true)

    private var notes = isSortedByDateCreated.flatMapLatest { isSorted ->
        if (isSorted) noteDao.getNotesOrderByDate()
        else noteDao.getNotesOrderByTitle()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _noteState = MutableStateFlow(NoteState())
    val noteState = combine(_noteState, isSortedByDateCreated, notes) { state, isSorted, notes ->
        state.copy(
            notes = notes
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(500), NoteState())

    fun onNoteEvent(event: NotesEvent)  {
        when (event) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteDao.deleteNote(event.note)
                }
            }

            is NotesEvent.SaveNote -> {
                val note = Note(
                    title = noteState.value.title.value,
                    content = noteState.value.content.value,
                    dateCreated = Utils.generateDate()
                )
                viewModelScope.launch {
                    noteDao.insertNote(note)
                }

                _noteState.update {
                    it.copy(
                        title = mutableStateOf(""),
                        content = mutableStateOf("")
                    )
                }
            }

            is NotesEvent.SortNotes -> {
                isSortedByDateCreated.value = !isSortedByDateCreated.value
            }

            is NotesEvent.DeleteAllNote -> {
                viewModelScope.launch {
                    noteDao.deleteAllNotes()
                }
            }
        }
    }

    fun updateNote(title: String, content: String) {
        val previousNoteId = selectedNote.value?.id ?: return
        val previousNoteDate = selectedNote.value?.dateCreated ?: return
        val updatedNote = Note(
            id = previousNoteId,
            title = title,
            content = content,
            dateCreated = previousNoteDate
        )

        viewModelScope.launch {
            noteDao.updateNote(updatedNote)
        }

        setSelectedNote(updatedNote)
    }

    fun setSelectedNote(note: Note?) {
        _selectedNote.value = note
    }
}