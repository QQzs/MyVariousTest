package com.zs.various.activity

import android.text.TextUtils
import android.view.View
import com.zs.various.R
import com.zs.various.base.BaseActivity
import com.zs.various.view.flowlayout.FlowTagLayoutMine
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
//        flow_fold_layout?.setTagAdapter(adapter)
        flow_layout_mine?.setTagAdapter(adapter)
        btn_add?.setOnClickListener {
            var tag = et_tag.text.toString().trim()
            if (!TextUtils.isEmpty(tag)) {
                var view = View.inflate(this , R.layout.item_tag_view , null);
                view.tv_tag_view.text = tag
//                tagViews.add(0 , view)

                tagStrings.add(tag)

//                flow_tag_layout.addView(view)
//                flow_layout_mine?.addView(view)

            } else {
                var view = View.inflate(this , R.layout.item_tag_view , null);
                view.tv_tag_view.text = "${tagViews.size} 我的数据"
//                tagViews.add(0 , view)

                tagStrings.add("${tagStrings.size} 我的数据")

//                flow_tag_layout.addView(view)

//                flow_layout_mine?.addView(view)

            }
//            flow_fold_layout?.setTagView(tagViews)
//            flow_layout_mine?.setTagView(tagViews)

            adapter?.addAll(tagStrings)

            et_tag.setText("")

        }

        btn_clear?.setOnClickListener {
//            flow_layout.clearTagView()
//            tagViews.clear()

            tagStrings.removeAt(tagStrings.size -1)
            adapter?.addAll(tagStrings)




//            tagViews.removeAt(tagViews.size -1);
//            flow_layout_mine?.setTagView(tagViews)
        }

    }

    override fun initData() {

        flow_layout_mine.setFoldListener(object : FlowTagLayoutMine.FoldViewListener{
            override fun onConvertFoldView(position: Int, foldView: View?) {
                foldView?.tv_tag_view?.text = "V"
                foldView?.setOnClickListener {
//                    flow_layout_mine.reSetView()
                }
            }
        })


    }



}