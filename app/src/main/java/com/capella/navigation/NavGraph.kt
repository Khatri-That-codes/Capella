package com.capella.navigation

import com.capella.screens.DailyLogScreen
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.currentBackStackEntryAsState
import com.capella.screens.HomeScreen
import com.capella.screens.MoodSelectionScreen
import com.capella.screens.ViewGratefulMessage
import com.capella.screens.WelcomeScreen
import com.capella.screens.ToDo
import com.capella.screens.ToDoScreen
import com.capella.screens.ViewAllJournalEntriesScreen
import com.capella.viewModel.JournalEntryViewModel
import com.capella.viewModel.MoodEntryViewModel

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),

) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    NavHost(navController = navController, startDestination = "welcome", modifier = modifier) {
        composable("welcome") {
            WelcomeScreen(onContinue = {
                navController.navigate("home") {
                    popUpTo("welcome") { inclusive = true }
                }
            })
        }

    composable("Home") {
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
                snackbarHostState = snackbarHostState,
                scope = scope,
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
                },
                snackbarHostState = snackbarHostState,
                scope = scope
            )
        }

        composable("Journals") {

            val context = LocalContext.current
            val journalEntryViewModel: JournalEntryViewModel = viewModel(
                factory = JournalEntryViewModel.JournalEntryViewModelFactory(context as androidx.activity.ComponentActivity)
            )
            ViewAllJournalEntriesScreen(modifier,journalEntryViewModel, onBack = {
                navController.navigate("home") {
                    popUpTo("home") { inclusive = false }
                }
            })
        }

        composable("To-Do List") {
            ToDoScreen(modifier = Modifier,
                onBack = {
                    // navigating back to home upon back press
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = false }
                    }
                }
            )
        }
    }
}
