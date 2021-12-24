package com.zs.various.kotlin.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.zs.various.kotlin.listener.TestListener

/**
 * @Author: zs
 * @Date: 2020/12/27 6:26 PM
 *
 * @Description:自定义View
 */
class TestView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    /**
     * 方式一：
     * 回调监听 Java方式
     */
    var testListener: TestListener? = null

    /**
     * 方式二：
     * 回调监听 Kotlin lambda表达式
     */
    var callBack = { s: String, i: Int -> Unit}

    /**
     * 方式三：
     * 回调监听 Kotlin lambda表达式 简写 一个参数
     */
    var callBack2: ((String) -> Unit)? = null

    /**
     * 回调监听 Kotlin lambda表达式 简写 两个参数
     */
    var callBack3: ((String, Int) -> Unit)? = null

    /**
     * 初始化方法
     */
    init {
        // 此时view还没渲染完成
        post {
            // 渲染完成
        }
    }

    fun backData() {
        callBack.invoke("back", 0)
    }

    fun backData2() {
        callBack2?.invoke("back")
        callBack3?.invoke("back", 1)
    }

    fun setCallBack(listener: TestListener?) {
        this.testListener = listener
    }

}
