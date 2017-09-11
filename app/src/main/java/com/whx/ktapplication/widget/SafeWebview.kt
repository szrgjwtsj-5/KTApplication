package com.whx.ktapplication.widget

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.webkit.WebView

/**
 * Created by whx on 2017/8/18.
 */
class SafeWebview(context: Context, attrs: AttributeSet?, def: Int) : WebView(context, attrs, def) {

    init {
        makeWebViewSafe()
    }

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0) {

        makeWebViewSafe()
    }
    constructor(context: Context): this(context, null) {
        makeWebViewSafe()
    }

    @SuppressLint("NewApi")
    private fun makeWebViewSafe() {
        removeSearchBoxJavaBridgeInterface()
        settings.allowFileAccess = false
        settings.savePassword = false
    }

    private fun removeSearchBoxJavaBridgeInterface() {
        if (Build.VERSION.SDK_INT in 11..17) {
            removeJavascriptInterface("searchBoxJavaBridge_")
        }
    }

    @SuppressLint("JavascriptInterface")
    override fun addJavascriptInterface(obj: Any, name: String) {
        if (Build.VERSION.SDK_INT >= 17) {
            super.addJavascriptInterface(obj, name)
        }

    }
}