package com.una.exam_frontend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.una.exam_frontend.repository.StudentRepository
import com.una.exam_frontend.models.Student

class StudentViewModel(private val repository: StudentRepository) : ViewModel() {
    private val _students = MutableStateFlow<List<Student>>(emptyList())
    val students: StateFlow<List<Student>> = _students

    private var currentCourseId: Int = -1

    fun loadStudents(courseId: Int) {
        currentCourseId = courseId
        viewModelScope.launch {
            _students.value = repository.getStudentsByCourse(courseId)
        }
    }

    fun addStudent(student: Student) {
        viewModelScope.launch {
            repository.addStudent(student)
            loadStudents(student.courseId)
        }
    }

    fun deleteStudent(studentId: Int) {
        viewModelScope.launch {
            repository.deleteStudent(studentId)
            if (currentCourseId != -1) {
                loadStudents(currentCourseId)
            }
        }
    }

    fun updateStudent(student: Student) {
        viewModelScope.launch {
            repository.updateStudent(student)
            loadStudents(student.courseId)
        }
    }
}
