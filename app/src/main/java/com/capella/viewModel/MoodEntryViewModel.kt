package com.capella.viewModel

import androidx.lifecycle.ViewModel
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.capella.repository.MoodEntryRepository
import com.capella.models.MoodEntry
import kotlinx.coroutines.launch


class MoodEntryViewModel(context: Context) : ViewModel() {

    private val repository = MoodEntryRepository(context)

    fun insertMoodEntry(moodEntry: MoodEntry) {
        // Using viewModelScope to launch a coroutine for database operation
        viewModelScope.launch {
            repository.insertMoodEntry(moodEntry)
        }
    }

    suspend fun getAllMoodEntries(): List<MoodEntry> {
        return repository.getAllMoodEntries()
    }


    fun createMoodEntry(
        date: String,
        time: Long,
        moodEmotion : String

    ){

        val moodEntry = MoodEntry(
            id = 0, // Assuming 0 means auto-generate
            date = date,
            timestamp = time,
            moodEmotion = moodEmotion
        )

        insertMoodEntry(moodEntry)
    }


    class MoodEntryViewModelFactory(context: Context): ViewModelProvider.Factory{
        private val context = context.applicationContext
        override fun<T : ViewModel> create(modelClass: Class<T>): T = MoodEntryViewModel(context) as T
    }
}

