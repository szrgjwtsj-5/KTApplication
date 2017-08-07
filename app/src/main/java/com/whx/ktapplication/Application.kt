package com.whx.ktapplication

import android.app.Application
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
    }
}