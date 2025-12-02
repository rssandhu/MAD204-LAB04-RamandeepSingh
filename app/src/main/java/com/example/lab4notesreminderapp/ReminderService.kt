package com.example.lab4notesreminderapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import androidx.core.app.NotificationCompat

/**
 * Service that posts a note reminder notification after 5 seconds.
 */
class ReminderService : Service() {

    companion object {
        const val CHANNEL_ID = "notes_reminder_channel"
        const val NOTIFICATION_ID = 101
    }

    override fun onBind(intent: Intent?): IBinder? = null

    /**
     * Starts the service and schedules the notification with a 5-second delay.
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        Handler(mainLooper).postDelayed({
            showReminderNotification()
            stopSelf()
        }, 5000)
        return START_NOT_STICKY
    }

    /**
     * Builds and shows the reminder notification.
     */
    private fun showReminderNotification() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, pendingFlags)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Reminder")
            .setContentText("Check your notes!")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Take a quick look at your notes and keep your ideas fresh."))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .notify(NOTIFICATION_ID, notification)
    }

    /**
     * Creates notification channel for Android O+.
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Notes Reminder",
                NotificationManager.IMPORTANCE_HIGH
            ).apply { description = "Channel for note reminder notifications" }
            (getSystemService(NotificationManager::class.java))
                .createNotificationChannel(channel)
        }
    }
}
