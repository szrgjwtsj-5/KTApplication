package com.whx.ktapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.whx.ktapplication.model.BarChartBean
import com.whx.ktapplication.model.BarChartItemBean
import kotlinx.android.synthetic.main.layout_test_barchart.*


/**
 * Created by whx on 2017/12/12.
 */
class BarChartActivity : AppCompatActivity() {

    private var data: MutableList<BarChartBean> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_test_barchart)

        createData()

    }

    private fun createData() {

        confirm.setOnClickListener {
            val num = barNum.text.toString().toInt()
            data.clear()
            for (i in 0 until num) {
                val bean = BarChartBean("全部门店", "w", mutableListOf())
                for (j in 0..1) {
                    bean.revenue.add(BarChartItemBean("2017/11", (Math.random() * 3000), "12.9%"))
                }
                data.add(bean)
            }
            barChart.setData(data)
        }

    }

}