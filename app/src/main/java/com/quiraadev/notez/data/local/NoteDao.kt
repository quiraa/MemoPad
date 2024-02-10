package com.quiraadev.notez.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.quiraadev.notez.data.local.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Upsert
    suspend fun upsertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM tbl_note")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM tbl_note ORDER BY dateCreated")
    fun getNotesOrderByDate(): Flow<List<Note>>

    @Query("SELECT * FROM tbl_note WHERE id = :noteId")
    fun getSingleNote(noteId: Int): Flow<Note>

    @Query("SELECT * FROM tbl_note ORDER BY title ASC")
    fun getNotesOrderByTitle(): Flow<List<Note>>
}