package com.whx.ktapplication

import com.whx.ktapplication.domain.Forecast

/**
 * Created by whx on 2017/8/2.
 */

interface OnItemClickListener {
    //当调用时，invoke方法可以省略
    operator fun invoke(forecast: Forecast)
}