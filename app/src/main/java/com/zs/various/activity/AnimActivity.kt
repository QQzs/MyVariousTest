package com.zs.various.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zs.various.R
import com.zs.various.util.LogUtil
import kotlinx.android.synthetic.main.activity_anim.*
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class AnimActivity : AppCompatActivity() {

    var flag2 = true
    var flag3 = true
    var flag4 = true
    var play = true
    var currentValue = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anim)
        initListener()

        setMediaAnim()
        setRoundProgress()
    }

    private fun initListener() {
        seek_view?.seekProgressCallBack = {
            LogUtil.logShow("progress = $it")
        }
    }

    fun setMediaAnim(){
        widget_view2.setOnClickListener {
            flag2 = !flag2
            widget_view2.setAnim(flag2)
        }
        widget_view3.setOnClickListener {
            flag3 = !flag3
            widget_view3.startAnimator(flag3)
        }
        widget_view4?.setOnClickListener{
            widget_view4.startAnimator(flag4)
            flag4 = !flag4

        }
    }

    fun setRoundProgress(){
        barStroke.max = 100
        barStrokeText.max = 100
        barFill.max = 100
        barStrokeTest.max = 100

        var pool = Executors.newScheduledThreadPool(1)
        var updateTimerTask = object : TimerTask() {
            override fun run() {
                currentValue += 0.1f
                barStroke.value = currentValue
                barStrokeText.value = currentValue
                barFill.value = currentValue
                barStrokeTest.value = currentValue
                if (currentValue >= 100f) {
                    currentValue = 0f
                }
            }
        }
        pool.scheduleAtFixedRate(updateTimerTask, 0, 100, TimeUnit.MILLISECONDS)

        barStrokeTest.setOnClickListener{
            barStrokeTest.setImage(!play)
            play = !play
        }
    }

}
