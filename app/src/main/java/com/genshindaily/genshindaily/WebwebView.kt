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
        webView.loadUrl("https://webstatic-sea.mihoyo.com/ys/event/signin-sea/index.html?act_id=e202102251931481&utm_source=link")
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