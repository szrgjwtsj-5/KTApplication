package com.whx.ktapplication

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.ViewGroup
import android.webkit.*
import android.widget.EditText
import com.whx.ktapplication.utils.showMsg
import kotlinx.android.synthetic.main.activity_web.*
import java.lang.Exception

/**
 * Created by whx on 2017/8/18.
 */
class WebActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        initWebView()

        val edit = EditText(this)
        edit.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun initWebView() {
        val url = "file:///android_asset/test.html"

        mWebView.setWebViewClient(MyWebViewClient())
        mWebView.setWebChromeClient(MyWebChromeClient())

        mWebView.settings.javaScriptEnabled = true
        //mWebView.settings.userAgentString(ua);

        mWebView.loadUrl(url)
    }
    class MyWebViewClient : WebViewClient() {

        var count = 0

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?) = false

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            showMsg(count.toString())
            super.onPageFinished(view, url)
        }

        override fun onLoadResource(view: WebView?, url: String?) {
            count ++
        }
    }

    class MyWebChromeClient : WebChromeClient() {
        override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
            try {
//                AlertDialog.Builder(MyApplication.instance())
//                        .setTitle("JsAlert")
//                        .setMessage(message)
//                        .setPositiveButton("OK") { dialog, which -> result?.confirm() }
//                        .setCancelable(false)
//                        .show()
                Log.w("--------", message)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return false
        }

        override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {

            Log.w("--------", message)
//            result?.confirm()
            result?.cancel()

            return false
        }

        override fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?): Boolean {
            Log.w("----------", defaultValue)
            Log.w("----------", message)

            result?.confirm("what the ***")

            return true
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            mWebView.clearHistory()

            val parent = mWebView.parent as ViewGroup
            parent.removeAllViews()

            mWebView.destroy()
        }
        super.onDestroy()
    }
}