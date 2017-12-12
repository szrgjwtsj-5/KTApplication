package test

import com.whx.ktapplication.data.A
import com.whx.ktapplication.utils.full2Half
import com.whx.ktapplication.utils.half2Full
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/**
 * Created by whx on 2017/7/31.
 */
const val topConstValue = "topConstValue"
val topValue = "topValue"

class Test {
    var num = 0
    var str = "test"

    fun add() {
        var lock = str.substring(1)

        synchronized(str) {
            num ++
        }
    }

    fun sub() {
        var lock = str.substring(1)

        synchronized(str) {
            num --
        }
    }

}
fun main(args: Array<String>) {

    var test = Test()

    for (i in 0..9) {
        object : Thread() {
            override fun run() {
                super.run()

                test.add()
                print(name + " add " + test.num + "       ")

                test.sub()
                println(name + " sub " + test.num)


                //sleep(1000)
            }
        }.start()
    }
}
