package test

/**
 * Created by whx on 2017/8/10.
 */

//嵌套类
class Outer1 {
    private var value = 1

    class Inner1{
        var inValue = 2

        fun method() {
            // println(value)
            println(Outer1().value)
        }
    }

    fun func() {
        // println(inValue)
        // method()
        println(Inner1().inValue)
        println()

    }
}

//内部类
class Outer2 {

    private var value1 = 233

    inner class Inner2 {
        private var inValue = 666

        fun method() {
            println(value1)
            func()
        }
    }

    private fun func() {
        // println(inValue)
        // println(Inner2().inValue)
    }
}

//匿名内部类
interface DoSomething {
    fun dooo()
}
class Outer3 {
    private val value = "i am outer value"

    private val hhh = object : DoSomething {
        override fun dooo() {
            println(value)
        }
    }
}

//局部内部类
class Outer4 {
    private var out = 233
    fun func() {
        val value = "hhhhh"

        class Inner4 {
            private var inner = 666
            fun method() {
                println(value)
                println(out)
            }
        }
    }

    fun hhh() {
        // println(inner)
        // println(Inner4().inner)
        // println(Outer4().Inner4())
        // println(Outer4.Inner4())
    }
}