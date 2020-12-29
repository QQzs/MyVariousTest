package com.zs.various.kotlin.extension

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

/**
 * @Author: zs
 * @Date: 2020/12/27 8:35 PM
 *
 * @Description:
 */

fun test(str: String): String {
    return "back$str"
}

fun TextView.setColor(colorRes: Int) {
    this.setTextColor(context.getColor(colorRes))
}

fun ImageView.loadImage(drawableRes: Int) {
    Glide.with(this)
            .load(drawableRes)
            .into(this)
}