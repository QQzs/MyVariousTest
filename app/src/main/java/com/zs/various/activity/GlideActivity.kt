package com.zs.various.activity

import android.graphics.drawable.Drawable
import android.os.Looper
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.zs.various.R
import com.zs.various.base.BaseActivity
import com.zs.various.util.DensityUtil
import com.zs.various.util.LogUtil
import com.zs.various.view.largeimage.LargeImageView
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

        var imageUrl = "https://img.hongrenshuo.com.cn/h5/a840bde806813f8ae20725eb0276422c.png"

        val w = DensityUtil.dip2px(108f)
        val h = DensityUtil.dip2px(192f)
        LogUtil.logShow("w = $w  h = $h")

//        imageUrl = "https://img.hongrenshuo.com.cn/h5/a840bde806813f8ae20725eb0276422c.png?x-oss-process=image/resize,m_fill,w_420,h_525"

//        imageUrl = "https://img.kilamanbo.com/tab-1215-red_@3x.webp"
//        imageUrl = "https://img.kilamanbo.com/h5/8244d0ad72299f34b4d7f3d29e5bbfd5.gif"
//        imageUrl = "https://img.kilamanbo.com/262024461929116858017219354e43dd0a4153da9e86235c4d9b36e6c6PC.jpg"
//        imageUrl = "https://img.kilamanbo.com/262024461929116858017219354e43dd0a4153da9e86235c4d9b36e6c6PC.jpg?x-oss-process=image/resize,m_fill,w_1440,h_3063"

//        imageUrl = "https://img.hongrenshuo.com.cn/h5/a840bde806813f8ae20725eb0276422c.png?x-oss-process=image/resize,m_fill,w_420,h_525"


//        imageUrl = "https://img.kilamanbo.com/2717534601218.png?t=1687175210000"
        imageUrl = "https://img.kilamanbo.com/21188445593861705641213784606899.gif"
        imageUrl = "https://img.kilamanbo.com/tab-1215-red_@3x.webp"
        val image = findViewById<ImageView>(R.id.image_view_url)
//        image.scaleType = ImageView.ScaleType.CENTER_CROP
        Glide.with(this)
            .load(imageUrl)
            .into(image)


        val image3 = findViewById<LargeImageView>(R.id.image_view_url_large)

        Glide.with(this).downloadOnly().load(imageUrl)
            .listener(object : RequestListener<File> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<File>?,
                    isFirstResource: Boolean
                ): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onResourceReady(
                    resource: File?,
                    model: Any?,
                    target: Target<File>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    LogUtil.logShow("onResourceReady")
//                    image3.setImage(FileBitmapDecoderFactory(resource))
                    return true
                }
            }).into(object : CustomTarget<File?>() {
                override fun onLoadCleared(placeholder: Drawable?) {}
                override fun onResourceReady(resource: File, transition: Transition<in File?>?) {

                }
            })

    }

    override fun setLayoutId(): Int = R.layout.activity_glide


    override fun onDestroy() {
        super.onDestroy()
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                //只能在主线程执行
//                Glide.get(this).clearMemory()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}