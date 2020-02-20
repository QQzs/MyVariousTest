package com.zs.various.activity

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.githang.statusbar.StatusBarCompat
import com.google.android.material.appbar.AppBarLayout
import com.zs.various.R
import com.zs.various.adapter.ActivityAdapter
import com.zs.various.util.RecyclerViewUtil
import com.zs.various.view.cxrecyclerview.CXRecyclerView
import kotlinx.android.synthetic.main.activity_coordinator_layout.*
import kotlinx.android.synthetic.main.toolbar_head1.*
import kotlinx.android.synthetic.main.toolbar_head2.*

/**
 *
Created by zs
Date：2018年 08月 27日
Time：15:20
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class CoordinatorLayoutActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator_layout)
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this,R.color.colorPrimary), true)

        app_bar_layout?.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset == 0){
                switchToolbar(1,255)
                myRecyclerView?.setPullRefreshEnabled(true)
            }else if (Math.abs(verticalOffset) >= appBarLayout.totalScrollRange){
                switchToolbar(2,255)
                myRecyclerView?.setPullRefreshEnabled(false)
            }else{
                myRecyclerView?.setPullRefreshEnabled(false)
                val alpha = 255 - Math.abs(verticalOffset)
                if (alpha < 0){
                    switchToolbar(2,Math.abs(verticalOffset))
                }else{
                    switchToolbar(1,alpha)
                }
            }
        })

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
        RecyclerViewUtil.init(this,myRecyclerView,adapter)

        myRecyclerView?.setLoadingListener(object : CXRecyclerView.LoadingListener{
            override fun onRefresh() {
                //下拉刷新完成
                myRecyclerView?.refreshComplete()

            }

            override fun onLoadMore() {
                //加载更多完成
                Handler().postDelayed(Runnable {
                    myRecyclerView?.loadMoreComplete()
                },1000)
            }

        })

    }

    /**
     * 切换Toolbar
     */
    private fun switchToolbar(flag: Int , alpha: Int){
        if (flag == 1){
            toolbar1?.visibility = View.VISIBLE
            toolbar2?.visibility = View.GONE
            setToolbar1Alpha(alpha)
        }else{
            toolbar1?.visibility = View.GONE
            toolbar2?.visibility = View.VISIBLE
            setToolbar2Alpha(alpha)
        }

    }

    //设置展开时各控件的透明度
    fun setToolbar1Alpha(alpha: Int) {
        img_zhangdan.drawable.alpha = alpha
        img_zhangdan_txt.setTextColor(Color.argb(alpha, 255, 255, 255))
        tongxunlu.drawable.alpha = alpha
        jiahao.drawable.alpha = alpha
    }

    //设置闭合时各控件的透明度
    fun setToolbar2Alpha(alpha: Int) {
        img_shaomiao.drawable.alpha = alpha
        img_fukuang.drawable.alpha = alpha
        img_search.drawable.alpha = alpha
        img_zhaoxiang.drawable.alpha = alpha
    }


}