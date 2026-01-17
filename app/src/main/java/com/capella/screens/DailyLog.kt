package com.capella.screens

import android.content.Context
import android.widget.Space
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import com.capella.ui.theme.CapellaTheme
import com.capella.viewModel.JournalEntryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Long
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DailyLog :ComponentActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val journalEntryViewModel: JournalEntryViewModel = ViewModelProvider(
            this,
            JournalEntryViewModel.JournalEntryViewModelFactory(this@DailyLog)
        )[JournalEntryViewModel::class.java]

        setContent{
            CapellaTheme {

                val snackbarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()

                Scaffold(modifier = Modifier.fillMaxSize(),
                    // snackbar code
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState){
                            data ->
                        Snackbar(
                            snackbarData = data,
                            containerColor = Color(0xFF7682C0), // light purple snackbar colour
                            contentColor = Color.LightGray
                        )
                    }
                    }
                    ) { innerPadding ->
                    DailyLogScreen(
                        modifier = Modifier.padding(innerPadding),
                        journalEntryViewModel = journalEntryViewModel,
                        onSaved = {
                            finish() // to return to home screen after saving

                        },
                        snackbarHostState = snackbarHostState,
                        scope = scope
                    )
                }
            }
        }

    }
}


@Composable
fun DailyLogScreen(
    modifier: Modifier = Modifier,
                   journalEntryViewModel: JournalEntryViewModel,
                   onSaved: () ->Unit,
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
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Daily Log",
            fontSize = 24.sp
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text( // might need adjustment
                text = "Date:",
                fontSize = 18.sp
            )
            Text(
                text = currentDate,
                fontSize = 18.sp
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Time",
                fontSize = 18.sp
            )
            Text(
                text = currentTime,
                fontSize = 18.sp
            )

        }


        // Mood Section - do i need it?
        Row(

            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Mood:",
                fontSize = 18.sp
            )
            userMood = moodDropDownMenu(context)

        }

        // message writing section
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Today's Feelings:",
                fontSize = 24.sp,

                )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            OutlinedTextField(
                value = dailyMessage,
                onValueChange = { dailyMessage = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                placeholder = { Text("How is your day going? ...") }
            )
        }
        Spacer(modifier = Modifier.height(40.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Wholesome Moment",
                fontSize = 24.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            OutlinedTextField(
                value = wholesomeMoment,
                onValueChange = { wholesomeMoment = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                placeholder = { Text("Share a positive moment from today...") }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // save button to save the journal entry to database
            Button(
                onClick = {

                    val entry = JournalEntry(
                        id = 0, // auto-generated ID
                        date = currentDate,
                        timestamp = System.currentTimeMillis(),
                        gratefulMessage = wholesomeMoment,
                        message = dailyMessage
                    )

                    journalEntryViewModel.insertJournalEntry(entry)


//                    // successful toast
//                    Toast.makeText(
//                        context,
//                        "Journal Entry Saved! \n Keep it up! You're doing great! \uD83D\uDE0A",
//                        Toast.LENGTH_LONG
//                    ).show()

                    scope.launch {

                        snackbarHostState.showSnackbar(
                            message = "Journal Entry Saved! \n Keep it up! You're doing great! 😊",
                            duration = SnackbarDuration.Short,
                        )
                    }

                    onSaved()
                }
            ){
                Text(
                    text = "Save Entry",
                    fontSize = 18.sp
                )
            }
//            Text(
//                text = "Keep it up! You're doing great! 😊",
//                fontSize = 18.sp
//            )
        }
    }
}




//mood drop down function
@Composable
fun moodDropDownMenu(context: Context): String {

    var expanded by remember {mutableStateOf(false) }
    var userMood by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize()){

        OutlinedTextField(
            value = userMood,
            onValueChange = {userMood = it},
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            readOnly = true,

            leadingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = "arrow down"
                    )
                }
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {

            //might need to addd more moods
            val moods = listOf("Happy", "Sad", "Angry", "Excited", "Anxious", "Relaxed")
            moods.forEach { mood ->
                androidx.compose.material3.DropdownMenuItem(
                    text = { Text(mood) },
                    onClick = {
                        userMood = mood
                        expanded = false
                    }
                )
                HorizontalDivider()
            }
        }
    }

    return userMood

}