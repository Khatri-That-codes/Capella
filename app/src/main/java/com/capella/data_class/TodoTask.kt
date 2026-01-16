package com.capella.data_class

data class TodoTask(
    val id: Int,
    val description: String,
    val isDone: Boolean = false
)
