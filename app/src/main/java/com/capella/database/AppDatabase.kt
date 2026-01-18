package com.capella.database

import androidx.room.Room
import androidx.room.Database
import androidx.room.RoomDatabase
import com.capella.dao.JournalDao
import com.capella.dao.MoodDao
import com.capella.models.JournalEntry
import com.capella.models.MoodEntry


@Database(entities = [MoodEntry::class, JournalEntry::class], version = 1, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {
    abstract fun moodDao(): MoodDao
    abstract fun journalDao(): JournalDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: android.content.Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}