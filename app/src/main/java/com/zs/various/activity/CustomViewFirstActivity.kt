package com.zs.various.activity

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.flaviofaria.kenburnsview.KenBurnsView
import com.flaviofaria.kenburnsview.Transition
import com.zs.various.R
import com.zs.various.listener.OnClickEffectTouchListener
import com.zs.various.util.MoreUtil
import kotlinx.android.synthetic.main.activity_custom_view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class CustomViewFirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view)

        try {
            val data = assets.open("livenew28")
            val size = data.available()
            val buffer = ByteArray(size)
            data.read(buffer)
            data.close()
            Log.e("My_Log","content = " + String(buffer))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        switch_view?.switchColor(ContextCompat.getColor(this,R.color.blue_show))

        var image = "http://www.bing.com/az/hprichbg/rb/Forest20171122_ZH-CN11904842708_1920x1080.jpg"
//        MyGlideImageLoader.displayImage(image, image_custom)
//        Picasso.with(this).load(image)
//                .placeholder(R.mipmap.timg)
//                .into(image_custom)

        image_custom.setTransitionListener(object : KenBurnsView.TransitionListener {
            override fun onTransitionStart(transition: Transition) {

            }

            override fun onTransitionEnd(transition: Transition) {

            }
        })

        rl_border.setOnClickListener{
//            rl_border?.setStrokeColor(R.color.colorAccent)
//            view_line?.setLineColorResource(R.color.colorPrimary)
        }

        tv_border.setOnClickListener{
//            rl_border?.setContentColorResource(R.color.colorPrimary)
            toast("click")
        }

        tv_rip?.setOnClickListener {
//            toast("rip")
        }

        MoreUtil.autotextSize(12,18,tv_auto)

        tv_complete?.setOnTouchListener(object : OnClickEffectTouchListener(){
            override fun onTouchClick(view: View?) {
                startActivity<CustomViewTwoActivity>()
            }
        })

    }
}
