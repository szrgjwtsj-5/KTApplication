package com.whx.ktapplication

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.StateListDrawable
import android.os.*
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.app.AppCompatActivity
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.Log
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

    }

    fun testSelector() {
        var drawable = ContextCompat.getDrawable(this, R.mipmap.ic_launcher)
        val colors = intArrayOf(ContextCompat.getColor(this, R.color.pink), ContextCompat.getColor(this, R.color.colorAccent))
        val states = arrayOf(intArrayOf(android.R.attr.state_pressed), intArrayOf())

        val colorList = ColorStateList(states, colors)
        val stateListDrawable = StateListDrawable().apply {
            addState(states[0], drawable)   //注意顺序
            addState(states[1], drawable)
        }

        val state = stateListDrawable.constantState
        drawable  = DrawableCompat.wrap(if (state == null) stateListDrawable else state.newDrawable()).mutate()
        DrawableCompat.setTintList(drawable, colorList)
        imageView.setImageDrawable(drawable)
    }
    fun testTint() {
        val drawable = ContextCompat.getDrawable(this, R.mipmap.ic_launcher)
        val state = drawable.constantState
        val drawable1 = DrawableCompat.wrap(if (state == null) drawable else state.newDrawable().mutate())
        drawable1.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.pink))

        imageView.setImageDrawable(drawable)
        //imageView1.setImageDrawable(drawable1)
    }

    private fun loadAdviseSuccess() {

        val str = "星级在同商圈品类商家中处于 中游"
        val comment = "评论数较同商圈品类商家均值高"
        val commentNum = "20%"

        val spannableStr = SpannableString(str)
        spannableStr.setSpan(RelativeSizeSpan(1.5f), str.length-2, str.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        evaluateStarTv.text = spannableStr

        val commentStr = "$comment $commentNum"
        val commentSpannable = SpannableString(commentStr)
        commentSpannable.setSpan(RelativeSizeSpan(1.5f), commentStr.length - commentNum.length, commentStr.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)

        if (commentNum[0] == '+')
            commentSpannable.setSpan(ForegroundColorSpan(Color.parseColor("#FC797A")), commentStr.length - commentNum.length, commentStr.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        else
            commentSpannable.setSpan(ForegroundColorSpan(Color.parseColor("#3DC6B6")), commentStr.length - commentNum.length, commentStr.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)

        evaluateCommentTv.text = commentSpannable
    }

    private fun ruun() {
        startActivity(Intent(this, WebActivity::class.java))
    }

}