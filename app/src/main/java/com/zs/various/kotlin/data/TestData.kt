package com.zs.various.kotlin.data

import com.zs.various.KotlinData

/**
 * @Author: zs
 * @Date: 2020/12/25 10:08 AM
 *
 * @Description:
 *
 * 默认生成以下方法：
 * set()  get() equals() hashCode()
 * toString() componentN() copy()
 *
 * 默认是final类型不能被继承
 * 解决方案：使用官方给出的插件来解决 noarg、allopen
 *
 */
data class UserData(
        var name: String,
        var age: Int = 20,
        var avatar: String? = null,
        var userInfo: UserInfo? = null
)

data class UserInfo(
        var info1: String? = null,
        var info2: String? = null,
        var info3: Int = 0
)

class OtherInfo {
    var info1: String? = null
    var info2: String? = null
    var info3: Int = 0
}

@KotlinData
data class OneData(var arg: String)

class NewData(var arg2: String, var arg3: Int, arg: String) : OneData(arg)



