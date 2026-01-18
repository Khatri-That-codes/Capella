package com.capella

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.capella.navigation.BottomBar
import com.capella.navigation.NavGraph
import com.capella.ui.theme.CapellaTheme
import androidx.navigation.compose.currentBackStackEntryAsState
import com.capella.navigation.AppTopBar

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val showBottomBar = currentRoute != "welcome"
            val showTopBar = currentRoute != "welcome" // change condition as needed
            CapellaTheme {
            Scaffold(
                topBar = {
                    if (showTopBar) {
                        AppTopBar(currentRoute = currentRoute,
                            onNavClick = {
                                // Handle navigation icon click if needed
                                navController.navigateUp()
                            }
                        )
                    }
                },
//                        TopAppBar(title = { Text(text = currentRoute ?: "Capella ") },
//                            navigationIcon = {
//                                Image(
//                                    painter = painterResource(id = R.drawable.capella_logo),
//                                    contentDescription = "Logo",
//                                    modifier = Modifier.padding(8.dp)
//
//                                )
////                                // show back button if there is a previous entry
////                                if (navController.previousBackStackEntry != null) {
////                                    IconButton(onClick = { navController.navigateUp() }) {
////                                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
////                                    }
////                                }
//                            }
//                        )
//                    }
//                },
                bottomBar = {
                    if (showBottomBar) {
                        BottomBar(navController = navController)
                    }
                },
                content = { innerPadding ->
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



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(currentRoute: String?, onNavClick: () -> Unit = {}) {

    TopAppBar(
        title = { Text(text = when {
            currentRoute?.equals("Home", ignoreCase = true) == true -> "Capella"
            currentRoute != null -> {

                currentRoute
            }
            else -> "Capella"
        }) },
        navigationIcon = {
            IconButton(
                onClick = onNavClick,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.capella_logo),
                    contentDescription = "Capella logo",
                    modifier = Modifier
                        .padding(4.dp)   // small inner padding
                        .size(32.dp),    // recommended size (24-40.dp)
                    contentScale = ContentScale.Fit
                )
            }
        }
    )
}
