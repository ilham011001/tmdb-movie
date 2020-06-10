package com.hamz.tmdb_movie.ui.detail.review

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.hamz.tmdb_movie.R
import kotlinx.android.synthetic.main.activity_webview_review.*

class WebviewReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview_review)

        initView()
        initWebview()
    }

    private fun initView() {
        title = "Review"
    }

    private fun initWebview() {
        val urlReview = intent.getStringExtra("url")

        webview_review.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        webview_review.settings.javaScriptEnabled = true
        webview_review.settings.loadWithOverviewMode = true
        webview_review.settings.useWideViewPort = true
        webview_review.settings.setSupportZoom(true)
        webview_review.settings.builtInZoomControls = true

        webview_review.loadUrl(urlReview)
    }
}