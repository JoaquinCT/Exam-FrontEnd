@file:Suppress("UNCHECKED_CAST")

package com.una.exam_frontend.ui.screens

import android.annotation.SuppressLint
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
import com.una.exam_frontend.models.AppDatabase
import com.una.exam_frontend.models.Student
import com.una.exam_frontend.repository.StudentRepository
import com.una.exam_frontend.viewmodel.StudentViewModel

@SuppressLint("RememberReturnType")
@Composable
fun StudentListScreen(
    navController: NavHostController,
    courseId: Int
) {
    val context = LocalContext.current
    val db = AppDatabase.getInstance(context)
    val repository = remember { StudentRepository(context, db.studentDao()) }

    val viewModel: StudentViewModel = viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return StudentViewModel(repository) as T
        }
    })

    val students by viewModel.students.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    var newStudentName by remember { mutableStateOf("") }
    var newStudentEmail by remember { mutableStateOf("") }
    var newStudentPhone by remember { mutableStateOf("") }

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
        Column(modifier = Modifier
            .padding(padding)
            .fillMaxSize()) {

            Text(
                "Estudiantes",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn {
                items(students.size) { idx ->
                    val student = students[idx]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { navController.navigate("studentDetail/${student.id}") }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    student.name,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                IconButton(onClick = {
                                    viewModel.deleteStudent(student.id)
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Email: ${student.email}", style = MaterialTheme.typography.bodyMedium)
                            Text("Teléfono: ${student.phone}", style = MaterialTheme.typography.bodyMedium)
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
                    Column {
                        Text("Curso ID: $courseId", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = newStudentName,
                            onValueChange = { newStudentName = it },
                            label = { Text("Nombre del estudiante") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = newStudentEmail,
                            onValueChange = { newStudentEmail = it },
                            label = { Text("Email") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = newStudentPhone,
                            onValueChange = { newStudentPhone = it },
                            label = { Text("Teléfono") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        if (newStudentName.isNotBlank()) {
                            viewModel.addStudent(
                                Student(
                                    id = 0,
                                    name = newStudentName.trim(),
                                    email = newStudentEmail.trim(),
                                    phone = newStudentPhone.trim(),
                                    courseId = courseId,
                                )
                            )
                            newStudentName = ""
                            newStudentEmail = ""
                            newStudentPhone = ""
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
