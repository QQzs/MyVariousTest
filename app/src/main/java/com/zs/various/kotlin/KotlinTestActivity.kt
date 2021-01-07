package com.zs.various.kotlin

import android.annotation.SuppressLint
import android.view.View
import com.zs.various.R
import com.zs.various.kotlin.data.UserData
import com.zs.various.base.BaseActivity
import com.zs.various.kotlin.extension.getBack
import com.zs.various.kotlin.extension.setColor
import com.zs.various.kotlin.view.TestView
import com.zs.various.util.LogUtil
import com.zs.various.kotlin.extension.loadImage
import kotlinx.android.synthetic.main.kotlin_test_layout.*
import kotlinx.android.synthetic.main.item_view_layout.view.*

/**
 * @Author: zs
 * @Date: 20/12/23 上午8:10
 *
 * @Description:
 */
const val num8 = 10

open class KotlinTestActivity : BaseActivity(), View.OnClickListener {

    /**
     * 变量：var
     */
    var num: Int = 10
    var num2 = 10
        set(value) {
            field = value + 5
        }
        get() {
            return field + 5
        }

    /**
     * 只读变量：val
     */
    val num3 = 10
        get() {
            return field + 5
        }

    /**
     * 伴生对象
     */
    companion object {
        /**
         * 静态变量
         */
        var num4 = 10
            @JvmStatic
            get() {
                return field + 10
            }

        /**
         * 常量：const val
         */
        const val num5 = 10

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

    /**
     *  静态类
     */
    object StaticData {
        /**
         * 静态变量（private）
         */
        var num6 = 10
        /**
         * 常量：const val
         */
        const val num7 = 10
    }

    var name: String? = null
    var name1: String = ""

    override fun setLayoutId(): Int {
        return R.layout.kotlin_test_layout
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {

    }

    @SuppressLint("SetTextI18n")
    override fun initData() {
        var user1 = UserData("name1")
        var user2 = UserData("name2", num)

        // 方法重载
        test("arg1", 2, "arg3")
        test("arg1", 2, "arg3", 4, "arg5")

        test(arg1 = "arg1", arg2 = 2, arg3 = "arg3")
        test(arg3 = "arg3", arg1 = "arg1", arg2 = 2)

        println("num = $num")
        println("num = ${user1.age}")

        tv_age?.text = "年龄 = $num2"
        item_view?.tv_item_content?.text = "aaaaaaaaaa"

        var test = getBack("test")
        tv_age?.setColor(R.color.color_4)
        tv_avatar?.loadImage(R.drawable.head_bg_img)


        tv_avatar?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

            }
        })

        // 如果 lambda 表达式是函数调用的最后一个实参，它可以放到括号的外边。
        tv_avatar?.setOnClickListener() {

        }

        // 当 lambda 是函数唯一实参时，还可以去掉代码中的空括号对
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
    fun test(arg1: String?, arg2: Int?, arg3: String?, arg4: Int? = 2, arg5: String = "test") {

    }

    fun add(num1: Int, num2: Int): Int {
        return num1 + num2
    }

    fun multiply(num1: Int, num2: Int): Int {
        return num1 * num2
    }

    /**
     * lambda 闭包
     */
    var add2 = { num1: Int, num2: Int ->
        num1 + num2
    }

    var multiply2 = { num1: Int, num2: Int ->
        num1 * num2
    }

    /**
     * 高阶函数：
     * 将一个函数作为另一个函数的参数或者返回值
     */
    fun test2(n1: Int, n2: Int, testFun: (Int, Int) -> Int): Int {
        return testFun(n1, n2)
    }

    fun getResult() {
        // 获取相加结果
        val addResult = test2(10, 5, this::add)
        // 获取相乘结果
        val multiplyResult = test2(10, 5, this::multiply)

        val addResult2 = test2(10, 5, add2)
        val multiplyResult2 = test2(10, 5, multiply2)

    }


}