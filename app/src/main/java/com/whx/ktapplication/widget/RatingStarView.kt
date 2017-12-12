package com.whx.ktapplication.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import com.whx.ktapplication.R

/**
 * Created by whx on 2017/10/24.
 */
class RatingStarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyArr: Int = 0) : View(context, attrs, defStyArr) {

    private var mStarMargin = 5.0f
    private var mStarCount = 5
    private var mStarSize = 0f
    private var mRate = 0.5f

    private var mFullStarBitmap: Bitmap? = null
    private var mEmptyStarDrawable: Drawable? = null

    private lateinit var mPaint: Paint

    init {
        initAttrs(attrs)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingStarView)
        mStarMargin = typedArray.getDimension(R.styleable.RatingStarView_starMargin, 5.0f)
        mStarCount = typedArray.getInt(R.styleable.RatingStarView_starCount, 5)
        mStarSize = typedArray.getDimension(R.styleable.RatingStarView_starSize, 0f)
        mRate = typedArray.getFloat(R.styleable.RatingStarView_starRate, 0f)
        mEmptyStarDrawable = typedArray.getDrawable(R.styleable.RatingStarView_starEmpty)
        mFullStarBitmap = drawableToBitmap(typedArray.getDrawable(R.styleable.RatingStarView_starFull))

        typedArray.recycle()

        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.shader = BitmapShader(mFullStarBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension((mStarSize * mStarCount + mStarMargin * (mStarCount - 1)).toInt(), mStarSize.toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        mEmptyStarDrawable?.let {

            for (i in 0 until mStarCount) {
                (mEmptyStarDrawable as Drawable).setBounds(((mStarMargin + mStarSize) * i).toInt(), 0, ((mStarMargin + mStarSize) * i + mStarSize).toInt(), mStarSize.toInt())
                (mEmptyStarDrawable as Drawable).draw(canvas)
            }
        }

        if (mRate > 1) {
            canvas?.drawRect(0f, 0f, mStarSize, mStarSize, mPaint)
            if(mRate - mRate.toInt() == 0.0f) {
                for (i in 1 until mRate.toInt()) {
                    canvas?.translate(mStarMargin + mStarSize, 0f)
                    canvas?.drawRect(0f, 0f, mStarSize, mStarSize, mPaint)
                }
            } else {
                for (i in 1 until mRate.toInt()) {
                    canvas?.translate(mStarMargin + mStarSize, 0f)
                    canvas?.drawRect(0f, 0f, mStarSize, mStarSize, mPaint)
                }
                canvas?.translate(mStarMargin + mStarSize, 0f)
                canvas?.drawRect(0f, 0f, mStarSize * (mRate - mRate.toInt()), mStarSize, mPaint)
            }
        } else {
            canvas?.drawRect(0f, 0f, mStarSize * mRate, mStarSize, mPaint)
        }
    }

    private fun drawableToBitmap(drawable: Drawable?) : Bitmap? {
        if (drawable == null)
            return null
        val bitmap = Bitmap.createBitmap(mStarSize.toInt(), mStarSize.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, mStarSize.toInt(), mStarSize.toInt())
        drawable.draw(canvas)

        return bitmap
    }
}