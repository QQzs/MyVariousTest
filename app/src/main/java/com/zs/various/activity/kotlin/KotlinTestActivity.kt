package com.zs.various.activity.kotlin

import com.zs.various.R
import com.zs.various.base.BaseActivity

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

    /**
     * 只读变量：val
     */
    val age3 = 10

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
        set(value) {
            field = value + "a"
        }
        get() {
            return field + "b"
        }

    val name2 : String? = null
        get() {
            return field + "c"
        }


    override fun setLayoutId(): Int {
        return R.layout.kotlin_test_layout
    }

    override fun initView() {

    }

    override fun initData() {

    }



}