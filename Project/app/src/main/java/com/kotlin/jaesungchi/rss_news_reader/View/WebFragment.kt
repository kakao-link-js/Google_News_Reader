package com.kotlin.jaesungchi.rss_news_reader.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.kotlin.jaesungchi.rss_news_reader.R

class WebFragment : Fragment(){

    private var webView : WebView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_web,container,false)
        webView= view.findViewById<WebView>(R.id.webView)
        webView!!.settings.javaScriptEnabled = true
        webView!!.loadUrl(arguments!!.getString("link"))
        webView!!.webChromeClient = WebChromeClient()
        webView!!.webViewClient = WebViewClientClass()
        return view
    }

    inner class WebViewClientClass : WebViewClient(){
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view!!.loadUrl(url)
            return true
        }
    }
}