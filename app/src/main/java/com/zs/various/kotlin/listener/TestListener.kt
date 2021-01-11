package com.zs.various.kotlin.listener

/**
 * @Author: zs
 * @Date: 2021/1/9 9:38 PM
 *
 * @Description:
 */
interface TestListener {

    fun callBack1()

    /**
     * 在java中，接口中定义的方法不可以实现，实现类必须实现所有方法
     * 在Kotlin中，接口中的方法可提前进行空实现，实现类可不实现其不用的方法
     */
    fun callBack2() {}

}