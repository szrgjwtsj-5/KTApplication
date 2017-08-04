package com.whx.ktapplication

import android.content.Context
import android.view.View
import android.widget.Toast

/**
 * Created by whx on 2017/8/1.
 * 包含一系列扩展函数的工具文件
 */


/**
 * 扩展函数——在一个类上增加一种新的行为，即使没有这个类代码的访问权限。
 * 扩展函数表现的就像是这个类中的方法一样，可以使用this关键字和所有public方法。
 */
fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

val View.ctx: Context
    get() = context