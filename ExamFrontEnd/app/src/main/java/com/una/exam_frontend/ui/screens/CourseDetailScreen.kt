@file:Suppress("UNCHECKED_CAST")

package com.una.exam_frontend.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.una.exam_frontend.models.AppDatabase
import com.una.exam_frontend.repository.CourseRepository
import com.una.exam_frontend.viewmodel.CourseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen(navController: NavHostController, courseId: Int) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val repository = remember { CourseRepository(context, db.courseDao()) }
    val viewModel: CourseViewModel = viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CourseViewModel(repository) as T
        }
    })
    val courses by viewModel.courses.collectAsState()
    val course = courses.find { it.id == courseId } ?: return

    var name by remember { mutableStateOf(course.name) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Curso") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre del curso") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        viewModel.updateCourse(course.copy(name = name))
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Cambios")
            }
        }
    }
}