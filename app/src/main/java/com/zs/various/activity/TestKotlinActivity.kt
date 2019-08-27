package com.zs.various.activity

import com.zs.various.R
import com.zs.various.base.BaseActivity
import com.zs.various.util.Constant
import com.zs.various.util.extension.isNullEmpty
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

    lateinit var test: String

    override fun setLayoutId(): Int {
        return R.layout.activity_test_kotlin
    }

    override fun initView() {

        test = ""

        btn_test?.setOnClickListener {
//            myToast("hahahaha")
            test?.let {
                if (it.isNullEmpty()){
                    myToast("null")
                }
            }
        }

    }

    override fun initData() {

        iv_test?.loadImage(Constant.IMAGE_URL)

//        val params = LinearLayout.LayoutParams(1, 1, (Gravity.CENTER or Gravity.BOTTOM).toFloat())


    }

}
