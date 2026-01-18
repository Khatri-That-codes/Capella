package com.capella.repository

import com.capella.dao.TodoDao
import com.capella.models.TodoEntity
import com.capella.data_class.TodoTask

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepository(private val dao: TodoDao) {
    fun getAllTasks(): Flow<List<TodoTask>> =
        dao.getAll().map { list -> list.map { it.toTodoTask() } }

    suspend fun addTask(description: String) {
        dao.insert(TodoEntity(description = description))
    }

    suspend fun updateTask(task: TodoTask) {
        dao.update(task.toEntity())
    }

    suspend fun deleteTask(task: TodoTask) {
        dao.delete(task.toEntity())
    }

    private fun TodoEntity.toTodoTask() = TodoTask(id = id, description = description, isDone = isDone)
    private fun TodoTask.toEntity() = TodoEntity(id = id, description = description, isDone = isDone)
}