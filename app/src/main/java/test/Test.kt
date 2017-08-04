package test

/**
 * Created by whx on 2017/7/31.
 */
class Test {
    var name = "zhangsan"
        get() = field.toUpperCase()
        set(value) {
            field = "Name: $value"
        }
    var age = 250
        get() = field + 2
}