package com.zs.various.util.extension

import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.zs.various.R

/**
 *
Created by zs
Date：2019年 03月 28日
Time：11:25
—————————————————————————————————————
About:
—————————————————————————————————————
 */
fun ImageView.loadImage(url: String?) {
    if (this.context != null){
        Picasso.with(this.context)
                .load(url)
                .error(R.mipmap.timg)
                .into(this)
    }
}
