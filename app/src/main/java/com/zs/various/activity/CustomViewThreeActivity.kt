package com.zs.various.activity

import com.zs.various.R
import com.zs.various.adapter.MessageAdapter
import com.zs.various.base.BaseActivity
import com.zs.various.util.RecyclerViewUtil
import kotlinx.android.synthetic.main.activity_custom_view3.*

class CustomViewThreeActivity(): BaseActivity(){

    var mAdapter: MessageAdapter? = null

    override fun setLayoutId() = R.layout.activity_custom_view3

    override fun initView() {
        mAdapter = MessageAdapter()
        RecyclerViewUtil.init(this , custom_recycler_view, mAdapter)
    }

    override fun initData() {

    }

}