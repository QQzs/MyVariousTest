package com.zs.various.kotlin.view

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * @Author: zs
 * @Date: 2020/12/27 6:26 PM
 *
 * @Description:自定义View
 */
class TestView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    var callBack: ((String) -> Unit)? = null
    var callBackMore: ((String, Int) -> Unit)? = null

    /**
     * 构造方法
     */
    init {

    }

    fun backData() {
        callBack?.invoke("back")
        callBackMore?.invoke("back", 1)
    }

}
