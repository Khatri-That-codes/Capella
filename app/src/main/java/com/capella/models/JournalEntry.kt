package com.capella.models

import androidx.room.Entity
import androidx.room.PrimaryKey


// need to check the details required for user

@Entity(tableName = "journal_entry")
data class JournalEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val date: String,

    // need to store the timestamp when message was created
    val timestamp: Long ,// need to check the format

    val message:String,

    val gratefulMessage: String



)