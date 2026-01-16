package com.capella.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.capella.models.JournalEntry


@Dao
interface JournalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournalEntry(journalEntry: JournalEntry)


    @Query("SELECT * FROM journal_entry")
    suspend fun getAllJournalEntries(): List<JournalEntry>
}