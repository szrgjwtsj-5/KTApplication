package test

import com.whx.ktapplication.data.A
import java.util.*

/**
 * Created by whx on 2017/7/31.
 */
const val topConstValue = "topConstValue"
val topValue = "topValue"

class Test {
    // const val cc = "cc"
    companion object {
        const val compObjConstValue = "compObjConstValue"
        val compObjValue = "compObjValue"
    }
}
fun main(args: Array<String>) {

    val calendar = Calendar.getInstance()


    println(calendar.get(Calendar.WEEK_OF_MONTH))
    calendar.add(Calendar.WEEK_OF_YEAR, 7)
    println(calendar.get(Calendar.WEEK_OF_MONTH))

}