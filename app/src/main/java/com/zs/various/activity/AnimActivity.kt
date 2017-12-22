package com.zs.various.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zs.various.R
import kotlinx.android.synthetic.main.activity_anim.*
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class AnimActivity : AppCompatActivity() {

    var flag1 = true
    var flag2 = true
    var flag3 = true
    var currentValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anim)

        setMediaAnim()
        setRoundProgress()
    }

    fun setMediaAnim(){
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

    fun setRoundProgress(){
        barStroke.max = 100
        barStrokeText.max = 100
        barFill.max = 100

        val pool = Executors.newScheduledThreadPool(1)
        var updateTimerTask = object : TimerTask() {
            override fun run() {
                currentValue += 1
                barStroke.value = currentValue
                barStrokeText.value = currentValue
                barFill.value = currentValue
                if (currentValue >= 100) {
                    currentValue = 0
                }
            }
        }
        pool.scheduleAtFixedRate(updateTimerTask, 0, 100, TimeUnit.MILLISECONDS)
    }

}
