package com.whx.ktapplication

import android.app.Application
import android.content.Context
import android.os.StrictMode
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.whx.ktapplication.delegate.DelegatesExt

/**
 * Created by whx on 2017/8/2.
 */
class MyApplication : Application() {
    companion object {
        private var instance: Application by DelegatesExt.notNullStringValue()
        fun instance() = instance

    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        setupLeakCanary()
    }

    fun setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        enableStrictMode()
        LeakCanary.install(this)
    }

    fun enableStrictMode() {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build())
    }
}