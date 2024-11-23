
package com.example.todo_app.todo.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo_app.todo.viewmodel.TodoViewModel
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen() {
    var todoText by remember { mutableStateOf("") }
    val viewModel: TodoViewModel = viewModel()
    val todolist by viewModel.todo.collectAsState()
    val backgroundColor = Color(0xFF000C49)
    val complementaryColors = listOf(
        Color(0xFFFF6F61), // Coral
        Color(0xFF009688), // Teal
        Color(0xFFFFD700), // Gold
        Color(0xFFD3D3D3), // Light Gray
        Color(0xFFFFFFFF)  // White
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo App", fontSize = 20.sp, color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF000F5F)
                ),
                actions = {
                    IconButton(onClick = { /* Do something */ }) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorite",
                            tint = Color.Red
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = backgroundColor
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Input Section
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            OutlinedTextField(
                                value = todoText,
                                onValueChange = { todoText = it },
                                placeholder = { Text("Enter task") },
                                textStyle = TextStyle(color = Color.White),
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            ElevatedButton(
                                onClick = {   if (todoText.isNotBlank()) {
                                    viewModel.addTodo(todoText)
                                    todoText = "" // Clear the input field after adding a task
                                }},
                                modifier = Modifier
                                    .height(50.dp).padding(top = 4.dp)
                                    .border(
                                        width = 1.dp,
                                        color = Color.White,
                                        shape = RoundedCornerShape(8.dp)
                                    ),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.elevatedButtonColors(
                                    containerColor = Color(0xFF00137A)
                                )
                            ) {
                                Text(
                                    text = "Add",
                                    style = TextStyle(
                                        color = Color.White,
                                        fontWeight = FontWeight.W500,
                                        fontSize = 14.sp
                                    )
                                )
                            }
                        }
                    }

                    // Todo Items Section
                    items(todolist) { todo ->
                        val randColor = complementaryColors[Random.nextInt(complementaryColors.size)]
                        TodoCard(
                            color = randColor,
                            todos = todo.todo,
                            onDelete = { viewModel.deleteTodo(todo.id) } // Pass the unique id instead of the index
                        )
                    }
                }
            }
        }
    )
}
@Composable
fun TodoCard(color: Color,todos:String,onDelete: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = color, shape = RoundedCornerShape(10.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 20.dp, vertical = 10.dp), // Add padding inside the card
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = todos,
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.W500,
                    fontSize = 20.sp
                ),
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically) // Allow text to take available space
            )
            IconButton(
                onClick = { onDelete()},
                modifier = Modifier.align(Alignment.Top)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TodoScreen()
}

