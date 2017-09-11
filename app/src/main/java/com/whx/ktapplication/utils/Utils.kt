package com.whx.ktapplication.utils

import android.widget.Toast
import com.whx.ktapplication.MyApplication

/**
 * Created by whx on 2017/8/29.
 */
fun showMsg(msg: String) {
    Toast.makeText(MyApplication.instance(), msg, Toast.LENGTH_SHORT).show()
}