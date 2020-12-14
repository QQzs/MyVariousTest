package com.zs.various.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import com.zs.various.R

/**
 * Created by zs
 * Date：2018年 10月 24日
 * Time：16:14
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
class DispatchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_dispatch_move_layout)
        setContentView(R.layout.activity_dispatch_layout)

//        view_back?.setOnClickListener {
//            Log.d("My_Log", " activity ====  setOnClickListener ")
//        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event?.action == MotionEvent.ACTION_DOWN) {
            Log.d("My_Log", " activity ====  onTouchEvent ACTION_DOWN ")
//            return true
        } else if (event?.action == MotionEvent.ACTION_MOVE) {
            Log.d("My_Log", " activity ====  onTouchEvent ACTION_MOVE ")
//            return true
        } else if (event?.action == MotionEvent.ACTION_UP) {
            Log.d("My_Log", " activity ====  onTouchEvent ACTION_UP ")
//            return true
        }
        return super.onTouchEvent(event)
    }


}
