@file:Suppress("UNCHECKED_CAST")

package com.una.exam_frontend.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.una.exam_frontend.interfaces.StudentDao
import com.una.exam_frontend.viewmodel.StudentViewModel
import com.una.exam_frontend.repository.StudentRepository
import com.una.exam_frontend.models.AppDatabase
import com.una.exam_frontend.models.Student

@SuppressLint("RememberReturnType")
@Composable
fun StudentListScreen(
    navController: NavHostController,
    courseId: Int
) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val repository = remember { StudentRepository(context = context, studentDao = db.studentDao()) }
    val viewModel: StudentViewModel = viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return StudentViewModel(repository) as T
        }
    })

    val students by viewModel.students.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var newStudentName by remember { mutableStateOf("") }

    LaunchedEffect(courseId) {
        viewModel.loadStudents(courseId)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Text("+")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            Text("Estudiantes", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(16.dp))
            LazyColumn {
                items(students.size) { idx ->
                    val student = students[idx]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { navController.navigate("studentDetail/${student.id}") }
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(student.name)
                            IconButton(onClick = { viewModel.deleteStudent(student.id, courseId) }) {
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
                title = { Text("Nuevo Estudiante") },
                text = {
                    OutlinedTextField(
                        value = newStudentName,
                        onValueChange = { newStudentName = it },
                        label = { Text("Nombre del estudiante") }
                    )
                },
                confirmButton = {
                    Button(onClick = {
                        if (newStudentName.isNotBlank()) {
                            val email = ""
                            val phone = ""
                            viewModel.addStudent(
                                Student(
                                    id = 0, // Room y el backend asignan el ID
                                    name = newStudentName,
                                    email = email,
                                    phone = phone,
                                    courseId = courseId,

                                )
                            )
                            newStudentName = ""
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


