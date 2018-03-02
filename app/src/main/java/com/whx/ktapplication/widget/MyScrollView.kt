package com.whx.ktapplication.widget

import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by whx on 2017/12/28.
 */
class MyScrollView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defArr: Int = 0) : NestedScrollView(context, attrs, defArr) {

    private var mLastX = 0f
    private var mLastY = 0f

//    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
//        var x = ev.x
//        var y = ev.y
//        var intercepted = false
//
//        when (ev.action) {
//            MotionEvent.ACTION_DOWN -> {
//                mLastX = x
//                mLastY = y
//                intercepted = false
//            }
//            MotionEvent.ACTION_MOVE -> {
//                intercepted = Math.abs(x - mLastX) <= Math.abs(y - mLastY)
//            }
//            MotionEvent.ACTION_UP -> {
//                intercepted = false
//            }
//        }
//
//        return intercepted
//    }
}