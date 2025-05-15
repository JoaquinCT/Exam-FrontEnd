package com.una.exam_frontend.interfaces

import androidx.room.*
import com.una.exam_frontend.models.Course

@Dao
interface CourseDao {
    @Query("SELECT * FROM Courses")
    suspend fun getAll(): List<Course>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(courses: List<Course>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(course: Course)

    @Delete
    suspend fun delete(course: Course)

    @Query("DELETE FROM Courses")
    suspend fun clearAll()

    @Update
    suspend fun update(course: Course)

    @Query("SELECT * FROM Courses WHERE id = :courseId LIMIT 1")
    suspend fun getById(courseId: Int): Course?
}