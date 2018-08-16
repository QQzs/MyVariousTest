package com.zs.various.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zs.various.R

/**
 *
Created by zs
Date：2018年 08月 16日
Time：9:40
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class DrawerLayoutActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_layout)

//        drawer_layout?.openDrawer(Gravity.LEFT)
    }

}