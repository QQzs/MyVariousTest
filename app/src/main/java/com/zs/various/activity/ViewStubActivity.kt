package com.zs.various.activity

import android.view.View
import com.zs.various.R
import com.zs.various.base.BaseActivity
import kotlinx.android.synthetic.main.error_layout.view.*
import kotlinx.android.synthetic.main.view_stub_layout.*



/**
 *
Created by zs
Date：2019年 03月 05日
Time：11:19
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class ViewStubActivity : BaseActivity(){

    var errorView: View? = null

    override fun setLayoutId(): Int {
        return R.layout.view_stub_layout
    }

    override fun initView() {

        btn_vs_showView?.setOnClickListener {

            //inflate 方法只能被调用一次，因为调用后viewStub对象就被移除了视图树；
            if (errorView == null){
                errorView = view_stub?.inflate()
            } else{
                errorView?.visibility = View.VISIBLE
            }
            errorView?.tv_vsContent?.text = "没有相关数据，请刷新"
        }

        btn_vs_changeHint?.setOnClickListener {
            errorView?.tv_vsContent?.text = "网络异常，无法刷新，请检查网络"
        }

        btn_vs_hideView?.setOnClickListener{
            errorView?.visibility = View.GONE
        }

    }

    override fun initData() {

    }

}