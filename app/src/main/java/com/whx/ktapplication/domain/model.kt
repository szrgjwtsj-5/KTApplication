package com.whx.ktapplication.domain

/**
 * Created by whx on 2017/8/2.
 */

data class ForecastList(val city: String, val country: String, val dailyForecast:List<Forecast>)

data class Forecast(val date: String, val description: String, val high: Int, val low: Int, val iconUrl: String)