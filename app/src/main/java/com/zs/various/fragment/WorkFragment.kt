package com.zs.various.fragment

import android.os.Bundle
import android.view.View
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import com.zs.various.R
import com.zs.various.base.BaseFragment
import com.zs.various.util.RecyclerViewUtil
import kotlinx.android.synthetic.main.fragment_work.*

/**
 * @Author: zs
 * @Date: 2019-05-25 10:09
 *
 * @Description:
 */
class WorkFragment: BaseFragment(){

    override fun getLayoutId(): Int {
        return R.layout.fragment_work    }

    override fun initView(view: View?, savedInstanceState: Bundle?) {
    }

    override fun initData() {

        var data = mutableListOf<String>()
        for( i in 0..20){
            data.add("" + i)
        }

        var adapter = object : CommonAdapter<String>(activity, R.layout.item_work_layout, data) {
            override fun convert(holder: ViewHolder, s: String, position: Int) {

            }
        }
        RecyclerViewUtil.initGrid(context , work_recycler_view , adapter , 3)
    }

}