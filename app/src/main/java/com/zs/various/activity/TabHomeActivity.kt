package com.zs.various.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
    var fragment0: Fragment? = null
    var fragment1: Fragment? = null
    var fragment2: Fragment? = null
    var fragment3: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_home_layout)

        fragment0 = PageFragment.getInstance(0)
        fragment1 = PageFragment.getInstance(1)
        fragment2 = PageFragment.getInstance(2)
        fragment3 = PageFragment.getInstance(3)

        changeFragment(fragment0)

        item_tab_0?.setOnClickListener(this)
        item_tab_1?.setOnClickListener(this)
        item_tab_2?.setOnClickListener(this)
        item_tab_3?.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.item_tab_0 ->{
                changeFragment(fragment0)
            }
            R.id.item_tab_1 ->{
                changeFragment(fragment1)
            }
            R.id.item_tab_2 ->{
                changeFragment(fragment2)
            }
            R.id.item_tab_3 ->{
                changeFragment(fragment3)
            }

        }

    }


    /**
     * fragment切换
     * @param nextFragment
     */
    private fun changeFragment(nextFragment: androidx.fragment.app.Fragment?) {
        if (nextFragment != null) {
            mCurrentFragment?.let {
                var transaction = supportFragmentManager.beginTransaction()
                transaction.hide(it).commitAllowingStateLoss()
            }
            mCurrentFragment = nextFragment
            var transaction = supportFragmentManager.beginTransaction()
            if (nextFragment.isAdded) {
                transaction.show(nextFragment).commitAllowingStateLoss()
            } else {
                transaction.add(R.id.fl_fragment_container, nextFragment).commitAllowingStateLoss()
            }
            if(mCurrentFragment == fragment0){
                StatusBarUtil.setTranslucentForImageViewInFragment(this,0 , null)
            }else{
//                StatusBarUtil.setColorNoTranslucent(this , ContextCompat.getColor(this,R.color.colorPrimary))
            }
        }
    }

}