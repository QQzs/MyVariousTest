package com.zs.various.activity

import androidx.core.content.ContextCompat
import android.text.TextUtils
import android.widget.TextView
import com.zs.various.R
import com.zs.various.base.BaseActivity
import kotlinx.android.synthetic.main.activity_view_flipper.*

/**
 *
Created by zs
Date：2019年 02月 27日
Time：11:15
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class ViewFlipperActivity: BaseActivity(){
    override fun setLayoutId(): Int {
        return R.layout.activity_view_flipper
    }

    override fun initView() {

        for (index in 0 .. 5){
            var tv = TextView(this)
            tv.text = "index = $index dddd   ddddddddddd   ddddddddddddddddddddddddddddd"
            tv.textSize = 16f
            tv.setTextColor(ContextCompat.getColor(this,R.color.color_1))
            tv.maxLines = 1
            tv.ellipsize = TextUtils.TruncateAt.END
            flipper?.addView(tv)
        }

    }

    override fun initData() {
    }


}