package com.zs.various.activity

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.bingoogolapple.baseadapter.BGAOnNoDoubleClickListener
import com.zs.various.R
import com.zs.various.adapter.RvAdapter
import com.zs.various.base.BaseActivity
import com.zs.various.bean.User
import com.zs.various.util.RecyclerViewUtil
import kotlinx.android.synthetic.main.activity_recycler_view.*
import org.jetbrains.anko.toast

/**
 * @Author: zs
 * @Date: 2020-05-06 17:11
 *
 * @Description:
 */
class BaseAdapterActivity : BaseActivity(){

    var mData = mutableListOf<User>()

    private var mAdapter: RvAdapter? = null

    override fun setLayoutId() = R.layout.activity_recycler_view

    override fun initView() {
        mAdapter = RvAdapter(recycler_view)
        RecyclerViewUtil.init(this , recycler_view , mAdapter?.headerAndFooterAdapter)
    }

    override fun initData() {
        for (i in 1..30){
            mData.add(User("name$i" , i))
        }
        mAdapter?.data = mData

        val header1Tv = TextView(mActivity)
        header1Tv.setBackgroundColor(Color.parseColor("#E15B5A"))
        header1Tv.setTextColor(Color.WHITE)
        header1Tv.gravity = Gravity.CENTER
        header1Tv.setPadding(30, 30, 30, 30)
        header1Tv.text = "头部1"
        header1Tv.setOnClickListener(object : BGAOnNoDoubleClickListener() {
            override fun onNoDoubleClick(v: View) {
                toast("Top")
            }
        })
        // 当时 LinearLayoutManager 时，需要设置一下布局参数的宽度为填充父窗体，否则 header 和 footer 的宽度会是包裹内容
        header1Tv.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
        mAdapter?.addHeaderView(header1Tv)

        val footer1Tv = TextView(mActivity)
        footer1Tv.setBackgroundColor(Color.parseColor("#6C9FFC"))
        footer1Tv.setTextColor(Color.WHITE)
        footer1Tv.gravity = Gravity.CENTER
        footer1Tv.setPadding(30, 30, 30, 30)
        footer1Tv.text = "底部1"
        footer1Tv.setOnClickListener(object : BGAOnNoDoubleClickListener() {
            override fun onNoDoubleClick(v: View) {
                toast("Bottom")
            }
        })
        footer1Tv.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
        mAdapter?.addFooterView(footer1Tv)

    }

}