package com.zs.various.activity

import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.zs.various.R
import kotlinx.android.synthetic.main.activity_count_down.*
import org.jetbrains.anko.toast

class CountDownActivity : AppCompatActivity() {

    /**
     * handler
     */
    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                1 -> {
                    ++test1
                    Log.d("My_Log"," msg  =  test1  ");
                    count_down_view1.start(60 * 1000)
                }
                2 -> {
                    ++test2
                    Log.d("My_Log"," msg  =  test2  ");
                    count_down_view2.start(60 * 1000)
                }
            }
            super.handleMessage(msg)
        }
    }

    var test1: Int = 0
    var test2: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down)

        // 单位 毫秒
//        count_down_view1.start(2 * 60 * 60 * 1000)
//        count_down_view2.start(124390614)

        count_down_view2.setOnCountdownEndListener {
            toast("fffff")
        }

        count_down_view3.init(System.currentTimeMillis() + 10 * 60 * 1000)

        tv_test1.text = "1"
        tv_test2.text = "1"

        var msg1 = mHandler.obtainMessage()
        msg1.what = 1
        mHandler.sendMessage(msg1)

        var msg2 = mHandler.obtainMessage()
        msg2.what = 2
        mHandler.sendMessage(msg2)

    }
}
