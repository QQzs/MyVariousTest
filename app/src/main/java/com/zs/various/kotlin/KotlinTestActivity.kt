package com.zs.various.kotlin

import android.annotation.SuppressLint
import android.view.View
import com.zs.various.R
import com.zs.various.kotlin.data.UserData
import com.zs.various.base.BaseActivity
import com.zs.various.kotlin.extension.loadImage
import com.zs.various.kotlin.extension.setColor
import com.zs.various.kotlin.view.TestView
import com.zs.various.util.LogUtil
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

    object StaticData {
        /**
         * 静态变量
         */
        var age6 = 10
    }

    var name: String? = null

    val name2: String? = null

    override fun setLayoutId(): Int {
        return R.layout.kotlin_test_layout
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {

    }

    @SuppressLint("SetTextI18n")
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

        tv_age?.text = "年龄 = $age2"
        tv_age?.setColor(R.color.color_4)
        tv_avatar?.loadImage(R.drawable.head_bg_img)


        /**
         * 设置监听事件
         */
        tv_avatar?.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

            }
        })

        tv_avatar?.setOnClickListener({

        })

        tv_avatar?.setOnClickListener {

        }

        var view = TestView(this)

        view.callBack = { s: String, i: Int ->
            LogUtil.logShow(s + i)
        }
        view.callBack2 = {
            LogUtil.logShow(it)
        }

        view.backData()
        view.backData2()

    }

    /**
     * @param arg1 必传
     * @param arg2 必传
     * @param arg3 必传
     * @param arg4 可不传，不传时默认值 2
     * @param arg5 可不传，不传时默认值 test
     */
    fun test(arg1: String?, arg2: Int?, arg3: String?, arg4: Int? = 2, arg5: String? = "test") {

    }

    /**
     * 高阶函数：
     * 将一个函数作为另一个函数的参数或者返回值
     */
    fun test2(n1: Int, n2: Int, testFun: (Int, Int) -> Int): Int {
        return testFun(n1, n2)
    }

    fun add(num1: Int, num2: Int): Int {
        return num1 + num2
    }

    fun multiply(num1: Int, num2: Int): Int {
        return num1 * num2
    }

    fun fun2() {
        //获取相加结果
        val addResult = test2(10, 5, this::add)
        //获取相乘结果
        val multiplyResult = test2(10, 5, this::multiply)
    }

    /**
     * lambda 闭包
     */
    var add2 = {num1: Int, num2: Int ->
        num1 + num2
    }

    var multiply2 = {num1: Int, num2: Int ->
        num1 * num2
    }

}