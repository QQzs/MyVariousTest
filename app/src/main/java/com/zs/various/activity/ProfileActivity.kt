package com.zs.various.activity

import android.graphics.Color
import com.google.android.material.appbar.AppBarLayout
import androidx.fragment.app.Fragment
import com.jaeger.library.StatusBarUtil
import com.zs.various.R
import com.zs.various.adapter.FmPagerAdapter
import com.zs.various.base.BaseActivity
import com.zs.various.fragment.WorkFragment
import com.zs.various.view.pull.callback.OnReadyPullListener
import kotlinx.android.synthetic.main.activity_profile_layout.*
import org.jetbrains.anko.startActivity
import java.util.*

/**
 * @Author: zs
 * @Date: 2019-05-24 17:31
 *
 * @Description:
 */
class ProfileActivity: BaseActivity(){

    private var mPercent = 0f

    private var titles = arrayOf("热门", "最新")
    private var fragments = ArrayList<androidx.fragment.app.Fragment>()

    override fun setLayoutId(): Int {
        return R.layout.activity_profile_layout
    }

    override fun initView() {

        iv_user_avatar?.setOnClickListener {
            startActivity<NotificationActivity>()
        }
    }

    override fun initData() {

        StatusBarUtil.setTranslucentForImageView(this , 0 , null)

        for (i in titles.indices) {
            fragments.add(WorkFragment())
            tab_layout.addTab(tab_layout.newTab())
        }
        view_pager.adapter = FmPagerAdapter(fragments, supportFragmentManager)
        tab_layout.setupWithViewPager(view_pager)

        for (j in titles.indices) {
            tab_layout.getTabAt(j)?.text = titles[j]
        }

        appbar?.addOnOffsetChangedListener(object: AppBarLayout.OnOffsetChangedListener{

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {

                mPercent = Math.abs(verticalOffset * 1.0f) / appBarLayout.totalScrollRange
                toolbar.setBackgroundColor(changeAlpha(Color.WHITE, mPercent))
            }

        })

        pull_layout?.setReadyListener(object : OnReadyPullListener{
            override fun isReady(): Boolean {
                return mPercent == 0f
            }
        })
        pull_layout?.setHeader(iv_bg)

    }

    fun changeAlpha(color: Int, fraction: Float): Int {
        val alpha = (Color.alpha(color) * fraction).toInt()
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color))
    }

}