package com.zs.various.util

import androidx.core.widget.TextViewCompat
import android.util.TypedValue
import android.widget.TextView

/**
 *
Created by zs
Date：2018年 10月 30日
Time：16:25
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class MoreUtil{

    companion object {

        /**
         * 设置字体自适应
         * @param min  字体最小字号
         * @param max  字体最大字号
         * @param textViews 要设置的textview
         */
        fun autotextSize(min: Int , max: Int , vararg textViews: TextView){
            for (textView in textViews){
                TextViewCompat.setAutoSizeTextTypeWithDefaults(textView, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM)
                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(textView, min, max, 1, TypedValue.COMPLEX_UNIT_SP)
            }
        }

    }

}