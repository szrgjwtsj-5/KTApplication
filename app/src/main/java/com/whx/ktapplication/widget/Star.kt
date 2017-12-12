package com.whx.ktapplication.widget

import android.annotation.TargetApi
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import com.whx.ktapplication.R
import com.whx.ktapplication.utils.dp2px

/**
 * Created by whx on 2017/10/23.
 */
class Star @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyArr: Int = 0) : View(context, attrs, defStyArr) {

    companion object {
        private val BACK_COLOR = Color.parseColor("#e9e9e9")
    }
    private var mColor = BACK_COLOR
    private var mWidth = 20
    private var mHeight = 20
    private var mRate = 0.5f
    private var mRadius = 0
    private var mCount = 5
    private var mStarMargin = dp2px(context, 10f)

    private lateinit var mPaint: Paint
    private lateinit var mStarPath: Path

    init {
        initAttrs(attrs)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Star)
        mColor = typedArray.getColor(R.styleable.Star_starColor, BACK_COLOR)
        mRate = typedArray.getFloat(R.styleable.Star_rate, 0.0f)
        mRadius = typedArray.getDimension(R.styleable.Star_starRadius, 20f).toInt()

        typedArray.recycle()

        mPaint = Paint()
        mPaint.color = mColor
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.FILL
        mPaint.strokeWidth = 2f

        mStarPath = Path()
    }

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        setMeasuredDimension(measuredWidth, resolveSize(mRadius * 2, heightMeasureSpec))
//    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        //mRadius = (if (w > h) h else w) / 2
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val outR = mRadius
        val inR = outR * sin(18) / sin(180 - 36 - 18) + 3

        if (mRate > 1) {
            for (i in 1..mRate.toInt()) {

                if (i == 1) {
                    canvas?.translate(mRadius.toFloat(), mRadius.toFloat())
                    canvas?.rotate(-18f)
                    setStarPath(outR.toFloat(), inR)
                    canvas?.drawPath(mStarPath, mPaint)
                } else {
                    canvas?.rotate(18f)
                    canvas?.translate(mRadius * 2 + mStarMargin, 0f)

                    canvas?.rotate(-18f)
                    setStarPath(outR.toFloat(), inR)

                    canvas?.drawPath(mStarPath, mPaint)
                }
            }
            canvas?.rotate(18f)
            canvas?.translate(mRadius * 2 + mStarMargin, 0f)
            drawRateStar(canvas, mRate - mRate.toInt())
        } else {
            canvas?.translate(mRadius.toFloat(), mRadius.toFloat())
            setStarPath(outR.toFloat(), inR)
            drawRateStar(canvas, mRate)
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun drawRateStar(canvas: Canvas?, rate: Float) {
        val rf = RectF((-mRadius).toFloat(), (-mRadius).toFloat(), mRadius * rate, (mRadius).toFloat())
        canvas?.rotate(-18f)

        canvas?.drawPath(mStarPath, mPaint)

//        val sc = canvas?.saveLayer(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), mPaint)


        //val rf = RectF(10f, 20f, 30f, 40f)
        val paint = Paint()
        paint.color = Color.CYAN
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_ATOP)

        val dstPath = Path()
        dstPath.addRect(rf, Path.Direction.CW)

        dstPath.op(mStarPath, Path.Op.INTERSECT)

        canvas?.drawPath(dstPath, paint)

        paint.xfermode = null
        //canvas?.restoreToCount(sc!!)
    }
    private fun setStarPath(outR: Float, inR: Float) {
        mStarPath.moveTo(outR * cos(72 * 0), outR * sin(72 * 0))
        mStarPath.lineTo(inR * cos(72 * 0 + 36), inR * sin(72 * 0 + 36))

        for (i in 1..4) {
            mStarPath.lineTo(outR * cos(72 * i), outR * sin(72 * i))
            mStarPath.lineTo(inR * cos(72 * i + 36), inR * sin(72 * i +36))
        }

        mStarPath.close()
    }

    /**
     * 设置百分比
     */
    fun setRate(rate: Float) {
        mRate = when {
            rate < 0.0 -> 0.0f
            rate > 1.0 -> 1.0f
            else -> rate
        }
        invalidate()
    }

    /**
     * 设置星星的个数，范围 1~10, 默认5个
     */
    fun setStarCount(count: Int) {
        mCount = when {
            count < 1 -> 1
            count > 10 -> 10
            else -> count
        }
    }
    private fun cos(angle: Int): Float = Math.cos(angle * Math.PI / 180).toFloat()

    private fun sin(angle: Int): Float = Math.sin(angle * Math.PI / 180).toFloat()
}