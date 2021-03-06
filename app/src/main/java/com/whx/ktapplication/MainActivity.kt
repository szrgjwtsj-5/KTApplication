package com.whx.ktapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.whx.ktapplication.domain.Forecast
import com.whx.ktapplication.domain.RequestForecastCommand
import com.whx.ktapplication.utils.toast
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ForecastListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)

        toTest.setOnClickListener { v ->
            startActivity<TestActivity>()
            /*val intent = Intent(MainActivity@this, javaClass<TestActivity>())
            startActivity(intent)*/ }

//        val url = "http://openweathermap.org/city/1816670"

        loadData()
    }

    private fun loadData() {
        async {
            val result = RequestForecastCommand("94043").execute()

            uiThread {
                adapter = ForecastListAdapter(result)
                adapter.setItemClickListener(object : OnItemClickListener {
                    override fun invoke(forecast: Forecast) {
                        toast(forecast.date)
                    }
                })

                recyclerView.adapter = adapter
            }
        }
    }
}
