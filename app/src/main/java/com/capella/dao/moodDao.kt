package com.capella.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.capella.models.MoodEntry

@Dao
interface MoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoodEntry(entry: MoodEntry)

    @Query("SELECT * FROM mood_entry")
    suspend fun getAllMoodEntries(): List<MoodEntry>

    @Query("SELECT * FROM mood_entry WHERE id = :id LIMIT 1")
    suspend fun getMoodEntryById(id: Int): MoodEntry?

    @Query("DELETE FROM mood_entry WHERE id = :id")
    suspend fun deleteById(id: Int): Int


}