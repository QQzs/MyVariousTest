package com.zs.various.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zs.various.R
import com.zs.various.adapter.ActivityAdapter
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
        drawer_layout?.setScrimColor(Color.TRANSPARENT)

        btn_open?.setOnClickListener {
            drawer_layout?.openDrawer(drawer_container)
        }

        var acts = ArrayList<Class<*>>()
        acts.add(WebViewActivity::class.java)
        acts.add(CountDownActivity::class.java)
        acts.add(RelativeActivity::class.java)
        acts.add(VoiceAnimActivity::class.java)
        acts.add(CustomViewActivity::class.java)
        acts.add(DialogActivity::class.java)
        acts.add(SpanStringActivity::class.java)

        var adapter = ActivityAdapter(acts)
        RecyclerViewUtil.init(this,rl_create_time,adapter)

        KeyboardStatusDetector(this).setmVisibilityListener { keyboardVisible ->
            if(keyboardVisible) {
                //Do stuff for keyboard visible
                LogUtil.logShow("visible")
            }else {
                //Do stuff for keyboard hidden
                LogUtil.logShow("hidden")
            }
        }


    }

}