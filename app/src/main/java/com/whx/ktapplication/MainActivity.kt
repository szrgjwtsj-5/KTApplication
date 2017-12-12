package com.whx.ktapplication

import android.content.Intent
import android.net.Uri
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
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ForecastListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val intent = Intent(this, TestActivity::class.java)

        val size = 504
        val sb = StringBuilder(size)
        val strList = ArrayList<String>()

        for (i in 0..size) {
            sb.append("a")
        }
        for (i in 0..511) {
            strList.add(sb.toString())
        }

        toTest.setOnClickListener {
            //intent.putExtra("test_intent_size", strList)

            startActivity(intent.apply { putExtra("hhh", Pair(1, 2)) })
        }

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
