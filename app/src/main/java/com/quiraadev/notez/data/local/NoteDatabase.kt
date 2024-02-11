package com.quiraadev.notez.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.quiraadev.notez.data.local.dao.NoteDao
import com.quiraadev.notez.data.local.dao.TodoDao
import com.quiraadev.notez.data.local.model.Note
import com.quiraadev.notez.data.local.model.Todo

@Database(entities = [Note::class, Todo::class], version = 3, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun todoDao() : TodoDao
}