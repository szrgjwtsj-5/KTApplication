package com.whx.ktapplication

import android.app.Application
import kotlin.properties.Delegates

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