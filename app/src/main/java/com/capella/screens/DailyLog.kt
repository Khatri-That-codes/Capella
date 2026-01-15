package com.capella.screens

import android.content.Context
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capella.ui.theme.CapellaTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DailyLog :ComponentActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            CapellaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DailyLogScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

    }
}

@Composable
fun DailyLogScreen(modifier: Modifier = Modifier){
    val context = LocalContext.current
    val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
    val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement =  Arrangement.Center
    ){

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Daily Log",
            fontSize = 24.sp
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
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
        ){
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
        ){
            Text(
                text = "Mood:",
                fontSize = 18.sp
            )

        }

    }


}

@Composable
fun moodDropDownMenu(context: Context){

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
    }

}