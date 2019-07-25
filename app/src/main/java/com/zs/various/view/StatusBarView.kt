package com.zs.various.view

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * @Author: zs
 * @Date: 2019-07-19 16:19
 *
 * @Description:
 */
class StatusBarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context , attrs , defStyleAttr){

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        var statusBarHeight = getStatusBarHeight(context)
        var newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(statusBarHeight), View.MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)

    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    fun getStatusBarHeight(context: Context): Int {

        val resources = context.resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)

    }


}