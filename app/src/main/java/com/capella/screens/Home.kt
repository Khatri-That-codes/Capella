// kotlin
package com.capella.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp

import com.capella.ui.theme.CapellaTheme
import com.capella.viewModel.QuoteViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


class Home : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CapellaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}





@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: QuoteViewModel = viewModel()) {
    val username = "Buddy 😋"
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Hey, $username!",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        HorizontalDivider()

        // Quote area
        when {
            uiState.isLoading -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator()
                }
            }
            uiState.error != null -> {
                Text(
                    text = "Failed to load quote: ${uiState.error}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
            uiState.quote != null -> {
                QuoteCard(quote = uiState.quote!!, onRefresh = { viewModel.refresh() })
            }
        }

        HorizontalDivider()

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { context.startActivity(Intent(context, DailyLog::class.java)) }) {
                Text("Daily Log")
            }
            Button(onClick = { context.startActivity(Intent(context,MoodCheckIn::class.java)) }, modifier = Modifier.padding(start = 12.dp)) {
                Text("Mood Check-In")
            }
        }

        Button(onClick = { context.startActivity(Intent(context,ViewGratefulMessage::class.java)) }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("View Wholesome Moments")
        }

        Button(onClick = { context.startActivity(Intent(context,ViewPreviousMood::class.java)) }, modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 12.dp)) {
            Text("View Recent Mood")
        }

    }
}

@Composable
private fun QuoteCard(quote: com.capella.models.Quote, onRefresh: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "“${quote.text}”",
                style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic)
            )
            Text(
                text = "- ${quote.author}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = onRefresh, modifier = Modifier.padding(top = 12.dp)) {
                    Text("Another")
                }
            }
        }
    }
}
