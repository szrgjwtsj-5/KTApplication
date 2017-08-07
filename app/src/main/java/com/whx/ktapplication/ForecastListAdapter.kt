package com.whx.ktapplication

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.whx.ktapplication.domain.Forecast
import com.whx.ktapplication.domain.ForecastList
import com.whx.ktapplication.utils.ctx
import kotlinx.android.synthetic.main.forecast_item.view.*

/**
 * Created by whx on 2017/7/31.
 */
class ForecastListAdapter (val weekForecast: ForecastList) : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun getItemCount(): Int = weekForecast.dailyForecast.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        holder?.bindForecast(weekForecast.dailyForecast[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {

        val view = LayoutInflater.from(parent?.ctx).inflate(R.layout.forecast_item, parent, false)

        return ViewHolder(view, onItemClickListener)
    }

    class ViewHolder (view: View, val itemClick: OnItemClickListener) : RecyclerView.ViewHolder (view) {

        fun bindForecast(forecast: Forecast) {
            //with函数是Kotlin标准库的一个函数，接收一个对象和一个扩展函数作为参数，然后使这个这个对象
            //扩展这个函数，这表示我们在大括号里编写的代码会作为对象的一个扩展函数，我们就可以像this一样
            //使用所有的public方法和属性，当我们针对一个对象有多种操作时非常有利于减少代码。
            with(forecast) {
                Picasso.with(itemView.ctx).load(forecast.iconUrl).into(itemView.icon)
                itemView.date.text = forecast.date
                itemView.description.text = forecast.description
                itemView.maxTemp.text = "${forecast.high}"
                itemView.minTemp.text = "${forecast.low}"

                itemView.setOnClickListener { itemClick(forecast) }
            }
        }
    }
}