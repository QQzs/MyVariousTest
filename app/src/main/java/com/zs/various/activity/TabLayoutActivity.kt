package com.zs.various.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.zs.various.R
import com.zs.various.adapter.TabPageAdapter
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
        mPageAdapter = TabPageAdapter(supportFragmentManager,titles)
        view_pager?.adapter = mPageAdapter
        view_pager?.offscreenPageLimit = 4
        view_pager?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        tab_layout?.setupWithViewPager(view_pager)
//        tab_layout?.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(view_pager))
        tab_layout?.addOnTabSelectedListener(TabListener(view_pager))

    }

    inner class TabListener(viewPager: ViewPager?) : TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

        override fun onTabSelected(tab: TabLayout.Tab?) {
            super.onTabSelected(tab)
//            if (tab?.position == 0){
//                StatusBarUtil.setTranslucentForImageViewInFragment(this@TabLayoutActivity,0 , null)
//            }else{
//                StatusBarUtil.setColorNoTranslucent(this@TabLayoutActivity , ContextCompat.getColor(this@TabLayoutActivity,R.color.colorPrimary))
//            }
        }

    }

}