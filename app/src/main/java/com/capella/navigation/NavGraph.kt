package com.capella.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import com.capella.screens.HomeScreen
import com.capella.screens.DailyLogScreen
import com.capella.screens.MoodSelectionScreen
import com.capella.screens.ViewGratefulMessage
import com.capella.screens.WelcomeScreen
import com.capella.screens.ToDo
import com.capella.viewModel.JournalEntryViewModel
import com.capella.viewModel.MoodEntryViewModel

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),

) {
    NavHost(navController = navController, startDestination = "welcome", modifier = modifier) {
        composable("welcome") {
            WelcomeScreen(onContinue = {
                navController.navigate("home") {
                    popUpTo("welcome") { inclusive = true }
                }
            })
        }

        composable("home") {
            HomeScreen(
                modifier = Modifier
            )
        }

        composable("daily_log") {
            // obtain ViewModel using activity context (MainActivity is ComponentActivity)
            val context = LocalContext.current
            val journalEntryViewModel: JournalEntryViewModel = viewModel(
                factory = JournalEntryViewModel.JournalEntryViewModelFactory(context as androidx.activity.ComponentActivity)
            )

            DailyLogScreen(
                journalEntryViewModel = journalEntryViewModel,
                onSaved = {
                    // returning to home after save
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = false }
                    }
                }
            )
        }

        composable("mood_checkin") {
            val context = LocalContext.current
            val moodEntryViewModel: MoodEntryViewModel = viewModel(
                factory = MoodEntryViewModel.MoodEntryViewModelFactory(context as androidx.activity.ComponentActivity)
            )

            MoodSelectionScreen(
                moodEntryViewModel = moodEntryViewModel,
                onSaved = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = false }
                    }
                }
            )
        }

        composable("journals") {
            // your journal list / grateful messages screen (assumes a composable entry point)
            ViewGratefulMessage()
        }

        composable("Todo") {
            ToDo()
        }
    }
}
