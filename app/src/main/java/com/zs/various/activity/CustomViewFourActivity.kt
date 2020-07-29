package com.zs.various.activity

import android.text.TextUtils
import android.view.View
import com.zs.various.R
import com.zs.various.base.BaseActivity
import com.zs.various.view.flowlayout.FoldFlowLayout
import com.zs.various.view.flowlayout.adapter.FoldAdapter
import kotlinx.android.synthetic.main.activity_custom_view4.*
import kotlinx.android.synthetic.main.item_tag_view.view.*

class CustomViewFourActivity(): BaseActivity(){

    var tagViews = mutableListOf<View>()

    var tagStrings = mutableListOf<String>()

    var adapter: FoldAdapter? = null

    override fun setLayoutId() = R.layout.activity_custom_view4

    override fun initView() {

        adapter = FoldAdapter()
        flow_layout_mine?.setTagAdapter(adapter)
        btn_add?.setOnClickListener {
            var tag = et_tag.text.toString().trim()
            if (!TextUtils.isEmpty(tag)) {
                var view = View.inflate(this , R.layout.item_tag_view , null);
                view.tv_tag_view.text = tag
                tagStrings.add(0 , tag)

            } else {
                var view = View.inflate(this , R.layout.item_tag_view , null);
                view.tv_tag_view.text = "${tagViews.size} 我的数据"
                tagStrings.add(0 , "${tagStrings.size} 我的数据")

            }
            adapter?.addAll(tagStrings)
            et_tag.setText("")
        }

        btn_clear?.setOnClickListener {
            tagStrings.clear()
            adapter?.addAll(tagStrings)
        }

        btn_reset?.setOnClickListener {
            flow_layout_mine?.setFold(true)
        }
    }

    override fun initData() {


    }



}