package com.capella.viewModel

import androidx.lifecycle.ViewModel
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.capella.repository.JournalEntryRepository
import com.capella.models.JournalEntry
import kotlinx.coroutines.launch

class JournalEntryViewModel(context: Context) : ViewModel() {

    private val repository = JournalEntryRepository(context)

    fun insertJournalEntry(journalEntry: JournalEntry) {
        // Using viewModelScope to launch a coroutine for database operation
        viewModelScope.launch {
            repository.insertJournalEntry(journalEntry)
        }
    }

    suspend fun getAllJournalEntries(): List<JournalEntry> {
        return repository.getAllJournalEntries()
    }


    fun createJournalEntry(
        date: String,
        time: Long,
        message : String,
        gratefulMessage: String){

        val journalEntry = JournalEntry(
            id = 0, // Assuming 0 means auto-generate
            date = date,
            timestamp = time,
            message = message,
            gratefulMessage = gratefulMessage
        )

        insertJournalEntry(journalEntry)
    }

    class JournalEntryViewModelFactory(context: Context): ViewModelProvider.Factory{
        private val context = context.applicationContext
        override fun<T : ViewModel> create(modelClass: Class<T>): T = JournalEntryViewModel(context) as T
    }

}