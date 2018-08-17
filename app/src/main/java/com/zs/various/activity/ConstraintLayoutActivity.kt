package com.zs.various.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zs.various.R
import kotlinx.android.synthetic.main.activity_constrain_layout.*

/**
 *
Created by zs
Date：2018年 08月 17日
Time：13:34
—————————————————————————————————————
About:ConstraintLayout
—————————————————————————————————————
 */
class ConstraintLayoutActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constrain_layout)

        banner?.setBackgroundResource(R.color.color_6)

    }

}