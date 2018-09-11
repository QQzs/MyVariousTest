package com.zs.various.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.flaviofaria.kenburnsview.KenBurnsView
import com.flaviofaria.kenburnsview.Transition
import com.zs.various.R
import kotlinx.android.synthetic.main.activity_custom_view.*

class CustomViewActivity : AppCompatActivity() {

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
            rl_border?.setStrokeColor(R.color.colorAccent)
            view_line?.setLineColorResource(R.color.colorPrimary)
        }

        tv_border.setOnClickListener{
            rl_border?.setContentColorResource(R.color.colorPrimary)
        }

        tv_rip?.setOnClickListener {
//            toast("rip")
        }

    }
}
