package com.capella.models
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val description: String,
    val isDone: Boolean = false
)