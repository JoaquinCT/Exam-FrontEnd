package com.una.exam_frontend.repository

import android.content.Context
import com.una.exam_frontend.models.Course
import com.una.exam_frontend.network.RetrofitInstance
import com.una.exam_frontend.interfaces.CourseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CourseRepository(
    private val context: Context,
    private val courseDao: CourseDao
) {
    private val api = RetrofitInstance.api

    suspend fun getCourses(): List<Course> = withContext(Dispatchers.IO) {
        return@withContext if (isNetworkAvailable(context)) {
            try {
                val courses = api.getCourses()
                // Guarda los datos en local
                courseDao.clearAll()
                courseDao.insertAll(courses)
                courses
            } catch (e: Exception) {
                // Si falla el API, usa local
                courseDao.getAll()
            }
        } else {
            courseDao.getAll()
        }
    }

    suspend fun addCourse(course: Course) = withContext(Dispatchers.IO) {
        if (isNetworkAvailable(context)) {
            api.createCourse(course)
            // Actualiza local
            val updated = api.getCourses()
            courseDao.clearAll()
            courseDao.insertAll(updated)
        } else {
            // Solo local
            courseDao.insertAll(listOf(course))
        }
    }

    suspend fun deleteCourse(courseId: Int) = withContext(Dispatchers.IO) {
        if (isNetworkAvailable(context)) {
            api.deleteCourse(courseId)
            val updated = api.getCourses()
            courseDao.clearAll()
            courseDao.insertAll(updated)
        } else {
            // Solo local
            val all = courseDao.getAll().filter { it.id != courseId }
            courseDao.clearAll()
            courseDao.insertAll(all)
        }
    }

    suspend fun updateCourse(course: Course) = withContext(Dispatchers.IO) {
        if (isNetworkAvailable(context)) {
            api.updateCourse(course.id, course)
            val updated = api.getCourses()
            courseDao.clearAll()
            courseDao.insertAll(updated)
        } else {
            // Solo local
            val all = courseDao.getAll().map { if (it.id == course.id) course else it }
            courseDao.clearAll()
            courseDao.insertAll(all)
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