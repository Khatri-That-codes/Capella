package com.capella.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mood_entry")
data class MoodEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val date: String,

    // need to store the timestamp when mood was recorded
    val timestamp: Long ,// need to check the format

    val moodEmotion: String

)

