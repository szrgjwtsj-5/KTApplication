package com.whx.ktapplication.model

/**
 * Created by whx on 2017/12/12.
 */
class BarChartBean(
        @JvmField val name: String,
        @JvmField val scale: String,
        @JvmField val revenue: MutableList<BarChartItemBean>
)