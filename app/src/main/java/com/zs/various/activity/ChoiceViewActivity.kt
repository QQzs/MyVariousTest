package com.zs.various.activity

import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.zs.various.R
import com.zs.various.adapter.ChoiceGridAdapter
import com.zs.various.base.BaseActivity
import com.zs.various.bean.GridItemBean
import com.zs.various.util.LogUtil
import com.zs.various.util.RecyclerViewUtil
import kotlinx.android.synthetic.main.activity_choice_layout.*

/**
 *
Created by zs
Date：2019年 01月 15日
Time：10:23
—————————————————————————————————————
About: 筛选
—————————————————————————————————————
 */
class ChoiceViewActivity : BaseActivity() {

    var mData: MutableList<GridItemBean> = mutableListOf()
    var mAdapter: ChoiceGridAdapter? = null

    override fun setLayoutId(): Int {
        return R.layout.activity_choice_layout
    }

    override fun initView() {

    }

    override fun initData() {

        mData.add(GridItemBean(1, "类型（多选）" , true))
        mData.add(GridItemBean("1" , "全部" , true))
        mData.add(GridItemBean("2" , "11"))
        mData.add(GridItemBean("3" , "12"))
        mData.add(GridItemBean("4" , "13"))
        mData.add(GridItemBean("4" , "14"))

        mData.add(GridItemBean(2 , "标准（单选）"))
        mData.add(GridItemBean("5" , "20"))
        mData.add(GridItemBean("6" , "21"))
        mData.add(GridItemBean("7" , "22"))
        mData.add(GridItemBean("8" , "23"))
        mData.add(GridItemBean("8" , "24"))

        mAdapter = ChoiceGridAdapter(mData)
        RecyclerViewUtil.initGrid(this, recycler_grid , mAdapter,4)

        var layoutManager : GridLayoutManager = recycler_grid?.layoutManager as GridLayoutManager
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                var type = mAdapter?.getItemViewType(position)
                return if (type == 0) 1 else 4
            }
        }

    }

    fun backData(view: View){

        var data1 = mAdapter?.getMultiChoiceItem(1)
        if (data1 != null){
            LogUtil.logShow(data1?.toString())
        }
        var data2 = mAdapter?.getChoiceItem(2)
        if (data2 != null){
            LogUtil.logShow(data2?.toString())
        }

    }

}