package com.zs.various.util.extension

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.squareup.picasso.Picasso
import com.zs.various.R
import com.zs.various.view.ShadowView
import com.zs.various.view.palette.GlidePalette

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

fun ImageView.loadImageGlide(url: String?) {
    if (this.context != null){
        Glide.with(this.context)
                .load(url)
                .error(R.mipmap.timg)
                .into(this)
    }
}

/**
 * 加载图片 + 阴影
 */
fun ImageView.loadImageShadow(url: String? , shadowLayout: ShadowView?){

    Glide.with(this)
            .load(url)
            .placeholder(R.mipmap.timg)
            .error(R.mipmap.timg)
            .listener(GlidePalette.with(url)
                    .intoCallBack { palette ->
                        palette?.let {
                            shadowLayout?.setShadowColor(it.getDominantColor(Color.GRAY))
                        }
                    }
                    .setGlideListener(object: RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            shadowLayout?.setShadowColor(Color.GRAY)
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            shadowLayout?.setShadowColor(Color.GRAY)
                            return false
                        }

                    })
            )
            .into(this)
}