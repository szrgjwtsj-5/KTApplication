package com.whx.ktapplication

import android.util.Log
import com.google.gson.Gson
import com.whx.ktapplication.data.ForecastResult
import java.net.URL

/**
 * Created by whx on 2017/8/1.
 */
class ForecastRequest(val zipCode: String) {

    companion object {
        private val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"
        private val URL = "http://api.openweathermap.org/data/2.5/" +
                "forecast/daily?mode=json&units=metric&cnt=7"
        private val COMPLETE_URL = "$URL&APPID=$APP_ID&q="
    }

    fun execute(): ForecastResult {
        val str = COMPLETE_URL + zipCode

        val jsonStr = URL(str).readText()

        return Gson().fromJson(jsonStr, ForecastResult::class.java)
    }
}