package com.zs.various.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zs.various.R
import com.zs.various.util.NotificationUtil
import kotlinx.android.synthetic.main.activity_notification_layout.*

/**
 *
Created by zs
Date：2018年 11月 06日
Time：11:24
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class NotificationActivity: AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_layout)

        btn_1?.setOnClickListener {
            NotificationUtil.showNotification(this)
        }
        btn_2?.setOnClickListener {
            NotificationUtil.showNotification2(this)
        }

    }

}