package com.zs.various.bean

/**
 * @Author: zs
 * @Date: 2019-08-03 16:33
 *
 * @Description:
 */
class MessageBean{

    var type: Int = 0
    var message: String? = null

    constructor(message: String?) {
        this.message = message
    }

    constructor(type: Int, message: String?) {
        this.type = type
        this.message = message
    }


}