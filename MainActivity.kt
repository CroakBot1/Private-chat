package com.example.monitorapp

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webView = WebView(this)
        setContentView(webView)

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://private-chat-sage-zeta.vercel.app/")

        // Start foreground service for background monitoring
        val serviceIntent = Intent(this, MonitorService::class.java)
        startForegroundService(serviceIntent)
    }
}
