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
            NotificationUtil.Builder(this)
                    .setNotificationId(666)
                    .setChannel("iddd" , "我的")
                    .setTitle("标题")
                    .setContent("据台湾“中央社”报道，国民党主席朱立伦今天(18日)向中常会报告，为败选请辞党主席一职，他感谢各位中常委的指教包容，也宣布未来党务工作由副主席黄敏惠暂代，完成未来所有补选工作。")
                    .setType(NotificationUtil.TYPE_MORE)
                    .setNextPage(ProfileActivity::class.java , HashMap<String , String>())
                    .build()
                    .notification()
        }




        btn_2?.setOnClickListener {

            var list = arrayListOf("1、是据台湾" , "2、今天18" , "3、主的席")
            NotificationUtil.Builder(this)
                    .setNotificationId(123)
                    .setChannel("iddd" , "我的")
                    .setTitle("标题")
                    .setContent("完成未来所有补选工作。")
                    .setContentList(list)
                    .setType(NotificationUtil.TYPE_INBOX)
                    .setNextPage(ProfileActivity::class.java , HashMap<String , String>())
                    .build()
                    .notification()

        }

    }

}