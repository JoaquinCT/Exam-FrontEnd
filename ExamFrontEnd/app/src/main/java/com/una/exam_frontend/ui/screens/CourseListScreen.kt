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
import coil.compose.AsyncImage
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

    // Campos del formulario
    var newCourseName by remember { mutableStateOf("") }
    var newCourseDescription by remember { mutableStateOf("") }
    var newCourseProfessor by remember { mutableStateOf("") }
    var newCourseSchedule by remember { mutableStateOf("") }
    var newCourseImageUrl by remember { mutableStateOf("") }

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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Cursos", style = MaterialTheme.typography.headlineMedium)
                Button(onClick = { viewModel.loadCourses() }) {
                    Text("Actualizar")
                }
            }

            LazyColumn {
                items(courses.size) { idx ->
                    val course = courses[idx]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { navController.navigate("students/${course.id}") }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Nombre: ${course.name}", style = MaterialTheme.typography.titleMedium)
                            Text("Descripción: ${course.description}")
                            Text("Profesor: ${course.professor}")
                            Text("Horario: ${course.schedule}")

                            AsyncImage(
                                model = course.imageUrl,
                                contentDescription = "Imagen del curso",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(onClick = {
                                    viewModel.deleteCourse(course.id)
                                    viewModel.loadCourses() // Recargar después de eliminar
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                                }
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
                    Column {
                        OutlinedTextField(
                            value = newCourseName,
                            onValueChange = { newCourseName = it },
                            label = { Text("Nombre del curso") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = newCourseDescription,
                            onValueChange = { newCourseDescription = it },
                            label = { Text("Descripción") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = newCourseProfessor,
                            onValueChange = { newCourseProfessor = it },
                            label = { Text("Profesor") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = newCourseSchedule,
                            onValueChange = { newCourseSchedule = it },
                            label = { Text("Horario") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = newCourseImageUrl,
                            onValueChange = { newCourseImageUrl = it },
                            label = { Text("URL de la imagen") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        if (newCourseName.isNotBlank()) {
                            viewModel.addCourse(
                                Course(
                                    id = 0,
                                    name = newCourseName,
                                    description = newCourseDescription,
                                    imageUrl = newCourseImageUrl,
                                    schedule = newCourseSchedule,
                                    professor = newCourseProfessor
                                )
                            )
                            // Limpiar campos
                            newCourseName = ""
                            newCourseDescription = ""
                            newCourseProfessor = ""
                            newCourseSchedule = ""
                            newCourseImageUrl = ""
                            showDialog = false
                            viewModel.loadCourses() // Recargar después de agregar
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
