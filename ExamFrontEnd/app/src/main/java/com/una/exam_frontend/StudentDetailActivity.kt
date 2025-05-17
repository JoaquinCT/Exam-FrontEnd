package com.una.exam_frontend

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.una.exam_frontend.models.Student

@Composable
fun StudentDetailActivityContent(student: Student, courseName: String) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Detalles del Estudiante", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        Text("Nombre: ${student.name}")
        Text("Email: ${student.email}")
        Text("Curso: $courseName")
    }
}