package com.quiraadev.notez.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quiraadev.notez.data.local.dao.TodoDao
import com.quiraadev.notez.data.local.model.Todo
import com.quiraadev.notez.presentation.common.TodoEvent
import com.quiraadev.notez.presentation.common.TodoState
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

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoDao: TodoDao
) : ViewModel() {

    private val _selectedTodo = MutableStateFlow<Todo?>(null)
    val selectedTodo = _selectedTodo.asStateFlow()

    private val isSortedByTask = MutableStateFlow(true)

    private var todos = isSortedByTask.flatMapLatest { _ ->
        todoDao.getTodosOrderByTask()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _todoState = MutableStateFlow(TodoState())
    val todoState = combine(_todoState, isSortedByTask, todos) { state, _, todos ->
        state.copy(
            todos = todos
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(500), TodoState())

    fun onTodoEvent(event: TodoEvent) {
        when (event) {
            is TodoEvent.DeleteAllTodo -> {
                viewModelScope.launch {
                    todoDao.deleteAllTodo()
                }
            }

            is TodoEvent.DoneTodo -> {
                viewModelScope.launch {
                    todoDao.deleteTodo(event.todo)
                }
            }

            is TodoEvent.SaveTodo -> {
                val todo = Todo(
                    task = todoState.value.task.value,
                    isImportant = todoState.value.isImportant.value,
                )

                viewModelScope.launch {
                    todoDao.insertTodo(todo)
                }

                _todoState.update {
                    it.copy(
                        task = mutableStateOf(""),
                        isImportant = mutableStateOf(false),
                    )
                }
            }
        }
    }

    fun updateTodo(task: String, isImportant: Boolean) {
        val previousTodoId = selectedTodo.value?.todoId ?: return
        val updatedTodo = Todo(
            todoId = previousTodoId,
            task = task,
            isImportant = isImportant
        )

        viewModelScope.launch {
            todoDao.updateTodo(updatedTodo)
        }

        setSelectedTodo(updatedTodo)
    }

    fun setSelectedTodo(todo: Todo?) {
        _selectedTodo.value = todo
    }
}