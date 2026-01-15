package com.capella.data_class

data class TodoTask(
    val int: Int,
    val description: String,
    val isDone: Boolean = false
)
