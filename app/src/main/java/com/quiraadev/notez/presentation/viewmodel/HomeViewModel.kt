@file:OptIn(ExperimentalCoroutinesApi::class)

package com.quiraadev.notez.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quiraadev.notez.data.local.NoteDao
import com.quiraadev.notez.data.local.model.Note
import com.quiraadev.notez.presentation.events.NotesEvent
import com.quiraadev.notez.presentation.states.NoteState
import com.quiraadev.notez.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dao: NoteDao
) : ViewModel() {

    private val isSortedByDateCreated = MutableStateFlow(true)

    private var notes = isSortedByDateCreated.flatMapLatest { isSorted ->
        if (isSorted) dao.getNotesOrderByDate()
        else dao.getNotesOrderByTitle()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val _state = MutableStateFlow(NoteState())
    val state = combine(_state, isSortedByDateCreated, notes) { state, isSorted, notes ->
        state.copy(
            notes = notes
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(500), NoteState())

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    dao.deleteNote(event.note)
                }
            }

            is NotesEvent.SaveNote -> {
                val note = Note(
                    title = state.value.title.value,
                    content = state.value.content.value,
                    dateCreated = Utils.generateDate()
                )
                viewModelScope.launch {
                    dao.upsertNote(note)
                }

                _state.update {
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
                    dao.deleteAllNotes()
                }
            }

            is NotesEvent.UpdateNote -> {

            }
        }
    }
}