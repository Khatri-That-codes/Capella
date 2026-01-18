package com.capella.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


// need to check the details required for user
@Parcelize
@Entity(tableName = "journal_entry")
data class JournalEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val date: String,

    // need to store the timestamp when message was created
    val timestamp: Long ,// need to check the format

    val message:String,

    val gratefulMessage: String



) : Parcelable