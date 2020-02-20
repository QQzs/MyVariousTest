package com.zs.various.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.zs.various.R
import com.zs.various.util.RandomUntil
import kotlinx.android.synthetic.main.activity_sync.*

/**
 *
Created by zs
Date：2018年 09月 19日
Time：13:58
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class SyncActivity : AppCompatActivity() {

    private var num = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync)

        tv_start?.setOnClickListener {
            var task1 = MyThread("tag = $num")
//            task1.name = num.toString()
            num++
            task1.start()
        }
    }

    class MyThread(var tag: String) : Thread(){

        override fun run() {
            super.run()
            Sync.task(tag)
        }
    }

    class Sync{
        companion object {
            fun task(tag: String){
                synchronized(this){
                    try {
                        Thread.sleep(2000L + RandomUntil.getNum(10) * 200)
//                    Log.d("My_Log","name = " + Thread.currentThread().name)
                        Log.d("My_Log", tag)
                    } catch (e: Exception) {
                    }
                }
            }
        }

    }

}