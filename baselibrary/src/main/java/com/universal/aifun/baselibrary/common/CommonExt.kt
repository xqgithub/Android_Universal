package com.universal.aifun.baselibrary.common

import java.util.regex.Pattern

/**
 * Author  guoyw
 * Date    2021/5/18
 * Desc    扩展函数
 */

/**
 * 判断字符串 是否符合数字类型
 */
fun String.toNumber(): Long {
    val pattern: Pattern = Pattern.compile("[0-9]*")
    val flag = pattern.matcher(this).matches()
    return if (flag) {
        this.toLong()
    } else {
        0L
    }
}