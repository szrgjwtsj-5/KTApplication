package com.whx.ktapplication.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.whx.ktapplication.model.BarChartBean
import com.whx.ktapplication.model.BarChartItemBean
import com.whx.ktapplication.utils.dp2px
import com.whx.ktapplication.utils.sp2px

/**
 * Created by whx on 2017/12/12.
 */
class BarChartView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defArr: Int = 0) : View(context, attrs, defArr) {

    private val BACK_LINE_COLOR = Color.parseColor("#EEEEEE")
    private val TEXT_COLOR_LIGHT = Color.parseColor("#999999")

    private val mData: MutableList<BarChartBean> = mutableListOf()
    private val mColorList = listOf(Color.parseColor("#1BA4FC"), Color.parseColor("#C0E1FE"))
    private var mLineNum = 4        //背景线行数
    private val pointsY = arrayOfNulls<String>(mLineNum)   //背景线上的y 轴数字
    private var mMaxHeight = 0          //柱状图的最大值
    private var mWidth = 0
    private var mHeight = 0
    private var mTextSize = sp2px(context, 10f)     //图表内的文字大小
    private val mMargin = dp2px(context, 10f)        //距父view 的左右间隔
    private val mMarginBottom = dp2px(context, 8f)   //柱状图文字间隔
    private val mLegendSize = dp2px(context, 10f)    //图例大小
    private val mLegendHeight = dp2px(context, 15f)  //图例区域的高度
    private var mBarWidth = 0       //柱状图宽度
    private var mBarMargin = 0      //柱状图间距
    private var mIsMore = true      //是否3 个柱状图以上
    private var mAvailableWidth = 0f     //柱状图区域可用宽度
    private var mAvailableHeight = 0f    //柱状图区域可用高度

    private var mSelectedLine: BarChartSelectLine? = null

    private val mRegions = arrayListOf<RegionWrapper>()

