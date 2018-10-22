package com.zs.various.fragment

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.zs.various.R

/**
 * Created by zs
 * Date：2018年 10月 22日
 * Time：11:20
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
class PageFragmentTest : LazyFragment() {


    companion object {

        fun getInstance(num: Int): PageFragmentTest{
            val fragment = PageFragmentTest()
            val args = Bundle()
            args.putInt("num", num)
            fragment.arguments = args
            return fragment
        }

    }

    override fun initView() {

        var num = arguments?.getInt("num")
        Log.d("My_Log","initView  $num")

        /**
         * 必须要重新获取view 直接用Kotlin  anko库找控件不可以
         */
        var tv_pager_num = getView<TextView>(R.id.tv_pager_num)
        tv_pager_num.text = "page = $num"

    }

    override fun setContentView(): Int {

        return R.layout.fragment_pager_item
    }

}
