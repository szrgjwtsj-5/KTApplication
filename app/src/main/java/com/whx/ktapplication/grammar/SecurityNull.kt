package com.whx.ktapplication.grammar

import test.JavaTest

/**
 * Created by whx on 2017/8/15.
 */

val a: Int? = null

fun nullVal() {
    //在类型后面加一个？声明一个可null的变量

    //根据前面的声明，编译器知道a是可null的，所以在
    //进行null检查之前不能使用这个对象调用可能引起错误的方法
    println(a?.toLong())     //编译不通过
    //当我们检查了一个对象的可null性之后这个对象会自动转化为不可null类型
    //但是在if语句之外仍需要检查
    if (a != null) {
        println(a.toLong())
    }

    //注意像toString这样的可接受null参数的方法，可以不对 a 进行null检查
    println(a.toString())
}

fun operators(): Boolean {
    //在Kotlin中我们可以使用安全访问操作符 " ？"来进行null检查
    //这个操作符的作用是只有当被检查的变量不为null时，才执行变量调用的方法,否则不会做任何事
    println(a?.toLong())

    //二元运算符 "?:" ，如果左侧不为空则返回左侧，否则返回右侧
    println(a ?: "233")
    //return 和 throw在Kotlin中也是一个表达式，所以该表达式
    // 通常可以用于一旦前面的值为空时返回一个值或者抛出异常
    val str = a?.toString() ?: return false
    //或
    val str2 = a?.toString() ?: throw IllegalArgumentException()    //注意在调用toString方法时，如果不加 ？，
    // toString方法永远不会返回null，所以就不会执行到？：后面的代码
    //加了？之后，如果a为null，则不执行toString方法，可以执行到？：后面的代码


    /*
       还有一种情况，我们确定我们是在用一个非null的变量，但是它的类型确实可null的，
       我们可以使用 "!!" 操作符来强制编译器执行可null类型时跳过限制检查
     */
    a!!.toLong()

    return true
}

fun main(vararg args: String) {

    val test = JavaTest()
    val myObj: Any? = test.getObject()
}