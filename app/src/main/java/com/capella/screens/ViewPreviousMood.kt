package com.capella.screens

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler

// this file is for a screen that shows all previous moods , going from
// most recent to least recent mood entries


import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row


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
import com.capella.models.MoodEntry
import com.capella.navigation.AppScaffold
import com.capella.ui.theme.CapellaTheme
import com.capella.viewModel.MoodEntryViewModel
class ViewPreviousMood : ComponentActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val moodEntryViewModel: MoodEntryViewModel = ViewModelProvider(
            this,
            MoodEntryViewModel.MoodEntryViewModelFactory(this@ViewPreviousMood)
        )[MoodEntryViewModel::class.java]

        setContent {
            CapellaTheme {
                AppScaffold(title = "Previous Moods", showTopBar = true) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        PreviousMoodScreen(
                            modifier = Modifier.padding(16.dp),
                            moodEntryViewModel = moodEntryViewModel,
                            onBack = { finish() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PreviousMoodScreen(
    modifier: Modifier = Modifier,
    moodEntryViewModel: MoodEntryViewModel,
    onBack: () -> Unit
) {
    var isLoading by rememberSaveable { mutableStateOf(true) }
    var moodEntries by rememberSaveable { mutableStateOf(listOf<MoodEntry>()) }

    LaunchedEffect(Unit) {
        moodEntries = moodEntryViewModel.getAllMoodEntries().sortedByDescending { it.date }
        isLoading = false
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

        } else {
            if (moodEntries.isEmpty()) {
                Text(
                    text = "No previous mood entries found.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(moodEntries) { entry ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Date: ${entry.date}",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Time: ${entry.timestamp}",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))


                                Text(
                                    text = "Mood: ${entry.moodEmotion}",
                                    fontSize = 16.sp
                                )


                                Spacer(modifier = Modifier.height(4.dp))

                            }
                        }
                    }
                }
            }
        }
    }
    BackHandler {
        onBack()
    }
}



