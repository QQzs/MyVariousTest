package com.zs.various.fragment

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.zs.various.R
import kotlinx.android.synthetic.main.fragment_pager_item.view.*

/**
 * Created by zs
 * Date：2018年 10月 22日
 * Time：11:20
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
class PageFragmentTest : LazyFragment() {

    var num: Int? = null
    var tv_pager_num: TextView? = null

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

        num = arguments?.getInt("num")
        Log.d("My_Log","initView  $num")

        /**
         * 必须要重新获取view 直接用Kotlin  anko库找控件不可以
         */
//        tv_pager_num = getView<TextView>(R.id.tv_pager_num)

        mRootView?.tv_pager_num?.text = "page = $num"


    }

    override fun setContentView(): Int {
        return R.layout.fragment_pager_item
    }

}
