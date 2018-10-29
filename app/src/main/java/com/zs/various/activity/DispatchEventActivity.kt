package com.zs.various.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

import com.zs.various.R
import com.zs.various.bean.task.TaskB
import com.zs.various.bean.task.Test
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dispatch_layout)

        bottom_view?.setOnClickListener {

            Log.d("My_Log"," ============ bottom_view =============")

        }

        top_view?.setOnClickListener {

            Log.d("My_Log"," ============ top_view =============")

        }


        var test = Test()

        Log.d("My_Log","a = " + test.taskA.a)

        test.taskC.print(test.taskA)

        test.taskC.print(TaskB())
    }

}
