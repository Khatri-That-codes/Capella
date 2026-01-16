package com.capella.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.capella.ui.theme.CapellaTheme
import com.capella.data_class.TodoTask

class ToDo: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CapellaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ToDoScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ToDoScreen(modifier: Modifier = Modifier) {

    val tasks = remember {mutableStateListOf<TodoTask>()} // need to check if by or =
    var textFieldState by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "To-Do List",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        // tasks input area
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
                        tasks.add(TodoTask(id = tasks.size, description = textFieldState))
                        textFieldState = "" // Clear input
                    }
                }
            ) {
                Text("Add")
            }
        }

        // will store tasks using lazy column
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(tasks, key = { it.id }) { task ->
                TodoItem(
                    task = task,
                    onToggleDone = {
                        // Update the list state
                        val index = tasks.indexOf(task)
                        tasks[index] = task.copy(isDone = !task.isDone)
                    },
                    onDelete = { tasks.remove(task) }
                )
            }
        }
    }

}


@Composable
fun TodoItem(
    task: TodoTask,
    onToggleDone: () -> Unit,
    onDelete: () -> Unit
){
    Card(
        modifier = Modifier.fillMaxWidth(),

        // refer this to change card elevation
        elevation = cardElevation(defaultElevation = 2.dp)
    ){
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Checkbox(
                checked = task.isDone,
                onCheckedChange = { onToggleDone() }
            )

            Text(
                text = task.description,
                modifier = Modifier.weight(1f).padding(start = 8.dp),

                //strikthrough when task is done
                textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None,
                color = if (task.isDone) Color.Gray else Color.Black
            )
            IconButton(
                onClick = { onDelete() }
            ) {
                Icon(Icons.Default.Delete,
                    contentDescription = "Delete Task", tint = Color.Red)

            }
        }
    }

}