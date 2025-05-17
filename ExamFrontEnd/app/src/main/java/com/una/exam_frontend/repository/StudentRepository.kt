package com.una.exam_frontend.repository

import android.content.Context
import com.una.exam_frontend.interfaces.StudentDao
import com.una.exam_frontend.models.Student
import com.una.exam_frontend.network.RetrofitInstance

class StudentRepository(context: Context, studentDao: StudentDao) {
    private val api = RetrofitInstance.api

    suspend fun getStudentsByCourse(courseId: Int): List<Student> {
        return try {
            api.getStudentsByCourse(courseId)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addStudent(student: Student) {
        try {
            api.createStudent(student)
        } catch (_: Exception) {
            // Manejar error o dejar vacío
        }
    }

    suspend fun deleteStudent(studentId: Int) {
        try {
            api.deleteStudent(studentId)
        } catch (_: Exception) {
            // Manejar error o dejar vacío
        }
    }

    suspend fun updateStudent(student: Student) {
        try {
            api.updateStudent(student.id, student)
        } catch (_: Exception) {
            // Manejar error o dejar vacío
        }
    }
}
