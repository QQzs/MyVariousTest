package com.zs.various.activity.kotlin

import android.annotation.SuppressLint
import com.zs.various.R
import com.zs.various.base.BaseActivity
import kotlinx.android.synthetic.main.kotlin_test_layout.*

/**
 * @Author: zs
 * @Date: 20/12/23 上午8:10
 *
 * @Description:
 */
open class KotlinTestActivity : BaseActivity() {

    /**
     * 变量：var
     */
    var age: Int = 10
    var age2 = 10
        set(value) {
            field = value + 5
        }
        get() {
            return field + 5
        }

    /**
     * 只读变量：val
     */
    val age3 = 10
        get() {
            return field + 5
        }

    companion object {
        /**
         * 静态变量
         */
        val age4 = 10
            @JvmStatic
            get() {
                return field + 10
            }

        /**
         * 常量：const val
         */
        const val age5 = 10

        /**
         * 静态变量
         */
        val age6 = 10
    }

    var name: String? = null

    val name2 : String? = null

    override fun setLayoutId(): Int {
        return R.layout.kotlin_test_layout
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {

        tv_age?.text = "年龄 = $age2"
        age2 = 20
        tv_age?.text = "年龄 = $age2"
    }

    override fun initData() {

    }



}