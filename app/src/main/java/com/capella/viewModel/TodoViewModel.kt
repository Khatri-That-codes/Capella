
package com.capella.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.capella.repository.TodoRepository
import com.capella.database.TodoDatabase
import com.capella.data_class.TodoTask
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TodoRepository(TodoDatabase.getInstance(application).todoDao())

    val tasks: StateFlow<List<TodoTask>> =
        repository.getAllTasks().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addTask(description: String) {
        if (description.isBlank()) return
        viewModelScope.launch { repository.addTask(description) }
    }

    fun toggleDone(task: TodoTask) {
        viewModelScope.launch {
            repository.updateTask(task.copy(isDone = !task.isDone))
        }
    }

    fun deleteTask(task: TodoTask) {
        viewModelScope.launch { repository.deleteTask(task) }
    }
}

