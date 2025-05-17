package com.una.exam_frontend.network

import com.una.exam_frontend.models.Course
import com.una.exam_frontend.models.Student
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("api/course")
    suspend fun getCourses(): List<Course>

    @GET("api/course/{id}")
    suspend fun getCourse(@Path("id") id: Int): Course

    @POST("api/course")
    suspend fun createCourse(@Body course: Course): Response<Unit>

    @PUT("api/course/{id}")
    suspend fun updateCourse(@Path("id") id: Int, @Body course: Course): Response<Unit>

    @DELETE("api/course/{id}")
    suspend fun deleteCourse(@Path("id") id: Int): Response<Unit>

    @GET("api/student")
    suspend fun getStudents(): List<Student>

    @GET("api/student/{id}")
    suspend fun getStudent(@Path("id") id: Int): Student

    @POST("api/student")
    suspend fun createStudent(@Body student: Student): Response<Unit>

    @PUT("api/student/{id}")
    suspend fun updateStudent(@Path("id") id: Int, @Body student: Student): Response<Unit>

    @DELETE("api/student/{id}")
    suspend fun deleteStudent(@Path("id") id: Int): Response<Unit>

    @GET("api/student/by-course/{courseId}")
    suspend fun getStudentsByCourse(@Path("courseId") courseId: Int): List<Student>

}
