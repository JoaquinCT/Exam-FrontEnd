package com.una.exam_frontend

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.una.exam_frontend.models.Student

@Composable
fun StudentsActivityContent(
    courseName: String,
    students: List<Student>,
    onAddStudent: () -> Unit,
    onEdit: (Student) -> Unit,
    onDelete: (Student) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Estudiantes en $courseName", style = MaterialTheme.typography.headlineMedium)

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(students) { student ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(student.name, style = MaterialTheme.typography.titleLarge)
                        Text(student.email)
                        Row {
                            Button(onClick = { onEdit(student) }) {
                                Text("Editar")
                            }
                            Spacer(Modifier.width(8.dp))
                            Button(onClick = { onDelete(student) }) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }

        Button(onClick = onAddStudent, modifier = Modifier.fillMaxWidth()) {
            Text("Agregar Estudiante")
        }
    }
}
