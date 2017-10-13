package com.whx.ktapplication.data

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by whx on 2017/8/3.
 */
val list = listOf(1, 2, 3, 4, 5)
val listWithNull = listOf(1, 2, 3, 4, null)

fun testAny() {

    //至少有一个元素符合给出的判断条件，则返回true
    println(list.any { it % 2 == 0 })
    println(list.any { it > 10 })
}

fun testAll() {
    //全部元素符合给出的判断条件，则返回true
    println(list.all { it < 10 })
    println(list.all { it % 2 == 0 })
}

fun testCount() {
    //返回符合给出判断条件的元素的个数
    println(list.count { it % 2 == 0 })

    println(list.count())
}

fun testFoldAndReduce() {
    //在一个初始值的基础上从第一项到最后一项通过一个函数累计所有的元素
    println(list.fold(4) { total, next -> total + next })
    //和fold一样，但是顺序是从最后一项到第一项
    println(list.foldRight(4) { total, next -> total + next})

    //和fold函数类似，只不过没有初始值
    println(list.reduce { total, next -> total + next })
    println(list.reduceRight { total, next -> total + next })
}

fun testForeach() {
    //遍历所有元素，并执行指定操作
    list.forEach { print(it.toString() + " ") }
    println()
    //与foreach类似，但同时还能获得元素的index
    list.forEachIndexed { index, value -> print("position $index contains a $value") }
    println()
}

fun testMax() {
    //返回最大的一项，如果没有则返回null
    println(list.max())
    //根据给定的函数返回最大的一项，如果没有则返回null
    println(list.maxBy { -it })
}

fun testMin() {
    //返回最小的一项，如果没有则返回null
    println(list.min())
    //根据给定的函数返回最小的一项，如果没有则返回null
    println(list.minBy { -it })
}

fun testNone() {
    //如果没有任何元素与给定的函数匹配，则返回true
    println(list.none { it % 7 == 0 })
}

fun testSumBy() {
    //返回所有每一项通过函数转换之后的数据的综合
    println(list.sumBy { it % 2 })
}

fun testDrop() {
    //返回包括去掉前n个元素的所有元素的列表
    println(list.drop(4))
    //返回根据给定函数从第一项开始去掉指定元素的列表
    println(list.dropWhile { it > 3 })
    //返回根据给定函数从最后一项开始去掉指定元素的列表
    println(list.dropLastWhile { it < 4 })
}

fun testFilter() {
    //过滤所有符合给定函数条件的元素
    println(list.filter { it % 2 == 0 })
    //过滤所有不符合给定函数条件的元素
    println(list.filterNot { it % 2 == 0 })
    //

    println(listWithNull.filterNotNull())
}

fun testSlice() {
    //过滤一个list中指定index的元素
    println(list.slice(listOf(1, 2)))
}

fun testTake() {
    //返回从第一个开始的n个元素
    println(list.take(2))
    //返回从最后一个开始的n个元素
    println(list.takeLast(2))
    //返回从第一个开始符合给定函数条件的元素
    println(list.takeWhile { it < 3 })
}

fun testMap() {
    //遍历所有元素，为每一个创建一个集合，最后把所有集合放在一个集合里
    println(list.flatMap { listOf(it, it + 1) })
    //返回一个根据给定函数分组后的map
    println(list.groupBy { if (it % 2 == 0) "even" else "odd" })
    //返回一个每一个元素根据给定函数转换成的list
    println(list.map { it * 2 })
    //返回一个每个元素根据给定的包含元素index的函数所转换所组成的list
    println(list.mapIndexed { index, it -> index * it })
    //返回一个每个非null元素根据给定函数转换所成的list
    println(listWithNull.mapNotNull{ it?.times(2) })
}

fun testOperate() {
    //找指定元素，找到返回true
    println(list.contains(2))
    //返回指定index的元素，如果index越界则抛出IndexOutOfBoundsException
    println(list.elementAt(2))
    //返回指定index的元素，如果index越界则根据给定函数返回默认值
    println(list.elementAtOrElse(9, {it * 2}))
    //返回指定index的元素，如果index越界则返回null
    println(list.elementAtOrNull(10))
    //返回符合指定函数条件的第一个元素
    println(list.first {it % 2 == 0})
    //返回符合指定函数条件的第一个元素，如果没有则返回null
    println(list.firstOrNull { it % 2 == 0 })
    //返回指定元素的第一个index，不存在则返回-1
    println(list.indexOf(2))
    //返回第一个满足给定函数条件的元素的index，没有符合则返回-1
    println(list.indexOfFirst { it % 2 == 0 })
    //返回最后一个满足给定函数条件的元素的index，没有符合则返回-1
    println(list.indexOfLast { it % 2 == 0 })
    //返回符合给定函数的最后一个元素
    println(list.last { it % 2 == 0})
    //返回符合给定函数的单个元素，如果没有符合或者超过一个，抛出异常
    println(list.single { it % 5 == 0})
    //返回符合给定函数的单个元素，如果没有符合或者超过一个，则返回null
    println(list.singleOrNull { it % 5 == 0})
}

fun testProduce() {
    val list1 = listOf(1, 2, 3, 4, 5, 6)
    val list2 = listOf(2, 2, 3, 4, 5, 5, 6)
    //把两个集合合并成一个新的，相同index的元素根据给定函数进行合并成新元素，返回新集合，新集合的
    //大小由元素数少的集合决定
    println(list1.zip(list2) {it1, it2 -> it1 + it2})
    //把一个给定的集合分割成两个，第一个集合由给定集合的每一个元素匹配给定函数返回true的元素
    //组成，第二个集合由返回false的元素组成
    val pair = list.partition { it % 2 == 0 }
    println(pair)
    //plus 函数将两个集合组合成一个，等同于用 + 操作符，也可以添加一个单独的元素
    val list_s = list1.plus(list2)

    list1.plus(7)
    list1 + list2

    println(list_s)
}

fun testOrder() {
    val list1 = listOf(3, 2, 5, 6, 1)
    //反转列表
    println(list.reversed())
    //返回自然排序的list
    println(list1.sorted())
    //返回根据给定函数排序后的list
    println(list1.sortedBy { -it })
    //返回一个降序排序的list，同理sortedByDescending {}
    println(list1.sortedDescending())
}

open class A {
    fun hh(){

    }

}

fun main(args: Array<String>) {


}