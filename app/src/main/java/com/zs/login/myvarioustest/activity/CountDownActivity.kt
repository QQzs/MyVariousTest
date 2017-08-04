package com.zs.login.myvarioustest.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zs.login.myvarioustest.R
import kotlinx.android.synthetic.main.activity_count_down.*

class CountDownActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down)

        // 单位 毫秒
        count_down_view1.start(2 * 60 * 60 * 1000)
        count_down_view2.start(30 * 60 * 1000)

        count_down_view3.init(System.currentTimeMillis() + 10 * 60 * 1000)
    }
}
