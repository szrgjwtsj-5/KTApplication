package com.whx.ktapplication.domain

import com.whx.ktapplication.ForecastRequest
import com.whx.ktapplication.data.ForecastResult
import com.whx.ktapplication.domain.Forecast as ModelForecast
import com.whx.ktapplication.data.Forecast
import java.text.DateFormat
import java.util.*

/**
 * Created by whx on 2017/8/1.
 */
interface Command<T> {
    fun execute(): T
}


class ForecastDataMapper {

    fun convertFromDataModel(forecast: ForecastResult): ForecastList {
        return ForecastList(forecast.city.name, forecast.city.country, convertForecastListToDomain(forecast.list))
    }

    fun convertForecastListToDomain(list: List<Forecast>): List<ModelForecast> {
        //list的映射方法，遍历list通过调用map里的转换方法将list中的数据类型A转换成另一种类型B并返回B的List
        return list.map { convertForecastItemToDomain(it) }
    }

    fun convertForecastItemToDomain(forecast: Forecast): ModelForecast {
        return ModelForecast(convertDate(forecast.dt), forecast.weather[0].description,
                forecast.temp.max.toInt(), forecast.temp.min.toInt(),
                generateIconUrl(forecast.weather[0].icon))
    }

    fun convertDate(date: Long): String {
        val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        return dateFormat.format(date * 1000)
    }

    fun generateIconUrl(iconCode: String): String = "http://openweathermap.org/img/w/$iconCode.png"
}

class RequestForecastCommand(val zipCode: String) : Command<ForecastList> {
    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        return ForecastDataMapper().convertFromDataModel(forecastRequest.execute())
    }
}