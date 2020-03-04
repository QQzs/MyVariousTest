package com.zs.various.activity

import com.zs.various.R
import com.zs.various.base.BaseActivity
import com.zs.various.util.extension.loadImageGlide
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

    private val mUrl = "https://freshmate-dev-bigbang-pub.oss-cn-beijing.aliyuncs.com/avatar/1000019/e8f2befd7187dc93bf86d7279c7e36fb.gif"
    var mUrl2 = "https://freshmate-dev-bigbang-pub.oss-cn-beijing.aliyuncs.com/groupchat/2020-02/1000548/5e5538fc076d1211e55a82d7acf274ca.gif"

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

        round_img?.loadImageGlide(mUrl2)

    }

}
