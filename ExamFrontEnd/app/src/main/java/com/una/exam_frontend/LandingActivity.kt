package com.una.exam_frontend

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.una.exam_frontend.R

class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Crear canal de notificaciones
        createNotificationChannel(this)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "event_reminder_channel"
            val channelName = "Event Reminders"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = "Notifies users about upcoming events"
            }

            val notificationManager =
                context.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }
}
