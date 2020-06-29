package com.zs.various.activity

import android.graphics.Bitmap
import android.graphics.Color
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.appbar.AppBarLayout
import com.jaeger.library.StatusBarUtil
import com.zs.various.R
import com.zs.various.adapter.FmPagerAdapter
import com.zs.various.base.BaseActivity
import com.zs.various.fragment.WorkFragment
import com.zs.various.util.LogUtil
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

    private var titles = arrayOf("热门", "最新" , "热门", "最新" , "热门", "最新")
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

        Glide.with(this)
                .asBitmap()
                .load("https://img.hongrenshuo.com.cn/22822672998651592400566623.png?x-oss-process=image/resize,m_mfit,h_417,w_300/format,webp")
                .listener(object : RequestListener<Bitmap?>{
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap?>?, isFirstResource: Boolean): Boolean {
                        LogUtil.logShow("listener onLoadFailed")
                        return true
                    }

                    override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap?>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        LogUtil.logShow("listener onResourceReady")
                        // 返回true表示已经处理了结果，下面into方法里面不会进去了，返回false 会进去
                        return true
                    }
                })
                .into(object : SimpleTarget<Bitmap?>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                        LogUtil.logShow("target onResourceReady")
                    }
                })

    }

    fun changeAlpha(color: Int, fraction: Float): Int {
        val alpha = (Color.alpha(color) * fraction).toInt()
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color))
    }

}