package com.whx.ktapplication.widget

/**
 * Created by whx on 2017/12/13.
 */
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.whx.ktapplication.R
import com.whx.ktapplication.widget.MyChartView

import java.util.ArrayList

class MyChartView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    var leftColor: Int = 0//双柱左侧
    var rightColor: Int = 0//双柱右侧
    var lineColor: Int = 0//横轴线
    var selectLeftColor: Int = 0//点击选中左侧
    var selectRightColor: Int = 0//点击选中右侧
    var lefrColorBottom: Int = 0//左侧底部
    var rightColorBottom: Int = 0//右侧底部
    private var mPaint: Paint? = null
    private var mChartPaint: Paint? = null
    private var mShadowPaint: Paint? = null//横轴画笔、柱状图画笔、阴影画笔
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mStartWidth: Int = 0
    private var mChartWidth: Int = 0
    private var mSize: Int = 0//屏幕宽度高度、柱状图起始位置、柱状图宽度
    private var mBound: Rect? = null
    private var list: List<Float> = ArrayList()//柱状图高度占比
    private var rect: Rect? = null//柱状图矩形
    private var listener: GetNumberListener? = null//点击接口
    private var number = 1000//柱状图最大值
    private var selectIndex = -1//点击选中柱状图索引
    private val selectIndexRoles = ArrayList<Int>()

    fun setList(list: List<Float>) {
        this.list = list
        mSize = width / 39
        mStartWidth = width / 13
        mChartWidth = width / 13 - mSize - 3
        invalidate()
    }

    fun setListener(listener: GetNumberListener) {
        this.listener = listener
    }

    init {
        //获取我们自定义的样式属性
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.MyChartView, defStyleAttr, 0)
        val n = array.indexCount
        (0 until n)
                .map { array.getIndex(it) }
                .forEach {
                    when (it) {
                        R.styleable.MyChartView_leftColor ->
                            // 默认颜色设置为黑色
                            leftColor = array.getColor(it, Color.BLACK)
                        R.styleable.MyChartView_selectLeftColor ->
                            // 默认颜色设置为黑色
                            selectLeftColor = array.getColor(it, Color.BLACK)
                        R.styleable.MyChartView_rightColor -> rightColor = array.getColor(it, Color.BLACK)
                        R.styleable.MyChartView_selectRightColor -> selectRightColor = array.getColor(it, Color.BLACK)
                        R.styleable.MyChartView_xyColor -> lineColor = array.getColor(it, Color.BLACK)
                        R.styleable.MyChartView_leftColorBottom -> lefrColorBottom = array.getColor(it, Color.BLACK)
                        R.styleable.MyChartView_rightColorBottom -> rightColorBottom = array.getColor(it, Color.BLACK)
                    }
                }
        array.recycle()
        init()
    }

    //初始化画笔
    private fun init() {
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        mBound = Rect()
        mChartPaint = Paint()
        mChartPaint!!.isAntiAlias = true
        mShadowPaint = Paint()
        mShadowPaint!!.isAntiAlias = true
        mShadowPaint!!.color = Color.WHITE
    }

    //测量高宽度
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width: Int
        val height: Int
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)

        if (widthMode == View.MeasureSpec.EXACTLY) {
            width = widthSize
        } else {
            width = widthSize * 1 / 2
        }
        if (heightMode == View.MeasureSpec.EXACTLY) {
            height = heightSize
        } else {
            height = heightSize * 1 / 2
        }

        setMeasuredDimension(width, height)
    }

    //计算高度宽度
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mWidth = width
        mHeight = height
        mStartWidth = width / 13
        mSize = width / 39
        mChartWidth = width / 13 - mSize
    }

    //重写onDraw绘制
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mPaint!!.color = lineColor
        //画坐标轴
        //canvas.drawLine(0, mHeight - 100, mWidth, mHeight - 100, mPaint);
        for (i in 0..11) {
            //画刻度线
            //canvas.drawLine(mStartWidth, mHeight - 100, mStartWidth, mHeight - 80, mPaint);
            //画数字
            mPaint!!.textSize = 35f
            mPaint!!.textAlign = Paint.Align.CENTER
            mPaint!!.getTextBounds((i + 1).toString() + "", 0, i.toString().length, mBound)
            canvas.drawText((i + 1).toString() + "月", (mStartWidth - mBound!!.width() * 1 / 2).toFloat(),
                    (mHeight - 60 + mBound!!.height() * 1 / 2).toFloat(), mPaint!!)
            mStartWidth += width / 13
        }
        //画柱状图
        for (i in list.indices) {
            val size = mHeight / 120
            if (selectIndexRoles.contains(i)) {
                //偶数
                mChartPaint!!.shader = null
                if (i % 2 == 0) {
                    mChartPaint!!.color = selectLeftColor
                } else {
                    mChartPaint!!.color = selectRightColor
                }
            } else {
                //偶数
                if (i % 2 == 0) {
                    val lg = LinearGradient(mChartWidth.toFloat(), (mChartWidth + mSize).toFloat(), (mHeight - 100).toFloat(),
                            (mHeight.toFloat() - 100f - list[i] * size).toFloat(), lefrColorBottom, leftColor, Shader.TileMode.MIRROR)
                    mChartPaint!!.shader = lg
                } else {
                    val lg = LinearGradient(mChartWidth.toFloat(), (mChartWidth + mSize).toFloat(), (mHeight - 100).toFloat(),
                            (mHeight.toFloat() - 100f - list[i] * size).toFloat(), rightColorBottom, rightColor, Shader.TileMode.MIRROR)
                    mChartPaint!!.shader = lg
                }
            }

            mChartPaint!!.style = Paint.Style.FILL
            //画阴影
            if (i == number * 2 || i == number * 2 + 1) {
                mShadowPaint!!.color = Color.BLUE
            } else {
                mShadowPaint!!.color = Color.WHITE
            }

            //画柱状图
            val rectF = RectF()
            rectF.left = mChartWidth.toFloat()
            rectF.right = (mChartWidth + mSize).toFloat()
            rectF.bottom = (mHeight - 100).toFloat()
            rectF.top = (mHeight.toFloat() - 100f - list[i] * size).toFloat()
            canvas.drawRoundRect(rectF, 10f, 10f, mChartPaint!!)
            //canvas.drawRect(mChartWidth, mHeight - 100 - list.get(i) * size, mChartWidth + mSize, mHeight - 100, mChartPaint)
            // ;// 长方形
            mChartWidth += if (i % 2 == 0) 3 + width / 39 else width / 13 - 3 - mSize
        }
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        if (hasWindowFocus) {

        }
    }

    /**
     * 注意:
     * 当屏幕焦点变化时重新侧向起始位置,必须重写次方法,否则当焦点变化时柱状图会跑到屏幕外面
     */

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        if (visibility == View.VISIBLE) {
            mSize = width / 39
            mStartWidth = width / 13
            mChartWidth = width / 13 - mSize - 3
        }
    }

    /**
     * 柱状图touch事件
     * 获取触摸位置计算属于哪个月份的
     * @param ev
     * @return
     */
    override fun onTouchEvent(ev: MotionEvent): Boolean {

        val x = ev.x.toInt()
        val y = ev.y.toInt()
        var left = 0
        val top = 0
        var right = mWidth / 12
        val bottom = mHeight - 100
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> for (i in 0..11) {
                rect = Rect(left, top, right, bottom)
                left += mWidth / 12
                right += mWidth / 12
                if (rect!!.contains(x, y)) {
                    listener!!.getNumber(i, x, y)
                    number = i
                    selectIndex = i
                    selectIndexRoles.clear()
                    selectIndexRoles.add(selectIndex * 2 + 1)
                    selectIndexRoles.add(selectIndex * 2)
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return true
    }

    interface GetNumberListener {
        fun getNumber(number: Int, x: Int, y: Int)
    }

}