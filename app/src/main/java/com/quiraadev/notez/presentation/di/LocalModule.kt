package com.quiraadev.notez.presentation.di

import android.content.Context
import androidx.room.Room
import com.quiraadev.notez.data.local.NoteDao
import com.quiraadev.notez.data.local.NoteDatabase
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
    fun provideDao(database: NoteDatabase): NoteDao = database.dao()

}