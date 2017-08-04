package com.whx.ktapplication.inline

import android.os.Build

/**
 * Created by whx on 2017/8/2.
 * 内联函数和普通函数不同，一个内联函数在编译时会被替换掉，而不是真正的方法调用，
 * 这在一些情况下可以减少内存分配和运行时开销。比如，有一个函数，它只接收一个函数作为参数，
 * 如果接收的是一个普通函数，内部会创建一个含有这个函数的对象，而内联函数则会把我们
 * 调用这个函数的地方替换掉，所以不需要为此生成一个内部对象。
 */

inline fun supportsLollipop(code: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        code()
    }
}