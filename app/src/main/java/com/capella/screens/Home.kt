package com.capella.screens


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capella.navigation.AppScaffold
import com.capella.network.PicGenerator
import com.capella.ui.theme.CapellaTheme
import com.capella.viewModel.QuoteViewModel

class Home : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CapellaTheme {
                AppScaffold(title = "Daily Log", showTopBar = true) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        HomeScreen(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: QuoteViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hey, Buddy \uD83D\uDE0B",
            style = MaterialTheme.typography.headlineMedium
        )

        HorizontalDivider()

        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }

            // when there is an error to load quote -- will show a picture instead
            uiState.error != null -> {

                Image(
                    painter = painterResource(id = PicGenerator.randomRes()),
                    contentDescription = "MV  - Quote Placeholder",
                    modifier = Modifier.size(200.dp)
                )


//                Text(
//                    text = "Failed to load quote",
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = MaterialTheme.colorScheme.error
//                )
            }
            uiState.quote != null -> {
                QuoteCard(quote = uiState.quote!!, onRefresh = { viewModel.refresh() })
            }
        }

        // buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                HomeActionCard(
                    label = "Daily Log",
                    icon = Icons.Default.Edit,
                    containerColor = MaterialTheme.colorScheme.primary,
                    onClick = { context.startActivity(Intent(context, DailyLog::class.java)) },
                    modifier = Modifier.weight(1f)
                )
                HomeActionCard(
                    label = "Mood Check-In",
                    icon = Icons.Default.AddCircle,
                    containerColor = MaterialTheme.colorScheme.secondary,
                    onClick = { context.startActivity(Intent(context, MoodCheckIn::class.java)) },
                    modifier = Modifier.weight(1f)
                )
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                HomeActionCard(
                    label = "Wholesome Moments",
                    icon = Icons.Default.FavoriteBorder,
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    onClick = { context.startActivity(Intent(context, ViewGratefulMessage::class.java)) },
                    modifier = Modifier.weight(1f)
                )
                HomeActionCard(
                    label = "Recent Moods",
                    icon = Icons.Default.Face,
                    containerColor = MaterialTheme.colorScheme.error,
                    onClick = { context.startActivity(Intent(context, ViewPreviousMood::class.java)) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun QuoteCard(quote: com.capella.models.Quote, onRefresh: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "“${quote.text}”",
                style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic)
            )
            Text(
                text = "- ${quote.author}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.End).padding(top = 8.dp)
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                androidx.compose.material3.Button(onClick = onRefresh, modifier = Modifier.padding(top = 12.dp)) {
                    Text("Another")
                }
            }
        }
    }
}

@Composable
private fun HomeActionCard(
    label: String,
    icon: ImageVector,
    containerColor: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(140.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(36.dp)
            )
            Text(
                text = label,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }
}
