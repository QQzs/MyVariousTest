package com.zs.various.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zs.various.R
import kotlinx.android.synthetic.main.activity_anim.*

class AnimActivity : AppCompatActivity() {

    var flag1 = true
    var flag2 = true
    var flag3 = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anim)

        widget_view.setOnClickListener {
            flag1 = !flag1
            widget_view.setAnim(flag1)
        }

        widget_view2.setOnClickListener {
            flag2 = !flag2
            widget_view2.setAnim(flag2)
        }

        widget_view3.setOnClickListener {
            flag3 = !flag3
            widget_view3.startAnimator(flag3)
        }
    }
}
