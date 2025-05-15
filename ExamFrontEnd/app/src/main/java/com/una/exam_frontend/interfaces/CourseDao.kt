package com.una.exam_frontend.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.una.exam_frontend.models.Course

@Dao
interface CourseDao {
    @Query("SELECT * FROM Courses")
    suspend fun getAll(): List<Course>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(events: List<Course>)

    @Query("DELETE FROM Courses")
    suspend fun clearAll()
}