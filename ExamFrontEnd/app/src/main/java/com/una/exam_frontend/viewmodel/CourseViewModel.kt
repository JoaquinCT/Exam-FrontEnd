package com.una.exam_frontend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.una.exam_frontend.repository.CourseRepository
import com.una.exam_frontend.models.Course

class CourseViewModel(private val repository: CourseRepository) : ViewModel() {
    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses

    fun loadCourses() {
        viewModelScope.launch {
            _courses.value = repository.getCourses()
        }
    }

    fun addCourse(course: Course) {
        viewModelScope.launch {
            repository.addCourse(course)
            loadCourses()
        }
    }

    fun deleteCourse(courseId: Int) {
        viewModelScope.launch {
            repository.deleteCourse(courseId)
            loadCourses()
        }
    }

    fun updateCourse(course: Course) {
        viewModelScope.launch {
            repository.updateCourse(course)
            loadCourses()
        }
    }
}