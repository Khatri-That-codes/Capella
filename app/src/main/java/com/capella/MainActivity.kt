package com.capella

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.capella.navigation.BottomBar
import com.capella.navigation.NavGraph
import com.capella.ui.theme.CapellaTheme
import androidx.navigation.compose.currentBackStackEntryAsState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CapellaTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                //hiding bottom bar on welcome screen
                val showBottomBar = currentRoute != "welcome"

                Scaffold(
                    modifier = Modifier,
                    bottomBar = {
                        if (showBottomBar) {
                            BottomBar(navController = navController)
                        }
                    },
                    content = { innerPadding: PaddingValues ->
                        NavGraph(
                            navController = navController,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                )
            }
        }
    }
}
