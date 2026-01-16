package com.capella.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.capella.models.JournalEntry


@Dao
interface JournalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournalEntry(entry: JournalEntry)

    @Query("SELECT * FROM journal_entry")
    suspend fun getAllJournalEntries(): List<JournalEntry>

    @Query("SELECT * FROM journal_entry WHERE id = :id LIMIT 1")
    suspend fun getJournalEntryById(id: Int): JournalEntry?

    @Query("DELETE FROM journal_entry WHERE id = :id")
    suspend fun deleteById(id: Int): Int


}