package com.cwb.newsapp.ui

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.cwb.newsapp.R

class WebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        // Initialize WebView and ProgressBar
        webView = findViewById(R.id.webView)
        progressBar = findViewById(R.id.progressBar)

        // Enable JavaScript if needed
        webView.settings.javaScriptEnabled = true

        // Set a WebViewClient to handle loading events
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                super.onPageStarted(view, url, favicon)
                // Show the progress bar when page starts loading
                progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // Hide the progress bar when page finishes loading
                progressBar.visibility = View.GONE
            }
        }

        // Set a WebChromeClient to show progress in the progress bar
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                // You can update the progress bar here if needed
                progressBar.progress = newProgress
            }
        }

        // Get the URL passed from the previous screen
        val url = intent.getStringExtra("URL")

        // Load the URL in the WebView
        if (url != null) {
            webView.loadUrl(url)
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack() // Go back to previous page in WebView if possible
        } else {
            super.onBackPressed() // Otherwise, close the activity
        }
    }
}