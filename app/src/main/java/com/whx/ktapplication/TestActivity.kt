package com.whx.ktapplication

import android.os.*
import android.support.v7.app.AppCompatActivity
import com.whx.ktapplication.utils.toast
import kotlinx.android.synthetic.main.activity_test.*

/**
 * Created by whx on 2017/8/10.
 */
class TestActivity : AppCompatActivity() {
    val handler = object : Handler(){
        override fun handleMessage(msg: Message?) {
            if (msg?.what == 123) {
                toast("666")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        start.setOnClickListener { v -> ruun() }
    }

    fun ruun() {
        object: AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void?): Void? {
                SystemClock.sleep(20000)
                return null
            }
        }.execute()
    }

}