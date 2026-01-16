package com.capella.repository

import android.content.Context
import com.capella.AppDatabase
import com.capella.models.MoodEntry
import com.capella.dao.MoodDao

class MoodEntryRepository(context: Context) {

    private val moodDao: MoodDao = AppDatabase.getDatabase(context).moodDao()

    suspend fun insertMoodEntry(moodEntry: MoodEntry) {
        moodDao.insertMoodEntry(moodEntry)
    }

    suspend fun getAllMoodEntries(): List<MoodEntry> {
        return moodDao.getAllMoodEntries()
    }

//    suspend fun deleteMoodEntryById(moodEntryId: Int) {
//        moodDao.deleteMoodEntryById(moodEntryId)
//    }

}