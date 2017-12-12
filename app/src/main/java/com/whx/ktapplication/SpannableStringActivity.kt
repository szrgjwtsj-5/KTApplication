package com.whx.ktapplication

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ImageSpan
import android.text.style.ScaleXSpan
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_spannable.*

/**
 * Created by whx on 2017/10/27.
 */
class SpannableStringActivity : AppCompatActivity(){

    private val src = "这是用来测试SpannableString的一段文字"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_spannable)


        testScaleX()

        testImageSpan()
    }

    private fun testImageSpan() {
        val spannableStr = SpannableString(src)
        val drawable = ContextCompat.getDrawable(this, R.drawable.rating_star_full).apply { setBounds(0, 0, 50, 50) }

        val imageSpan = ImageSpan(drawable)
        spannableStr.setSpan(imageSpan, 4, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        container.addView(TextView(this).apply { text = spannableStr })

    }

    private fun testScaleX() {
        val spannableStr = SpannableString(src)
        val span = ScaleXSpan(0.5f)

        spannableStr.setSpan(span, 4, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        container.addView(TextView(this).apply { text = spannableStr })
    }
}