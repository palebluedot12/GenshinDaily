package com.genshindaily.genshindaily

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class WebwebView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview_daily_check_in)

        val webView = findViewById<WebView>(R.id.webview_daily_check_in)
        webView.apply{
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }
        webView.loadUrl("https://act.hoyolab.com/ys/event/signin-sea-v3/index.html?act_id=e202102251931481&hyl_auth_required=true&hyl_presentation_style=fullscreen&utm_source=share&utm_medium=link&utm_campaign=web")
    } //onCreate

    override fun onBackPressed() {
        val webView = findViewById<WebView>(R.id.webview_daily_check_in)
        if(webView.canGoBack())
        {
            webView.goBack()
        }
        else
        {
            finish()
        }
    }
}