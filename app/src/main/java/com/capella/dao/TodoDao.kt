package com.capella.dao

import androidx.room.*
import com.capella.models.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo ORDER BY id ASC")
    fun getAll(): Flow<List<TodoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TodoEntity): Long

    @Update
    suspend fun update(entity: TodoEntity)

    @Delete
    suspend fun delete(entity: TodoEntity)
}
