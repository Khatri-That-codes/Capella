package com.capella.repository

import com.capella.AppDatabase
import android.content.Context
import com.capella.models.JournalEntry

class JournalEntryRepository(context : Context) {

    private val journalDao = AppDatabase.getDatabase(context).journalDao()

    suspend fun insertJournalEntry(journalEntry: JournalEntry) {
        journalDao.insertJournalEntry(journalEntry)
    }
    suspend fun getAllJournalEntries(): List<JournalEntry> {
        return journalDao.getAllJournalEntries()
    }

}