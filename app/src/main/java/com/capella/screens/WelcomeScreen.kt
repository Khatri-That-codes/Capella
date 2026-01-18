package com.capella.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capella.R
import com.capella.ui.theme.CapellaTheme

class WelcomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CapellaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WelcomeScreen(
                        modifier = Modifier.padding(innerPadding),
                        onContinue = {
                            val context = this@WelcomeScreen
                            context.startActivity(Intent(context, Home::class.java))
                            finish()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen(modifier: Modifier = Modifier,
                  onContinue: () -> Unit) { // need to check if i need modifier here

    val context = LocalContext.current   // having context to know the current state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement =  Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        //Capella Logo
        androidx.compose.foundation.Image(
            painter = painterResource(id = R.drawable.capella_logo),
            contentDescription = "Capella Logo",
            modifier = Modifier.size(200.dp)
        )
        Text(
            text = "Capella",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 30.sp
        )



        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text =
            "For your Daily Mood Check-ins and Journaling ",
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,

            )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                onContinue()

              // context.startActivity(Intent(context, Home::class.java))


            }
        ) {
            Text(
                text = "Let's Start ",
                fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(30.dp))



    }
}


