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

    var testListener: TestListener? = null

    var callBack = { s: String, i: Int -> Unit}

    var callBack2: ((String) -> Unit)? = null
    var callBack3: ((String, Int) -> Unit)? = null

    /**
     * 初始化方法
     */
    init {

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
