
package com.capella.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.capella.data_class.TodoTask

class TodoViewModel : ViewModel() {
    private val _tasks = mutableStateListOf<TodoTask>()
    val tasks: List<TodoTask> get() = _tasks

    fun addTask(description: String) {
        if (description.isBlank()) return
        val nextId = if (_tasks.isEmpty()) 0 else (_tasks.maxOf { it.id } + 1)
        _tasks.add(TodoTask(id = nextId, description = description))
    }

    fun toggleDone(task: TodoTask) {
        val idx = _tasks.indexOf(task)
        if (idx >= 0) _tasks[idx] = task.copy(isDone = !task.isDone)
    }

    fun deleteTask(task: TodoTask) {
        _tasks.remove(task)
    }
}
