@file:Suppress("UNCHECKED_CAST")

package com.una.exam_frontend.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.una.exam_frontend.viewmodel.CourseViewModel
import com.una.exam_frontend.models.Course
import com.una.exam_frontend.repository.CourseRepository
import com.una.exam_frontend.models.AppDatabase

@Composable
fun CourseListScreen(navController: NavHostController) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val repository = remember { CourseRepository(context, db.courseDao()) }
    val viewModel: CourseViewModel = viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CourseViewModel(repository) as T
        }
    })
    val courses by viewModel.courses.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var newCourseName by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadCourses()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Text("+")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            Text("Cursos", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(16.dp))
            LazyColumn {
                items(courses.size) { idx ->
                    val course = courses[idx]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { navController.navigate("students/${course.id}") }
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(course.name)
                            IconButton(onClick = { viewModel.deleteCourse(course.id) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                            }
                        }
                    }
                }
            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Nuevo Curso") },
                text = {
                    OutlinedTextField(
                        value = newCourseName,
                        onValueChange = { newCourseName = it },
                        label = { Text("Nombre del curso") }
                    )
                },
                confirmButton = {
                    Button(onClick = {
                        if (newCourseName.isNotBlank()) {
                            viewModel.addCourse(
                                Course(
                                    id = 0, // Room y el backend deben asignar el ID
                                    name = newCourseName,
                                    description = "",
                                    imageUrl = "",
                                    schedule = "",
                                    professor = ""
                                )
                            )
                            newCourseName = ""
                            showDialog = false
                        }
                    }) { Text("Agregar") }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) { Text("Cancelar") }
                }
            )
        }
    }
}