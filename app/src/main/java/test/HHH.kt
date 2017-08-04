package test

/**
 * Created by whx on 2017/7/31.
 */

    val test = Test()

    fun method() {
        println(test.name)
        println(test.age)

        test.name = "hhh"
        test.age = 123

        println(test.name)
        println(test.age)
    }

    fun main(args: Array<String>) {
        method()
    }
