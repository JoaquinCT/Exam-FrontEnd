package com.una.exam_frontend.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.una.exam_frontend.interfaces.CourseDao
import com.una.exam_frontend.interfaces.StudentDao

@Database(entities = [Course::class, Student::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "App_database"
                )
                    .fallbackToDestructiveMigration(false)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
