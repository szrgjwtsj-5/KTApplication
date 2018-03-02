package com.whx.ktapplication.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.whx.ktapplication.utils.dp2px

/**
 * Created by whx on 2017/12/28.
 */
class TestView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defArr: Int = 0) : View(context, attrs, defArr) {

    private lateinit var mPaint: Paint

    init {
        mPaint = Paint().apply {
            color = Color.CYAN
            isAntiAlias = true
            strokeWidth = dp2px(context, 30f)
            style = Paint.Style.FILL
        }
    }

    private var px = dp2px(context, 15f)
    private var py = dp2px(context, 15f)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawPoint(px, py, mPaint)
    }

    private var mLastX = 0f
    private var mLastY = 0f

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        var ex = event.x
        var ey = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> parent.requestDisallowInterceptTouchEvent(true)
            MotionEvent.ACTION_MOVE -> {
                if (Math.abs(ex - mLastX) <= Math.abs(ey - mLastY)) {
                    parent.requestDisallowInterceptTouchEvent(false)
                }
            }
        }
        mLastX = ex
        mLastY = ey
        return super.dispatchTouchEvent(event)
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        var ex = event.x
        var ey = event.y

        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                px = ex
                py = ey
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                px = ex
                py = ey
                invalidate()
            }
        }
        return true
    }
}