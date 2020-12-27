package com.zs.various.activity.kotlin.data

/**
 * @Author: zs
 * @Date: 2020/12/25 10:08 AM
 *
 * @Description:
 */
data class UserData(
        var name: String,
        var age: Int = 20,
        var avatar: String? = null,
        var userInfo: UserInfo? = null
)

data class UserInfo(
        var info1: String,
        var info2: String? = null,
        var info3: Int = 0
)


