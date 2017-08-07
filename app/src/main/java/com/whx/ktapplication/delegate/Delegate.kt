package com.whx.ktapplication.delegate

import android.database.sqlite.SQLiteDatabase
import com.whx.ktapplication.data.ForecastDbHelper
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by whx on 2017/8/2.
 */

/**
 * 创建一个notnull的委托，只能被赋值一次，如果第二次赋值就抛异常
 * 自定义委托需要实现ReadWriteProperty或ReadOnlyProperty，具体取决于我们被委托的对象是var还是val
 * 这个委托可以作用在任何非null的类型，它接收任何类型的引用，然后像getter和setter那样使用T
 */
class NotNullSingleValueVar<T> : ReadWriteProperty<Any?, T> {

    private var value: T? = null

    //getter函数，如果已经初始化则返回值，否则抛异常
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("value not initialized")
    }

    //setter函数，如果值为null则设置值，否则抛异常
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = if (this.value == null) value
        else throw IllegalStateException("value already initialized")
    }
}

object DelegatesExt {
    fun <T> notNullStringValue():
            ReadWriteProperty<Any?, T> = NotNullSingleValueVar()
}

class ViewModel(val db: ForecastDbHelper) {

    //observable委托会帮我们监测我们希望观察的属性变化，当被观察属性的set方法被调用时，会自动执行我们
    //指定的lambda表达式，所以一旦该属性被赋予新值，我们就会接收到被委托的属性，旧值和新值
    var myProperty by Delegates.observable(2) {
        d, old, new ->
        db.onUpgrade(db.readableDatabase, old, new)
    }
    //一个特殊的observable，它让你决定是否这个值需要被保存，它可以被用于在
    //真正保存之前进行一些条件判断，下面这个委托只允许在新的值是 >=0 时执行保存
    //在lambda中，最后一行表示返回值，不需要使用return
    var positiveNumber = Delegates.vetoable(0) {
        d, old, new ->
        new >= 0
    }
}

/**
 * 从map中映射值，属性的值会从一个map中获取value，属性的名字对应这个map中的key，
 * 这个委托可以让我们做一些很强大的事，因为我们可以简单地从一个动态的map中创建一个对象实例
 * 如果我们import kotlin.properties.getValue，我们可以从构造函数映射到val属性来得到
 * 一个不可修改的map，如果我们想去修改map和属性，我们也可以import kotlin.properties.setValue.
 * 类需要一个MutableMap作为构造函数的参数
 */
class Configuration(map: Map<String, Any?>) {
    val width: Int by map
    val height: Int by map
    val dp: Int by map
    val deviceName: String by map
}