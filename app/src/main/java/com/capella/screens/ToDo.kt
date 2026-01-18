package com.capella.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capella.ui.theme.CapellaTheme
import com.capella.data_class.TodoTask
import com.capella.navigation.AppScaffold
import com.capella.viewModel.TodoViewModel

class ToDo: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CapellaTheme {
                AppScaffold(title = "To-Do", showTopBar = true) { innerPadding ->
                    ToDoScreen(modifier = Modifier.padding(innerPadding), onBack = { finish() })
                }
            }
        }
    }
}

@Composable
fun ToDoScreen(modifier: Modifier = Modifier, onBack: () -> Unit) {
    val todoViewModel: TodoViewModel = viewModel()
    val tasksState = todoViewModel.tasks.collectAsState()
    val tasks = tasksState.value
    var textFieldState by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = textFieldState,
                onValueChange = { textFieldState = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Add a new task...") },
                singleLine = true
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (textFieldState.isNotBlank()) {
                        todoViewModel.addTask(textFieldState)
                        textFieldState = ""
                    }
                }
            ) {
                Text("Add")
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks, key = { it.id }) { task ->
                TodoItem(
                    task = task,
                    onToggleDone = { todoViewModel.toggleDone(task) },
                    onDelete = { todoViewModel.deleteTask(task) }
                )
            }
        }
    }
    BackHandler { onBack() }
}

@Composable
fun TodoItem(
    task: TodoTask,
    onToggleDone: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isDone,
                onCheckedChange = { onToggleDone() }
            )

            Text(
                text = task.description,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None,
                color = if (task.isDone) Color.Gray else Color.Black
            )

            IconButton(onClick = { onDelete() }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Task", tint = Color.Red)
            }
        }
    }
}
