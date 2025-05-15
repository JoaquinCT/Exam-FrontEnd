package com.una.exam_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.una.exam_frontend.ui.theme.ExamFrontEndTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExamFrontEndTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExamFrontEndTheme {
        Greeting("Android")
    }
}
/*
@Composable
fun MainActivityContent(courses: List<Course>, onAddCourse: () -> Unit, onEdit: (Course) -> Unit, onDelete: (Course) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Lista de Cursos", style = MaterialTheme.typography.headlineMedium)

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(courses) { course ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(course.name, style = MaterialTheme.typography.titleLarge)
                        Text(course.description)
                        Text("Horario: ${course.schedule}")
                        Text("Profesor: ${course.professor}")
                        Row {
                            Button(onClick = { onEdit(course) }) {
                                Text("Editar")
                            }
                            Spacer(Modifier.width(8.dp))
                            Button(onClick = { onDelete(course) }) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }

        Button(onClick = onAddCourse, modifier = Modifier.fillMaxWidth()) {
            Text("Agregar Curso")
        }
    }
}
*/
