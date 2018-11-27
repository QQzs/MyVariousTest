package com.zs.various.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jaeger.library.StatusBarUtil
import com.zs.various.R
import com.zs.various.fragment.PageFragment
import kotlinx.android.synthetic.main.activity_tab_home_layout.*

/**
 *
Created by zs
Date：2018年 11月 26日
Time：16:28
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class TabHomeActivity : AppCompatActivity() , View.OnClickListener{

    var mCurrentFragment: Fragment? = null
    var fragment0: PageFragment? = null
    var fragment1: PageFragment? = null
    var fragment2: PageFragment? = null
    var fragment3: PageFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_home_layout)

        fragment0 = PageFragment.getInstance(0)
        fragment1 = PageFragment.getInstance(1)
        fragment2 = PageFragment.getInstance(2)
        fragment3 = PageFragment.getInstance(3)

        changeFragment(fragment0)
        StatusBarUtil.setColorNoTranslucent(this , ContextCompat.getColor(this,R.color.colorPrimary))
//        StatusBarUtil.setTranslucentForImageViewInFragment(this,0 , null)

        item_tab_0?.setOnClickListener(this)
        item_tab_1?.setOnClickListener(this)
        item_tab_2?.setOnClickListener(this)
        item_tab_3?.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.item_tab_0 ->{
                changeFragment(fragment0)
                StatusBarUtil.setColorNoTranslucent(this , ContextCompat.getColor(this,R.color.colorPrimary))
//                StatusBarUtil.setTranslucentForImageViewInFragment(this,0 , null)
            }
            R.id.item_tab_1 ->{
                changeFragment(fragment1)
                StatusBarUtil.setColorNoTranslucent(this , ContextCompat.getColor(this,R.color.colorPrimary))
            }
            R.id.item_tab_2 ->{
                changeFragment(fragment2)
                StatusBarUtil.setColorNoTranslucent(this , ContextCompat.getColor(this,R.color.colorPrimary))
            }
            R.id.item_tab_3 ->{
                changeFragment(fragment3)
                StatusBarUtil.setTranslucentForImageViewInFragment(this,0 , null)
//                StatusBarUtil.setColorNoTranslucent(this , ContextCompat.getColor(this,R.color.colorPrimary))
            }

        }

    }


    /**
     * fragment切换
     * @param nextFragment
     */
    private fun changeFragment(nextFragment: Fragment?) {
        if (nextFragment != null) {
            if (mCurrentFragment != null) {
                var transaction = supportFragmentManager.beginTransaction()
                transaction.hide(mCurrentFragment).commitAllowingStateLoss()
            }
            mCurrentFragment = nextFragment
            var transaction = supportFragmentManager.beginTransaction()
            if (nextFragment.isAdded) {
                transaction.show(nextFragment).commitAllowingStateLoss()
            } else {
                transaction.add(R.id.fl_fragment_container, nextFragment).commitAllowingStateLoss()
            }
        }
    }

}