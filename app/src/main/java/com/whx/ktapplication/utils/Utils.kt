package com.whx.ktapplication.utils

import android.content.Context
import android.text.TextUtils
import android.util.TypedValue
import android.widget.Toast
import com.whx.ktapplication.MyApplication
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat

/**
 * Created by whx on 2017/8/29.
 */
fun showMsg(msg: String) {
    Toast.makeText(MyApplication.instance(), msg, Toast.LENGTH_SHORT).show()
}

/**
 * 该方法是将半角字符转换成全角字符。<br>
 * 1.全角：指一个字符占用两个标准字符位置。汉字字符和规定了全角的英文字符及国标GB2312-80中的图形符号和特殊字符都是全角字符。
 * </br>一般的系统命令是不用全角字符的，只是在作文字处理时才会使用全角字符。
 * 2.半角：指一字符占用一个标准的字符位置。通常的英文字母、数字键、符号键都是半角的，半角的显示内码都是一个字节。
 * </br>在系统内部，以上三种字符是作为基本代码处理的，所以用户输入命令和参数时一般都使用半角。
 *
 * </br>全角字符unicode编码从65281~65374（十六进制0xFF01 ~ 0xFF5E）
 * </br>半角字符unicode编码从33~126（十六进制0x21~ 0x7E）
 * </br>特例：
 * </br>空格比较特殊，全角为12288（0x3000），半角为 32（0x20）
 * </br><b>注：</b>
 * </br>1. 中文文字永远是全角，只有英文字母、数字键、符号键才有全角半角的概念,一个字母或数字占一个汉字的位置叫全角，占半个汉字的位置叫半角。
 * </br>2. 引号在中英文、全半角情况下是不同的。
 */
fun half2Full(input: String?) : String{
    if (input.isNullOrEmpty())
        return ""
    val chars = input!!.toCharArray()

    for (i in 0 until chars.size) {
        if (chars[i] == ' ')
            chars[i] = 12288.toChar()
        else if (chars[i].toInt() in 33..126)
            chars[i] = (chars[i].toInt() + 65248).toChar()
    }
    return String(chars)
}

fun full2Half(input: String?) : String {
    if (input.isNullOrEmpty())
        return ""
    val chars = input!!.toCharArray()

    for (i in 0 until chars.size) {
        if (chars[i].toInt() == 12288)
            chars[i] = ' '
        else if (chars[i].toInt() in 65281..65374)
            chars[i] = (chars[i].toInt() - 65248).toChar()
    }
    return String(chars)
}

fun dp2px(context: Context, dimen: Float) : Float {
    val scale = context.resources.displayMetrics.density
    return dimen * scale + 0.5f
}

fun sp2px(context: Context, spValue: Float): Float {

    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.resources.displayMetrics)

}
/**
 * 保留n位小数
 */
fun testXX(num: Float) {
    //println(BigDecimal(num.toDouble()).setScale(2).toFloat())    必须有RoundingMode
    println(BigDecimal(num.toDouble()).setScale(2, RoundingMode.HALF_UP).toFloat())
    println(DecimalFormat("#.00").format(num).toFloat())
    println(String.format("%.2f", num).toFloat())
    println(NumberFormat.getInstance().apply { maximumFractionDigits = 2}.format(num).toFloat())
    println(DecimalFormat(".##").format(num).toFloat())
}