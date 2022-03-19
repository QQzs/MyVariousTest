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
import kotlinx.android.synthetic.main.activity_glide.*
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

        var local = "/storage/emulated/0/DCIM/Camera/dc86a559eef62847be1ab2673348bebf.jpeg"
        Glide.with(this).load(local).into(image_view)

        var imageUrl = "https://img.hongrenshuo.com.cn/h5/a840bde806813f8ae20725eb0276422c.png"

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