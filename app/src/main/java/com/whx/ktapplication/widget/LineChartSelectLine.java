package com.whx.ktapplication.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import com.whx.ktapplication.R;


/**
 * Created by xuqianying on 16/8/23.
 */
public abstract class LineChartSelectLine extends View {
    //文本框和线之间的距离
    private float mDesMarginWithLine;
    //文本框内部左边padding
    protected float mTextLeftPadding;
    //文本框内部右边padding
    protected float mTextRightPadding;
    //线的顶点内半径
    private float mInnerR;
    //线的顶点外半径
    private float mOutterR;
    //线顶点的位置的margin
    private float mSelectLineMargin;
    //下级业绩文本框文字之间的间隔
    protected float mSubTextPadding;
    //y轴最低点的margin
    private float mYBottomMargin;
    protected float mDesLeft;

    protected Paint mTextPaint;
    private Paint mPointPaint;
    private Paint mSelectLinePaint;
    private Paint mTextBackgroundPaint;
    protected Paint mTextPointPaint;

    private boolean mIsGlobalLayout;
    private boolean mIsDataReady;
    protected boolean mIsPrepared;
    private float mMeasuredH;
    protected float mDescWidth;
    protected float mDescHeight;
    protected boolean mIsSubChartView;
    protected int mIndex = -1;
    private float mX;
    protected String mMonthOnMonthText = "环比";

    public LineChartSelectLine(Context context) {
        this(context, null);
    }

    public LineChartSelectLine(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartSelectLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        Resources res = getResources();

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SelectLine);
            mDesMarginWithLine = typedArray.getDimension(R.styleable.SelectLine_desMarginWithLine, 9);
            mTextLeftPadding = typedArray.getDimension(R.styleable.SelectLine_textLeftPadding, 7);
            mTextRightPadding = typedArray.getDimension(R.styleable.SelectLine_textRightPadding, 20);
            mInnerR = typedArray.getDimension(R.styleable.SelectLine_innerR, 3);
            mOutterR = typedArray.getDimension(R.styleable.SelectLine_outerR, 5);
            mSelectLineMargin = typedArray.getDimension(R.styleable.SelectLine_selectLineMargin, 11);
            mSubTextPadding = typedArray.getDimension(R.styleable.SelectLine_subTextPadding, 8);
            mYBottomMargin = typedArray.getDimension(R.styleable.SelectLine_bottomMargin, 22);
            typedArray.recycle();
        }

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(9);

        mPointPaint = new Paint();
        mPointPaint.setAntiAlias(true);
        mPointPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setColor(Color.parseColor("#8d99b0"));

        mSelectLinePaint = new Paint();
        mSelectLinePaint.setAntiAlias(true);
        mSelectLinePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mSelectLinePaint.setStrokeWidth(1);
        mSelectLinePaint.setColor(Color.parseColor("#205887FB"));

        mTextBackgroundPaint = new Paint();
        mTextBackgroundPaint.setAntiAlias(true);
        mTextBackgroundPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextBackgroundPaint.setStyle(Paint.Style.FILL);
        mTextBackgroundPaint.setShadowLayer(3, 0, 0, Color.parseColor("#148D8D8D"));

        mTextPointPaint = new Paint();
        mTextPointPaint.setAntiAlias(true);
        mTextPointPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPointPaint.setStyle(Paint.Style.FILL);
        mMeasuredH = 225;

        setLayerType(LAYER_TYPE_SOFTWARE, null);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mIsGlobalLayout = true;
                if (mIsDataReady) {
                    prepareSelectData();
                    prepare();
                }
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    public void refresh(int index, float x, boolean isSubChartView) {

//        if (mIndex == index) {
//            return;
//        }

        mIsSubChartView = isSubChartView;

        mIndex = index;
        mX = x;
        mIsDataReady = true;
        if (mIsGlobalLayout) {
            prepareSelectData();
            prepare();
            invalidate();
        }

    }

    public void clearView() {
        mIsPrepared = false;
        mIndex = -1;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mIsPrepared) {
            drawSelectedLine(canvas);
            drawSelectLinePoint(canvas);
            drawDescriptionBg(canvas);
            drawDescription(canvas);
        }
    }

    private void drawSelectedLine(Canvas canvas) {
        canvas.drawLine(mX, mSelectLineMargin, mX, mMeasuredH - mYBottomMargin, mSelectLinePaint);
    }

    private void drawSelectLinePoint(Canvas canvas) {
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setColor(getResources().getColor(android.R.color.white));
        mPointPaint.setShadowLayer(3, 0, 0, Color.parseColor("#3F8D99B0"));
        mPointPaint.setStrokeWidth(1);
        canvas.drawCircle(mX, 6, mOutterR, mPointPaint);
        mPointPaint.setColor(Color.parseColor("#8D99B0"));
        mPointPaint.setShadowLayer(0, 0, 0, 0);
        mPointPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mX, 6, mInnerR, mPointPaint);
    }

    private void drawDescriptionBg(Canvas canvas) {
        float left;
        float right;
        float top = 2;
        float bottom = mDescHeight;
        //如果左边放不下了，description放在右边
        if (mDescWidth + mDesMarginWithLine > mX) {
            left = mX + mDesMarginWithLine;
        } else {
            left = mX - mDesMarginWithLine - mDescWidth;
        }
        if (!mIsSubChartView) {
            mTextBackgroundPaint.setColor(getResources().getColor(android.R.color.white));
        } else {
            mTextBackgroundPaint.setColor(Color.parseColor("#26FFFFFF"));
        }
        mDesLeft = left;
        right = left + mDescWidth;
        RectF rect = new RectF((int) left, (int) top, (int) right, (int) bottom);
        canvas.drawRoundRect(rect, 2, 2, mTextBackgroundPaint);
    }

    public void setMonthOnMonthText(String monthOnMonthText) {
        this.mMonthOnMonthText = monthOnMonthText;
    }

    public abstract void prepareSelectData();

    //计算说明区的长和宽
    public abstract void prepare();

    //画说明区，横向坐标从mDesLeft开始
    public abstract void drawDescription(Canvas canvas);
}
