package com.capella.screens

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.capella.ui.theme.CapellaTheme
import com.capella.data_class.Emotion
import com.capella.data_class.emotions
import com.capella.models.MoodEntry
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.capella.viewModel.MoodEntryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class MoodCheckIn : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val moodEntryViewModel: MoodEntryViewModel = ViewModelProvider(
            this,
            MoodEntryViewModel.MoodEntryViewModelFactory(this@MoodCheckIn)
        )[MoodEntryViewModel::class.java]
        setContent {
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
                    MoodSelectionScreen(
                        modifier = Modifier.padding(innerPadding),
                        moodEntryViewModel = moodEntryViewModel,
                        onSaved = {
                            finish() // to return to home (prev screen)
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
fun MoodSelectionScreen(
    modifier: Modifier = Modifier,
    moodEntryViewModel: MoodEntryViewModel,
    onSaved: () ->Unit,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope
)
{
    // State to keep track of which emotion is selected
    var selectedEmotion by remember { mutableStateOf<Emotion?>(null) }
    var currentContext = LocalContext.current



    val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
    val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "HOW ARE YOU FEELING?",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(vertical = 24.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(4), // having 4 items per row
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            items(emotions) { emotion ->
                EmotionItem(
                    emotion = emotion,
                    isSelected = selectedEmotion == emotion,
                    onSelect = { selectedEmotion = emotion }
                )
            }
        }




        selectedEmotion?.let {
            Button(
                onClick = {
                    val entry = MoodEntry(
                        id = 0,
                        date = currentDate,
                        timestamp = System.currentTimeMillis(),
                        moodEmotion = it.icon,

                        )

                    moodEntryViewModel.insertMoodEntry(entry)

                    scope.launch {

                        snackbarHostState.showSnackbar(
                            message = " Your mood '${it.icon}' has been recorded!",
                            duration = SnackbarDuration.Short,
                        )
                    }
                    //toast message
                  //  Toast.makeText(currentContext, "Mood '${it.icon}' saved!\n OK to be Moody!", Toast.LENGTH_SHORT).show()

                    onSaved()
                },
                modifier = Modifier.padding(top = 24.dp)
            ) {
                Text("Confirm Mood")
            }
        }
    }
}


@Composable
fun EmotionItem(
    emotion: Emotion,
    isSelected: Boolean,
    onSelect: (Emotion) -> Unit

){
    Column(
        modifier = Modifier
            .clickable { onSelect(emotion) }
            .padding(8.dp)
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) Color.Blue else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(
            text = emotion.icon,
            fontSize = 48.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = emotion.label,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}




