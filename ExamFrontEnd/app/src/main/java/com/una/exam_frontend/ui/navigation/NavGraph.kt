package com.una.exam_frontend.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.una.exam_frontend.ui.screens.CourseListScreen
import com.una.exam_frontend.ui.screens.CourseDetailScreen
import com.una.exam_frontend.ui.screens.StudentListScreen
import com.una.exam_frontend.ui.screens.StudentDetailScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "courses") {
        composable("courses") { CourseListScreen(navController) }
        composable("courseDetail/{courseId}") { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("courseId")?.toIntOrNull() ?: return@composable
            CourseDetailScreen(navController, courseId)
        }
        composable("students/{courseId}") { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("courseId")?.toIntOrNull() ?: return@composable
            StudentListScreen(navController, courseId)
        }
        composable("studentDetail/{studentId}") { backStackEntry ->
            val studentId = backStackEntry.arguments?.getString("studentId")?.toIntOrNull() ?: return@composable
            StudentDetailScreen(navController, studentId)
        }
    }
}