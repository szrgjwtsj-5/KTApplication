package com.whx.ktapplication.data

import kotlin.collections.HashMap

/**
 * Created by whx on 2017/8/1.
 * 为数据库创建model类，
 */

//默认的构造函数会接收一个含有属性和对应值的map，由于委托，这些值会根据key
//的名字映射到对应属性中去，属性的名字必须和数据库中对应的名字一致
class CityForecast(val map: MutableMap<String, Any?>, val dailyForecast: List<DayForecast>) {
    var _id: Long by map
    var city: String by map
    var country: String by map

    //这个构造函数是因为我们需要从domain映射到数据库类，传入一个空map，由于委托
    //我们在设置属性值的时候，会自动增加所有值到map中
    constructor(id: Long, city: String, country: String,
                dailyForecast: List<DayForecast>) : this(HashMap(), dailyForecast) {
        this._id = id
        this.city = city
        this.country = country
    }
}

class DayForecast(var map: MutableMap<String, Any?>) {
    var _id: Long by map
    var date: Long by map
    var description: String by map
    var high: Int by map
    var low: Int by map
    var iconUrl: String by map
    var cityId: Long by map

    constructor(date: Long, description: String, high: Int, low: Int,
                iconUrl: String, cityId: Long) : this(HashMap()){
        this.date = date
        this.description = description
        this.high = high
        this.low = low
        this.iconUrl = iconUrl
        this.cityId = cityId
    }
}