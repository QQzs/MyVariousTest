package com.zs.various.util.extension

/**
 *
Created by zs
Date：2019年 03月 28日
Time：13:49
—————————————————————————————————————
About:
—————————————————————————————————————
 */
fun String.isNullEmpty(): Boolean{
    return this == null || ""== this || this.trim().isEmpty() || "null" == this.trim()
}
