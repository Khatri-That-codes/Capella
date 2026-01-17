package com.capella.screens


//  this screen displays all the journal entries in a recyclerview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.capella.models.JournalEntry
import com.capella.navigation.AppScaffold
import com.capella.ui.theme.CapellaTheme
import com.capella.viewModel.JournalEntryViewModel
import com.capella.viewModel.JournalEntryViewModel.JournalEntryViewModelFactory
import java.text.SimpleDateFormat
import java.util.Locale



class ViewGratefulMessage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val journalEntryViewModel: JournalEntryViewModel = ViewModelProvider(
            this,
            JournalEntryViewModelFactory(this@ViewGratefulMessage)
        )[JournalEntryViewModel::class.java]

        setContent {
            CapellaTheme {
                AppScaffold(title = "Wholesome Moments", showTopBar = true) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        GratefulMessageScreen(
                            modifier = Modifier.padding(16.dp),
                            journalEntryViewModel = journalEntryViewModel,
                            onBack = { finish() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GratefulMessageScreen(
    modifier: Modifier = Modifier,
    journalEntryViewModel: JournalEntryViewModel,
    onBack: () -> Unit
) {
    var isLoading by rememberSaveable { mutableStateOf(true) }
    var journalEntries by rememberSaveable { mutableStateOf(listOf<JournalEntry>()) }

    LaunchedEffect(Unit) {
        journalEntries = journalEntryViewModel.getAllJournalEntries()
        isLoading = false
    }
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        Text(
            text = "Your Wholesome Moments",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
        )
        if (isLoading) {
            // Show loading indicator while fetching data
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        } else {
            if (journalEntries.isEmpty()) {
                // Show message for no entries
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No Wholesome Moments Available",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                // using LazyColumn to display journal entries
                LazyColumn(
                    contentPadding = innerPadding,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(journalEntries) { entry ->
                        GratefulMessageCard(journalEntry = entry)
                    }
                }
            }
        }
    }
    BackHandler {
        onBack()
    }



}


@Composable
fun GratefulMessageCard(journalEntry: JournalEntry) {
    val formattedDate = try {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val date = parser.parse(journalEntry.date)
        if (date != null) {
            formatter.format(date)
        } else {
            journalEntry.date
        }
    } catch (e: Exception) {
        journalEntry.date
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Date: $formattedDate",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Grateful Message: ${journalEntry.gratefulMessage}",
                fontSize = 14.sp
            )
        }
    }
}