package com.una.exam_frontend.repository

import android.content.Context
import com.una.exam_frontend.models.Student
import com.una.exam_frontend.network.RetrofitInstance
import com.una.exam_frontend.interfaces.StudentDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class StudentRepository(
    private val context: Context,
    private val studentDao: StudentDao
) {
    private val api = RetrofitInstance.api

    suspend fun getStudentsByCourse(courseId: Int): List<Student> = withContext(Dispatchers.IO) {
        return@withContext if (isNetworkAvailable(context)) {
            try {
                val students = api.getStudentsByCourse(courseId)
                // Guarda los datos en local
                studentDao.clearAllByCourse(courseId)
                studentDao.insertAll(students)
                students
            } catch (e: Exception) {
                // Si falla el API, usa local
                studentDao.getByCourse(courseId)
            }
        } else {
            studentDao.getByCourse(courseId)
        }
    }

    suspend fun addStudent(student: Student) = withContext(Dispatchers.IO) {
        if (isNetworkAvailable(context)) {
            api.createStudent(student)
            // Actualiza local
            val updated = api.getStudentsByCourse(student.courseId)
            studentDao.clearAllByCourse(student.courseId)
            studentDao.insertAll(updated)
        } else {
            // Solo local
            studentDao.insertAll(listOf(student))
        }
    }

    suspend fun deleteStudent(studentId: Int, courseId: Int) = withContext(Dispatchers.IO) {
        if (isNetworkAvailable(context)) {
            api.deleteStudent(studentId)
            val updated = api.getStudentsByCourse(courseId)
            studentDao.clearAllByCourse(courseId)
            studentDao.insertAll(updated)
        } else {
            // Solo local
            val all = studentDao.getByCourse(courseId).filter { it.id != studentId }
            studentDao.clearAllByCourse(courseId)
            studentDao.insertAll(all)
        }
    }

    suspend fun updateStudent(student: Student) = withContext(Dispatchers.IO) {
        if (isNetworkAvailable(context)) {
            api.updateStudent(student.id, student)
            val updated = api.getStudentsByCourse(student.courseId)
            studentDao.clearAllByCourse(student.courseId)
            studentDao.insertAll(updated)
        } else {
            // Solo local
            val all = studentDao.getByCourse(student.courseId).map { if (it.id == student.id) student else it }
            studentDao.clearAllByCourse(student.courseId)
            studentDao.insertAll(all)
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        return RetrofitInstance
            .javaClass
            .getDeclaredMethod("isNetworkAvailable", Context::class.java)
            .apply { isAccessible = true }
            .invoke(RetrofitInstance, context) as Boolean
    }
}