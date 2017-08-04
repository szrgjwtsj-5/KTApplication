package com.whx.ktapplication.data

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.whx.ktapplication.MyApplication

/**
 * Created by whx on 2017/8/2.
 */
object CityForecastTable {
    val NAME = "CityForecast"
    val ID = "_id"
    val CITY = "city"
    val COUNTRY = "country"
}

object DayForecastTable {
    val NAME = "DayForecast"
    val ID = "_id"
    val DATE = "date"
    val DESCRIPTION = "description"
    val HIGH = "high"
    val LOW = "low"
    val ICON_URL = "iconUrl"
    val CITY_ID = "cityId"
}

class ForecastDbHelper : SQLiteOpenHelper(MyApplication.instance(), ForecastDbHelper.DATA_NAME,
        null, ForecastDbHelper.DB_VERSION) {

    companion object {
        val DATA_NAME = "forecast.db"
        val DB_VERSION = 1
        //这个属性使用了lazy委托，表示直到它真正被调用的时候才会被创建
        //一般lazy委托的代码快可以阻止在多个不同线程中创建多个对象，即线程安全的
        val instance: ForecastDbHelper by lazy { ForecastDbHelper() }
    }

    override fun onCreate(db: SQLiteDatabase?) {

    }

    override fun onUpgrade(db: SQLiteDatabase?, old: Int, new: Int) {

    }
}