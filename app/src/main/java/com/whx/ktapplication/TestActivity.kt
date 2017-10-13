package com.whx.ktapplication

import android.content.Intent
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.TextView
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

        start.setOnClickListener { ruun() }

        val texts = listOf("客户等级A,拜访目的:", "拜访结果:老板还没有批,谈预定", "客户等级A,拜访目的:谈产品,拜访结果:老板还没有批,谈预定,了解产品情况,申请有结果后答复我,后续策略:继续跟进,毛利:7%")

        texts.forEach {
            val textView = TextView(start.context)
            textView.setSingleLine()
            textView.ellipsize = TextUtils.TruncateAt.END
            textView.text = it

            container.addView(textView)
        }

    }

    private fun ruun() {
        startActivity(Intent(this, WebActivity::class.java))
    }

}