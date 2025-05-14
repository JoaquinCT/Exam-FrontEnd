package com.una.exam_frontend.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Students",
    foreignKeys = [ForeignKey(
        entity = Course::class,            // Entidad referenciada
        parentColumns = ["id"],            // Columna en Course (PK)
        childColumns = ["course_id"],      // Columna en Student (FK)
        onDelete = ForeignKey.CASCADE      // Acción al eliminar curso
    )]
)
data class Student(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "course_id") val courseId: Int  // FK con Course
)

