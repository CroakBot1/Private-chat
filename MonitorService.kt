// File: MonitorService.kt
package com.example.monitorapp

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class MonitorService : Service() {

    private val CHANNEL_ID = "MonitorServiceChannel"
    private val handler = Handler()
    private var secondsPassed = 0

    private lateinit var overlay: OverlayView

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Activity Monitor Running")
            .setContentText("Monitoring your app activity in full-screen")
            .setSmallIcon(R.drawable.ic_monitor)
            .build()
        startForeground(1, notification)

        // Start overlay
        overlay = OverlayView(this)
        overlay.show()

        startTimer()
    }

    private fun startTimer() {
        handler.post(object : Runnable {
            override fun run() {
                secondsPassed++
                overlay.updateCountdown(secondsPassed)
                Log.d("MonitorService", "Seconds passed: $secondsPassed")
                handler.postDelayed(this, 1000)
            }
        })
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Activity Monitor Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
}
