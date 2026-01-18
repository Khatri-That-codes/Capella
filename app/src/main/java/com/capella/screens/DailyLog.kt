package com.capella.screens

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.capella.models.JournalEntry
import com.capella.navigation.AppScaffold
import com.capella.ui.theme.CapellaTheme
import com.capella.viewModel.JournalEntryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class DailyLog : ComponentActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val journalEntryViewModel: JournalEntryViewModel = ViewModelProvider(
            this,
            JournalEntryViewModel.JournalEntryViewModelFactory(this@DailyLog)
        )[JournalEntryViewModel::class.java]

        setContent {
            CapellaTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()


                AppScaffold(title = "Daily Log", showTopBar = true) { innerPadding ->

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        snackbarHost = {
                            SnackbarHost(hostState = snackbarHostState) { data ->
                                Snackbar(
                                    snackbarData = data,
                                    containerColor = Color(0xFF7682C0),
                                    contentColor = Color.LightGray
                                )
                            }
                        }
                    ) { contentPadding ->

                        //combining the modifier
                        val combinedModifier = Modifier
                            .padding(innerPadding)
                            .padding(contentPadding)

                        DailyLogScreen(
                            modifier = combinedModifier,
                            journalEntryViewModel = journalEntryViewModel,
                            onSaved = { finish()
                            },
                            snackbarHostState = snackbarHostState,
                            scope = scope
                        )
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyLogScreen(
    modifier: Modifier = Modifier,
    journalEntryViewModel: JournalEntryViewModel,
    onSaved: () -> Unit,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope
) {
    val context = LocalContext.current
    val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
    val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

    var userMood by remember { mutableStateOf("") }
    var dailyMessage by remember { mutableStateOf("") }
    var wholesomeMoment by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // <- make the page scrollable
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
            ) {
                Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                    Text(text = "Date: ", fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                    Text(text = currentDate, fontSize = 14.sp)
                }
            }

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                ),
                shape = RoundedCornerShape(16.dp),
            ) {
                Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                    Text(text = "Time: ", fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                    Text(text = currentTime, fontSize = 14.sp)
                }
            }
        }

        // Mood selection card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "Mood", fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
                userMood = moodDropDownMenu(context, initial = userMood)
            }
        }

        // Message input card (expanded) — removed .weight so it can scroll
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "Today's Feelings", fontSize = 16.sp)
                OutlinedTextField(
                    value = dailyMessage,
                    onValueChange = { dailyMessage = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    placeholder = { Text("How is your day going?") },
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Wholesome Moment", fontSize = 16.sp)
                OutlinedTextField(
                    value = wholesomeMoment,
                    onValueChange = { wholesomeMoment = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    placeholder = { Text("Share a positive moment from today...") },
                    shape = RoundedCornerShape(10.dp)
                )
            }
        }

        // Save button aligned bottom
        Box(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    val entry = JournalEntry(
                        id = 0,
                        date = currentDate,
                        timestamp = System.currentTimeMillis(),
                        gratefulMessage = wholesomeMoment,
                        message = dailyMessage
                    )
                    journalEntryViewModel.insertJournalEntry(entry)

                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Journal Entry Saved! Keep it up! 😊",
                            duration = SnackbarDuration.Long,
                        )
                    }
                    onSaved()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Save Entry", fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun moodDropDownMenu(context: Context, initial: String = ""): String {
    var expanded by remember { mutableStateOf(false) }
    var userMood by remember { mutableStateOf(initial) }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = userMood,
            onValueChange = { userMood = it },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            readOnly = true,
            placeholder = { Text("Select mood") },
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "arrow down")
                }
            },
            shape = RoundedCornerShape(10.dp)
        )

        androidx.compose.material3.DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            val moods = listOf("Happy", "Sad", "Angry", "Excited", "Anxious", "Relaxed")
            moods.forEach { mood ->
                androidx.compose.material3.DropdownMenuItem(
                    text = { Text(mood) },
                    onClick = {
                        userMood = mood
                        expanded = false
                    }
                )
            }
        }
    }

    return userMood
}
