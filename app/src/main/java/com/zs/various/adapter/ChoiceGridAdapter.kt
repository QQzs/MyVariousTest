package com.zs.various.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.zs.various.R
import com.zs.various.bean.GridItemBean
import kotlinx.android.synthetic.main.item_choice_tab.view.*
import kotlinx.android.synthetic.main.item_choice_title.view.*

/**
 *
Created by zs
Date：2019年 01月 15日
Time：10:47
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class ChoiceGridAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mData: MutableList<GridItemBean> = mutableListOf()

    constructor(mData: MutableList<GridItemBean>) : this() {
        this.mData = mData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0){
            return TabHolder(View.inflate(parent?.context , R.layout.item_choice_tab , null))
        }else{
            return TitleHolder(View.inflate(parent?.context , R.layout.item_choice_title , null))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return mData[position].type
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == 0){
            (holder as TabHolder).bindData(position)
        }else{
            (holder as TitleHolder).bindData(position)
        }

    }

    inner class TabHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindData(position: Int)= with(itemView){
            var data = mData[position]
            tv_item_tab?.text = data.name
            if (data.isChoice){
                tv_item_tab?.setBackgroundResource(R.mipmap.search_label_bg_sel02)
            }else{
                tv_item_tab?.setBackgroundResource(R.mipmap.search_label_bg_nor)
            }
            setOnClickListener {
                changeStatus(position)
            }
        }

    }

    inner class TitleHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindData(position: Int) = with(itemView){
            tv_item_title?.text = mData[position].title
        }

    }

    /**
     * 修改选择条目
     */
    fun changeStatus(position: Int){
        var last = 0               // 同类型的第一条数据位置
        var next = mData.size //  同类型的最后一条数据位置
        for(index in (position - 1) downTo 0){
            if (mData[index].type != 0){
                last = index
                break
            }
        }
        for (index in (position + 1) until mData.size){
            if (mData[index].type != 0){
                next = index
                break
            }
        }
        if (next > last){
            for (index in (last + 1) until next){
                mData[index].isChoice = position == index
            }
            notifyDataSetChanged()
        }
    }

    /**
     * 查找选择的条目
     */
    fun getChoiceItem(type: Int): GridItemBean?{
        var last = 0               // 同类型的第一条数据位置
        var next = mData.size  //  同类型的最后一条数据位置
        for (index in mData.indices){
            if (mData[index].type == type){
                last = index
                break
            }
        }
        for (index in (last + 1) until mData.size){
            if (mData[index].type != 0){
                next = index
                break
            }
        }
        for (index in (last + 1) until next){
            if (mData[index].isChoice){
                return mData[index]
                break
            }
        }
        return null
    }

}