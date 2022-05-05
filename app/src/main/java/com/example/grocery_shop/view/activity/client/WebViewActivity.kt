package com.example.grocery_shop.view.activity.client

import android.net.Uri
import android.util.Log
import android.webkit.WebViewClient
import com.example.grocery_shop.base.BaseVMActivity
import com.example.grocery_shop.databinding.ActivityWebViewBinding
import com.example.grocery_shop.viewmodel.AuthenticationViewModel
import android.widget.Toast

import android.webkit.WebResourceResponse

import android.webkit.WebResourceRequest

import android.webkit.WebView


class WebViewActivity : BaseVMActivity<ActivityWebViewBinding, AuthenticationViewModel>() {


    override fun initView() {

        val url = intent.getStringExtra("url")
        val uri = Uri.parse(url)
        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.domStorageEnabled = true
            settings.javaScriptEnabled = true
            settings.databaseEnabled = true
            loadUrl(uri.toString())
        }
        check404()
    }

    override fun initListener() {
        if (binding.webView.isSelected) {
                binding.webView.url == "https://sale-server-app.herokuapp.com/"
                Toast.makeText(
                    applicationContext, "Oh no!",
                    Toast.LENGTH_SHORT
                ).show()
        }
        check404()
    }

    override fun initData() {
        check404()

    }

    private fun check404() {
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView, errorCode: Int,
                description: String, failingUrl: String
            ) {
                Toast.makeText(
                    applicationContext, "Oh no! $description",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {

            }
        }
    }

    class GenericWebClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (url === "https://sale-server-app.herokuapp.com/") {
                view.goBack()
                return true
            }
            return false
        }
    }
//    fun onReceivedHttpError(
//        view: WebView,
//        request: WebResourceRequest?,
//        errorResponse: WebResourceResponse
//    ) {
//        Toast.makeText(view.context, "HTTP error " + errorResponse.statusCode, Toast.LENGTH_LONG)
//            .show()
//    }
}