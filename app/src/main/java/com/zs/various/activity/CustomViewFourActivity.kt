package com.zs.various.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.zs.various.R
import com.zs.various.adapter.MessageAdapter
import com.zs.various.base.BaseActivity
import com.zs.various.util.RecyclerViewUtil
import kotlinx.android.synthetic.main.activity_custom_view3.*
import kotlinx.android.synthetic.main.activity_custom_view4.*

class CustomViewFourActivity(): BaseActivity(){

    private var mList = mutableListOf<String>()

    override fun setLayoutId() = R.layout.activity_custom_view4

    override fun initView() {

    }

    override fun initData() {
        mList.add("我的内容有点长有点长有点长有点长有点长有点长有点长有点长有点长有点长有点长有点长有点长有点长有点长有点长")
//        mList.add("IOS")
//        mList.add("python")
//        mList.add("IOSIOSIOSIOSIOSIOSIOSIOSIOSIOSIOSIOSIOSIOSIOSIOSIOSIOSIOSIOSIOSIOSIOSIOS")
//        mList.add("Android")
//        mList.add("Java")
//        mList.add("IOS")
//        mList.add("python")
//        mList.add("AndroidAndroidAndroidAndroidAndroid")
//        mList.add("Java")
//        mList.add("IOS")
//        mList.add("pythonpythonpythonpython")

        flow_layout.updateData(mList)

    }

}