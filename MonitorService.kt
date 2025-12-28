package com.example.monitorapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import android.os.Handler
import android.util.Log

class MonitorService : Service() {

    private val CHANNEL_ID = "MonitorServiceChannel"
    private val handler = Handler()
    private var secondsPassed = 0

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Activity Monitor Running")
            .setContentText("Monitoring your app activity in full-screen")
            .setSmallIcon(R.drawable.ic_monitor)
            .build()
        startForeground(1, notification)

        startTimer()
    }

    private fun startTimer() {
        handler.post(object : Runnable {
            override fun run() {
                secondsPassed++
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
