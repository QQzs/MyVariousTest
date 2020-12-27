package com.zs.various.activity

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.zs.various.R
import com.zs.various.adapter.TabPageAdapter
import com.zs.various.fragment.PageFragment
import com.zs.various.fragment.PageFragmentTest
import com.zs.various.fragment.TabFragment
import com.zs.various.listener.TabFragmentHelper
import com.zs.various.util.LogUtil
import kotlinx.android.synthetic.main.activity_tab_layout.*

/**
 *
Created by zs
Date：2018年 10月 22日
Time：10:19
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class TabLayoutActivity: AppCompatActivity(){

    var mPageAdapter: TabPageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_layout)

        var titles = arrayListOf("体育", "科技", "新闻", "阅读")
        mPageAdapter = TabPageAdapter(supportFragmentManager,titles, TabFragmentHelper { index ->
            LogUtil.logShow("TabFragmentHelper index = $index")
            PageFragment.getInstance(index)
        })
        view_pager?.adapter = mPageAdapter
        view_pager?.offscreenPageLimit = 1
        view_pager?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        tab_layout?.setupWithViewPager(view_pager)
//        tab_layout?.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(view_pager))
        // 自定义TabSelectedListener
        tab_layout?.addOnTabSelectedListener(TabListener(view_pager))

    }

    inner class TabListener(viewPager: ViewPager?) : TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

        override fun onTabSelected(tab: TabLayout.Tab) {
            super.onTabSelected(tab)
        }

    }

}