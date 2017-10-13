package com.whx.ktapplication.grammar

import android.text.TextUtils
import android.widget.TextView
import com.whx.ktapplication.MyApplication

/**
 * Created by whx on 2017/9/18.
 */
fun testLet(): Int {
    "string".let {
        println(it)
        val tst = it.plus(" - hhh")
        //plus("hh")
        println(tst)
        return 1
    }
}

fun testRun() {
    val list = mutableListOf("a", "b", "c")
    var what = 0

    what = list.run{
        this.add("d")
        add("e")
        size
    }

    val a = run {
        println("test")
        666
    }

    println(list)
    println(what)

    //println(a)
}

fun testApply() {
    val textView = TextView(MyApplication.instance()).apply {
        text = "hhhhh"
        this.setSingleLine()
        ellipsize = TextUtils.TruncateAt.END
    }
}

fun testWith() {
    val list = mutableListOf("a", "b")

    val what = with(list) {
        this.add("c")
        add("d")
        size
    }

    println(what)
}

fun testAlso() {
    val list = mutableListOf("a", "b", "c")

    list.also {
        println(it)
        it.add("d")
    }
    println(list)
}
fun main(args: Array<String>) {
    //testLet()
    //testRun()
    //testWith()
    testAlso()
}