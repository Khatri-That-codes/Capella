package com.capella.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.capella.models.MoodEntry

@Dao
interface MoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoodEntry(moodEntry: MoodEntry)

    @Query("SELECT * FROM mood_entry")
    suspend fun getAllMoodEntries(): List<MoodEntry>

    @Query("DELETE FROM mood_entry WHERE id = :moodEntryId")
    suspend fun deleteMoodEntryById(moodEntryId: Int)
}