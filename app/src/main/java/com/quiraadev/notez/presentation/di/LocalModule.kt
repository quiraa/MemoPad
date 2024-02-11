package com.quiraadev.notez.presentation.di

import android.content.Context
import androidx.room.Room
import com.quiraadev.notez.data.local.NoteDatabase
import com.quiraadev.notez.data.local.dao.NoteDao
import com.quiraadev.notez.data.local.dao.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): NoteDatabase = Room.databaseBuilder(
        context,
        NoteDatabase::class.java,
        "note.db"
    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

    @Provides
    fun provideNoteDao(database: NoteDatabase): NoteDao = database.noteDao()

    @Provides
    fun provideTodoDao(database: NoteDatabase): TodoDao = database.todoDao()
}