package com.quiraadev.notez.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.quiraadev.notez.data.local.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun dao(): NoteDao
}