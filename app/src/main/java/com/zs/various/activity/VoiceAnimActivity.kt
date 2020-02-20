package com.zs.various.activity

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.zs.various.R
import kotlinx.android.synthetic.main.activity_voice_anim.*
import org.jetbrains.anko.dip


class VoiceAnimActivity : AppCompatActivity() {

    var mParams: ViewGroup.LayoutParams? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_anim)

        tv_1.text = "我的身份说明啊"
        tv_2.text = "\u3000\u3000语音介绍\u3000\u3000"

        iv_anim.setImageResource(R.drawable.voice_anim_list)
        val animationDrawable = iv_anim.drawable as AnimationDrawable
        animationDrawable.start()

        ic_voice_start.setOnClickListener{
            ic_voice_start.visibility = View.GONE
            progress_voice.visibility = View.VISIBLE
            iv_voice_icon.setImageResource(R.mipmap.zhibo_yuyin_stop)
        }
        var progress: Int = 0
        progress_voice.setOnClickListener {
            progress += 2
            progress_voice.progress = progress

            mParams?.width = dip(50) + progress
            view_test.layoutParams = mParams
        }

        mParams = view_test.layoutParams


    }
}
