package com.capella.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.capella.ui.theme.CapellaTheme


class Home: ComponentActivity() {
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
fun HomeScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val username = "User" // Placeholder for username
    val motivationalQuote = "" // Placeholder quote

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        Text(
            text = "Welcome, $username!",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        HorizontalDivider()

        if (motivationalQuote.isNotEmpty()) {
            Text(
                text = "\"$motivationalQuote\"",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        HorizontalDivider()


        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),

            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
           Button(
               onClick = {
                   // Navigate to Daily Log Screen
               }
           ) {
                Text("Daily Log")

           }
              Button(
                onClick = {
                     // Navigate to Mood Check-In Screen
                }
              ) {
                 Text("Mood Check-In")

              }
        }

        Button(
            onClick = {
                // Navigate to Stats Screen
            }
        ) {
            Text("View Past Journals")

        }


    }
}