    private val backLinePaint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = BACK_LINE_COLOR
        }
    }
    private val textPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = TEXT_COLOR_LIGHT
            textSize = mTextSize
        }
    }
    private val mBarPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
        }
    }

    init {

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mWidth = w
        mHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawColor(Color.parseColor("#ffffff"))
        if (mData.isNotEmpty()) {
            drawLegend(canvas)
        }
        drawBackLine(canvas)
        if (mData.isNotEmpty()) {
            drawBar(canvas)
        }
    }

    /**
     * 画图例
     */
    private fun drawLegend(canvas: Canvas?) {
        val marginLeft = dp2px(context, 15f)
        val marginMiddle = dp2px(context, 5f)

        var del = 0f
        mData[0].revenue.forEachReversedWithIndex { i, barChartItemBean ->
            mBarPaint.color = mColorList[i]
            canvas?.let {
                del += marginLeft + textPaint.measureText(barChartItemBean.dateStr)
                textPaint.textAlign = Paint.Align.LEFT
                it.drawText(barChartItemBean.dateStr, (mWidth - del), mTextSize, textPaint)
                del += marginMiddle + mLegendSize
                it.drawRect(mWidth - del, 0f, mWidth - del + mLegendSize, mLegendSize, mBarPaint)
            }
        }
    }

    /**
     * 画背景线
     */
    private fun drawBackLine(canvas: Canvas?) {

        mAvailableHeight = mHeight - mTextSize - mMarginBottom * 2 - mLegendHeight
        canvas?.let {
            for (i in pointsY.indices) {
                textPaint.textAlign = Paint.Align.LEFT
                it.drawText(pointsY[i].toString(), mMargin, (i + 1) * mAvailableHeight/4f - 10 - mTextSize, textPaint)
                if (i == 0) {
                    mAvailableWidth = mWidth - mMargin - textPaint.measureText(pointsY[i].toString()) - 10
                }
                it.drawLine(mMargin, (i + 1) * mAvailableHeight/4f, mWidth.toFloat() - mMargin, (i + 1) * mAvailableHeight/4f, backLinePaint)
            }
        }
    }

    private val rect = RectF()
    private var matr: Matrix? = null
    /**
     * 画柱状图
     */
    private fun drawBar(canvas: Canvas?) {
        canvas?.let {
            mRegions.clear()

            it.translate(mWidth.toFloat() - mAvailableWidth, 0f)    //向右平移画布
            mAvailableWidth -= (mMargin + 10)
            getBarWidth()

            val width = mBarWidth / 2 - 1

            if (!mIsMore) {
                it.translate(mBarMargin.toFloat(), 0f)
            }
            matr = canvas.matrix
            val y = mAvailableHeight + mMarginBottom + mTextSize
            mData.forEachWithIndex { i, barChartBean ->
                val margin = 2f
                var x = 0f

                barChartBean.revenue.forEachWithIndex { j, itemBean ->
                    if (j == 1) {
                        x = i * mBarWidth + j * width + j * margin + i * mBarMargin
                    }
                    val left = i * mBarWidth + j * width + j * margin + i * mBarMargin
                    val top = mAvailableHeight - itemBean.value.toFloat()/mMaxHeight * mAvailableHeight * 0.75f
                    val right = i * mBarWidth + (j + 1) * width + j * margin +  + i * mBarMargin
                    val bottom = mAvailableHeight

                    rect.set(left, top, right, bottom)
                    mBarPaint.color = mColorList[j]
                    canvas.drawRect(rect, mBarPaint)
                    mRegions.add(RegionWrapper(Region(left.toInt(), top.toInt(), right.toInt(), bottom.toInt()), itemBean, barChartBean.name, left))
                }

                //画柱形下的文字
                textPaint.textAlign = Paint.Align.CENTER
                val num = textPaint.breakText(barChartBean.name, true, (mBarWidth + mBarMargin).toFloat(), null)

                val str = if (num < barChartBean.name.length - 1) {
                    barChartBean.name.substring(0, num) + "..."
                } else {
                    barChartBean.name
                }
                canvas.drawText(str, x, y, textPaint)

            }
        }
    }

    /**
     * 获取柱形的最大值
     */
    private fun getMaxHeight() {
        var res = 0.0
        var scale = ""
        mData.forEach {
            it.revenue.forEach {
                if (res <= it.value) {
                    res = it.value
                }
            }
            scale = it.scale
        }
        mMaxHeight = res.toInt()
        for (i in 0 until mLineNum) {
            pointsY[mLineNum -1 - i] = (i * mMaxHeight / mLineNum).toString() + scale
        }
    }

    /**
     * 计算柱和柱间距宽度
     */
    private fun getBarWidth() {
        val size = mData.size
        if (size > 3) {
            mIsMore = true
            mBarMargin = (mAvailableWidth / (12f * size.toFloat() / 7 - 1)).toInt()
            //mBarMargin = mAvailableWidth / (2 * size)
            mBarWidth = (5 * mBarMargin.toFloat() / 7).toInt()
        } else {
            mIsMore = false
            mBarMargin = (mAvailableWidth / (3 * size.toFloat() / 2 + 1)).toInt()
            mBarWidth = (mBarMargin.toFloat() / 2).toInt()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        val ex = event?.x!!
        val ey = event.y
        val pts = FloatArray(2)
        pts[0] = ex; pts[1] = ey
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                //由于画图时曾对坐标系平移，这里要对事件的x、y 进行矩阵转换
                val invertMatrix = Matrix()
                matr?.invert(invertMatrix)
                invertMatrix.mapPoints(pts)

                mRegions.forEach {
                    if (it.region.contains(pts[0].toInt(), pts[1].toInt())) {
                        Log.e("region =   ", it.region.toString())
                        mSelectedLine?.setData(BarChartSelectLine.BarData(it.barItem, it.name))
                        mSelectedLine?.refresh(0, it.xAias, false)
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    fun setData(dataList: MutableList<BarChartBean>) {
        mData.clear()
        mData.addAll(dataList)
        getMaxHeight()
        invalidate()
    }

    fun attachSelectLine(selectLine: BarChartSelectLine) {
        this.mSelectedLine = selectLine
    }

    private fun <T> List<T>.forEachReversedWithIndex(f: (Int, T) -> Unit) {
        var i = size - 1
        while (i >= 0) {
            f(i, get(i))
            i--
        }
    }
    private fun <T> List<T>.forEachWithIndex(f: (Int, T) -> Unit) {
        val lastIndex = size - 1
        for (i in 0..lastIndex) {
            f(i, get(i))
        }
    }

    private class RegionWrapper (
        val region: Region,
        val barItem: BarChartItemBean,
        val name: String,
        val xAias: Float
    )
}