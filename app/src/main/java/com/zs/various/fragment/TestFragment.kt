package com.zs.various.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zs.various.R
import kotlinx.android.synthetic.main.activity_dialog.*
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 *
Created by zs
Date：2017年 12月 27日
Time：11:39
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class TestFragment : androidx.fragment.app.Fragment(){
    var currentValue = 0
    var pool : ScheduledExecutorService?= null

    private var mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                1001 -> {
                    btn_1?.text = "" + currentValue
                }
            }
            super.handleMessage(msg)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.activity_dialog,null,false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pool = Executors.newScheduledThreadPool(1)
        var updateTimerTask = object : TimerTask() {
            override fun run() {
                currentValue += 1
                if (currentValue >= 100) {
                    currentValue = 0
                }
                mHandler.sendEmptyMessage(1001)
            }
        }
        pool?.scheduleAtFixedRate(updateTimerTask, 0, 100, TimeUnit.MILLISECONDS)

    }

    override fun onStop() {
        super.onStop()
        pool?.shutdown()
        Log.d("My_Log","onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("My_Log","onStop")
    }



}
