package com.capella.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.capella.dao.TodoDao
import com.capella.models.TodoEntity

@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile private var INSTANCE: TodoDatabase? = null

        fun getInstance(context: Context): TodoDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_db"
                ).build().also { INSTANCE = it }
            }
    }
}