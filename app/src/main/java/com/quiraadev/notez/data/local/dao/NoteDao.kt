package com.quiraadev.notez.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.quiraadev.notez.data.local.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM tbl_note")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM tbl_note WHERE id = :noteId")
    fun getSingleNote(noteId: Int): Flow<Note>

    @Query("SELECT * FROM tbl_note ORDER BY dateCreated")
    fun getNotesOrderByDate(): Flow<List<Note>>

    @Query("SELECT * FROM tbl_note ORDER BY title ASC")
    fun getNotesOrderByTitle(): Flow<List<Note>>
}