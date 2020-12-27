package com.zs.various.kotlin

import android.annotation.SuppressLint
import android.view.View
import com.zs.various.R
import com.zs.various.kotlin.data.UserData
import com.zs.various.base.BaseActivity
import com.zs.various.kotlin.data.NewData
import com.zs.various.kotlin.data.OneData
import com.zs.various.kotlin.extension.loadImage
import com.zs.various.kotlin.extension.setColor
import kotlinx.android.synthetic.main.kotlin_test_layout.*

/**
 * @Author: zs
 * @Date: 20/12/23 上午8:10
 *
 * @Description:
 */
const val age7 = 10

open class KotlinTestActivity : BaseActivity(), View.OnClickListener {

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
        var age4 = 10
            @JvmStatic
            get() {
                return field + 10
            }

        /**
         * 常量：const val
         */
        const val age5 = 10

        /**
         * 静态方法
         */
        fun getData(): Int {
            return 1
        }

        /**
         * 静态方法
         */
        @JvmStatic
        fun getNewData(): Int {
            return 2
        }

    }

    object StaticData{

        /**
         * 静态变量
         */
        var age6 = 10

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
        var user1 = UserData("name1")
        var user2 = UserData("name2", age)

        // 方法重载
        test("arg1", 2, "arg3")
        test("arg1", 2, "arg3", 4, "arg5")

        test(arg1 = "arg1", arg2 = 2, arg3 = "arg3")
        test(arg3 = "arg3", arg1 = "arg1", arg2 = 2)


        println("age = $age")
        println("age = ${user1.age}")


        tv_age?.setColor(R.color.color_4)
        tv_avatar?.loadImage(R.drawable.head_bg_img)
    }

    /**
     * @param arg1 必传
     * @param arg2 必传
     * @param arg3 必传
     * @param arg4 可不传，不传时默认值 2
     * @param arg5 可不传，不传时默认值 test
     */
    fun test(arg1: String?, arg2: Int?, arg3: String?, arg4: Int? = 2, arg5: String? = "test") {
//        var data = OneData()
    }

    override fun onClick(v: View?) {
        super.onClick(v)


    }

}