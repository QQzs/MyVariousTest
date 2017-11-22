package com.zs.various.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.flaviofaria.kenburnsview.KenBurnsView
import com.flaviofaria.kenburnsview.Transition
import com.squareup.picasso.Picasso
import com.zs.various.R
import kotlinx.android.synthetic.main.activity_custom_view.*

class CustomViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view)

        var image = "http://www.bing.com/az/hprichbg/rb/Forest20171122_ZH-CN11904842708_1920x1080.jpg"
//        MyGlideImageLoader.displayImage(image, image_custom)
        Picasso.with(this).load(image)
                .placeholder(R.mipmap.timg)
                .into(image_custom)

        image_custom.setTransitionListener(object : KenBurnsView.TransitionListener {
            override fun onTransitionStart(transition: Transition) {

            }

            override fun onTransitionEnd(transition: Transition) {

            }
        })

        rl_border.setOnClickListener{
            rl_border?.setStrokeColor(R.color.colorAccent)
        }

        tv_border.setOnClickListener{
            rl_border?.setContentColorResource(R.color.hint_color)
        }

    }
}
