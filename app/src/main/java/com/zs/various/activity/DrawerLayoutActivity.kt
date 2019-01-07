package com.zs.various.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.zs.various.R
import com.zs.various.util.KeyboardStatusDetector
import com.zs.various.util.LogUtil
import com.zs.various.util.RecyclerViewUtil
import kotlinx.android.synthetic.main.drawer_layout.*

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
//        drawer_layout?.openDrawer(Gravity.RIGHT)
        drawer_layout?.setScrimColor(Color.TRANSPARENT)
        RecyclerViewUtil.initScroll(this,recycler_view,null)

        btn_open?.setOnClickListener {
            drawer_layout?.openDrawer(drawer_content)
        }

        view_bottom?.setOnClickListener {
            drawer_layout?.closeDrawers()
        }

        KeyboardStatusDetector(this).setmVisibilityListener { keyboardVisible ->
            if(keyboardVisible) {
                //Do stuff for keyboard visible
                LogUtil.logShow("visible")
                view_bottom?.visibility = View.GONE
            }else {
                //Do stuff for keyboard hidden
                LogUtil.logShow("hidden")
                view_bottom?.visibility = View.VISIBLE
            }
        }


    }

}