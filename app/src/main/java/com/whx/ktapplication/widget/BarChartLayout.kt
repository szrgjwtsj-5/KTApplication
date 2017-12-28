package com.whx.ktapplication.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.whx.ktapplication.R
import com.whx.ktapplication.model.BarChartBean

/**
 * Created by whx on 2017/12/14.
 */
class BarChartLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defArr: Int = 0) : FrameLayout(context, attrs, defArr) {
    private var barChartView: BarChartView? = null
    private var lineSelected: BarChartSelectLine? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.dashboard_line_chart_layout, this)
        barChartView = findViewById(R.id.dash_chart) as? BarChartView
        lineSelected = findViewById(R.id.dash_select) as? BarChartSelectLine

        barChartView?.attachSelectLine(lineSelected!!)
    }

    fun setData(data: MutableList<BarChartBean>) {
        barChartView?.setData(data)
    }
}