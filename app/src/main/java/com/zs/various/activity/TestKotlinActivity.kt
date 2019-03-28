package com.zs.various.activity

import com.zs.various.R
import com.zs.various.base.BaseActivity
import com.zs.various.util.Constant
import com.zs.various.util.extension.loadImage
import com.zs.various.util.extension.myToast
import kotlinx.android.synthetic.main.activity_test_kotlin.*

/**
 *
Created by zs
Date：2019年 03月 28日
Time：10:28
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class TestKotlinActivity : BaseActivity(){

    override fun setLayoutId(): Int {
        return R.layout.activity_test_kotlin
    }

    override fun initView() {

        btn_test?.setOnClickListener {
            myToast("hahahaha")
        }

    }

    override fun initData() {

        iv_test?.loadImage(Constant.IMAGE_URL)

    }

}
