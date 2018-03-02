package test

import android.content.Context
import com.whx.ktapplication.utils.toast

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

    val nums = arrayListOf(1, 2, 3, 4, 5, 6)

    run loop@ {
        nums.forEach {
            println(it)
            if (it == 3) {
                return@loop
            }
        }
    }

}

fun blalba(context: Context) {
    context.toast("fuck")
}
