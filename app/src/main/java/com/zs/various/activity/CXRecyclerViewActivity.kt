package com.zs.various.activity

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.zs.various.R
import com.zs.various.adapter.ActivityAdapter
import com.zs.various.util.RecyclerViewUtil
import com.zs.various.view.cxrecyclerview.CXRecyclerView
import kotlinx.android.synthetic.main.activity_cx_layout.*
import java.util.*

/**
 *
Created by zs
Date：2018年 07月 25日
Time：16:44
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class CXRecyclerViewActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cx_layout)

        var acts = ArrayList<Class<*>>()
        acts.add(WebViewActivity::class.java)
        acts.add(CountDownActivity::class.java)
        acts.add(RelativeActivity::class.java)
        acts.add(VoiceAnimActivity::class.java)
        acts.add(CustomViewFirstActivity::class.java)
        acts.add(DialogWithPopupActivity::class.java)
        acts.add(SpanStringActivity::class.java)
        acts.add(AnimActivity::class.java)
        acts.add(ReflexActivity::class.java)
        acts.add(WebViewActivity::class.java)
        acts.add(CountDownActivity::class.java)
        acts.add(RelativeActivity::class.java)
        acts.add(VoiceAnimActivity::class.java)
        acts.add(CustomViewFirstActivity::class.java)
        acts.add(DialogWithPopupActivity::class.java)
        acts.add(SpanStringActivity::class.java)
        acts.add(AnimActivity::class.java)
        acts.add(ReflexActivity::class.java)
        acts.add(WebViewActivity::class.java)
        acts.add(CountDownActivity::class.java)
        acts.add(RelativeActivity::class.java)
        acts.add(VoiceAnimActivity::class.java)
        acts.add(CustomViewFirstActivity::class.java)
        acts.add(DialogWithPopupActivity::class.java)
        acts.add(SpanStringActivity::class.java)
        acts.add(AnimActivity::class.java)
        acts.add(ReflexActivity::class.java)

        var adapter = ActivityAdapter(acts)
        RecyclerViewUtil.init(this,cx_recycler_view,adapter)
        // 默认有下拉刷新和上拉加载
//        cx_recycler_view?.setPullRefreshEnabled(true)
//        cx_recycler_view?.setLoadingMoreEnabled(true)
        cx_recycler_view?.setLoadingListener(object : CXRecyclerView.LoadingListener{
            override fun onRefresh() {
                //下拉刷新完成
                cx_recycler_view?.refreshComplete()

            }

            override fun onLoadMore() {
                //加载更多完成
                Handler().postDelayed(Runnable {
                    cx_recycler_view?.loadMoreComplete()
                },1000)
            }

        })

        var header = View.inflate(this,R.layout.dialog_layout,null)
        cx_recycler_view?.addHeaderView(header)
    }

}