package com.zs.various.activity

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.zs.various.R
import com.zs.various.util.LogUtil
import kotlinx.android.synthetic.main.activity_dispatch_layout.*

/**
 * Created by zs
 * Date：2018年 10月 24日
 * Time：16:14
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
class DispatchEventActivity : AppCompatActivity() {

    val mHandler1 = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what){
                1 ->{
                    LogUtil.logShow("mHandler1_1")
                }
                2 ->{
                    LogUtil.logShow("mHandler1_2")
                }
            }

        }
    }

    val mHandler2 = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what){
                1 ->{
                    LogUtil.logShow("mHandler2_1")
                }
                2 ->{
                    LogUtil.logShow("mHandler2_2")
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dispatch_layout)

        bottom_view?.setOnClickListener {

            Log.d("My_Log"," ============ bottom_view =============")

        }

        top_view?.setOnClickListener {

            Log.d("My_Log"," ============ top_view =============")

        }

//        var test = Test()
//        Log.d("My_Log","a = " + test.taskA.a)
//        test.taskC.print(test.taskA)
//        test.taskC.print(TaskB())


        var msg1 = Message.obtain()
        msg1.what = 1
        mHandler1.sendMessage(msg1)

        var msg2 = Message.obtain()
        msg2.what = 2
        mHandler2.sendMessage(msg2)

    }

}
