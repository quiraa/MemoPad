package com.quiraadev.notez.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.quiraadev.notez.data.local.model.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("DELETE FROM tbl_todo")
    suspend fun deleteAllTodo()

    @Query("SELECT * FROM tbl_todo WHERE todoId = :todoId")
    fun getTodoById(todoId: Int): Flow<Todo>

    @Query("SELECT * FROM tbl_todo ORDER BY task ASC")
    fun getTodosOrderByTask() : Flow<List<Todo>>

}