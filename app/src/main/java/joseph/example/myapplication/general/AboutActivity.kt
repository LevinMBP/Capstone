package joseph.example.myapplication.general

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import joseph.example.myapplication.R

class AboutActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        // Initialize the WebView
        val webView: WebView = findViewById(R.id.webView)

        // Enable JavaScript (if needed)
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        // Set WebViewClient to prevent opening the browser
        webView.webViewClient = WebViewClient()

        // Load a URL
        webView.loadUrl("https://www.google.com")


    }
}