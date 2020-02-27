package com.zs.various.activity

import com.zs.various.R
import com.zs.various.base.BaseActivity
import com.zs.various.util.extension.loadImage
import com.zs.various.view.ExpandTextView
import kotlinx.android.synthetic.main.activity_custom_view2.*

/**
 * Created by zs
 * Date：2018年 12月 25日
 * Time：10:52
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
class CustomViewTwoActivity : BaseActivity() {

    private val mUrl = "https://freshmate-dev-bigbang-pub.oss-cn-beijing.aliyuncs.com/avatar/1000017/05ff9e913cd89f0b36c1ec184bd2a471.jpg"

    override fun setLayoutId(): Int {
        return R.layout.activity_custom_view2
    }

    override fun initView() {

        val expandTextView = findViewById<ExpandTextView>(R.id.tv_limit1)
        expandTextView.initText(getString(R.string.text_info))

    }


    override fun initData() {


//        round_img?.loadImageGlide(mUrl)

//        round_img?.loadImageShadow(mUrl , shadow_img)

        round_img?.loadImage(mUrl)

    }

}
