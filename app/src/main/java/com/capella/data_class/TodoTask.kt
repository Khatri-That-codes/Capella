package com.capella.data_class

data class TodoTask(
    val id: Int = 0,
    val description: String,
    val isDone: Boolean = false
)

