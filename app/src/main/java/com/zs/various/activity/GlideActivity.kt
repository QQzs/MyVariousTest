package com.zs.various.activity

import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.zs.various.R
import com.zs.various.base.BaseActivity
import com.zs.various.util.LogUtil
import java.io.File

/**
 * @Author: zs
 * @Date: 2020/10/18 9:57 AM
 *
 * @Description:
 */
class GlideActivity : BaseActivity(){

    override fun initView() {

    }

    override fun initData() {

        var imageUrl = "https://img.hongrenshuo.com.cn/274411095247015978507022630.gif"

        Glide.with(this).downloadOnly().load(imageUrl).listener(object: RequestListener<File>{
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<File>?, isFirstResource: Boolean): Boolean {
                return true
            }

            override fun onResourceReady(resource: File?, model: Any?, target: Target<File>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                LogUtil.logShow("submit thread = " + Thread.currentThread().name)
                return true
            }

        }).submit()

        Glide.with(this).downloadOnly().load(imageUrl).listener(object: RequestListener<File>{
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<File>?, isFirstResource: Boolean): Boolean {
                return true
            }

            override fun onResourceReady(resource: File?, model: Any?, target: Target<File>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                LogUtil.logShow("into thread = " + Thread.currentThread().name)
                return true
            }

        }).into(object: SimpleTarget<File>(){
            override fun onResourceReady(resource: File, transition: Transition<in File>?) {
            }
        })

    }

    override fun setLayoutId(): Int = R.layout.activity_glide


}