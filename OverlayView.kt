// File: OverlayView.kt
package com.example.monitorapp

import android.app.Activity
import android.content.Context
import android.graphics.PixelFormat
import android.view.*
import android.widget.FrameLayout
import android.widget.TextView

class OverlayView(private val context: Context) {

    private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private val overlayView: View = LayoutInflater.from(context).inflate(R.layout.overlay_layout, null)
    private val countdownText: TextView = overlayView.findViewById(R.id.countdownText)
    private val fullscreenNotice: TextView = overlayView.findViewById(R.id.fullscreenNotice)

    private val params = WindowManager.LayoutParams(
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.WRAP_CONTENT,
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        else
            WindowManager.LayoutParams.TYPE_PHONE,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
        PixelFormat.TRANSLUCENT
    ).apply {
        gravity = Gravity.TOP or Gravity.END
        x = 20
        y = 50
    }

    fun show() {
        windowManager.addView(overlayView, params)
    }

    fun updateCountdown(seconds: Int) {
        val hrs = seconds/3600
        val mins = (seconds%3600)/60
        val secs = seconds%60
        countdownText.text = String.format("%02d:%02d:%02d", hrs, mins, secs)

        // Fullscreen check
        fullscreenNotice.visibility = if ((context as Activity).window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    fun hide() {
        windowManager.removeView(overlayView)
    }
}
