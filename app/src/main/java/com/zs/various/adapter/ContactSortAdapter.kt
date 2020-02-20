package com.zs.various.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.zs.various.R
import com.zs.various.bean.SortModel
import kotlinx.android.synthetic.main.adapter_sort.view.*
import kotlinx.android.synthetic.main.item_letter_layout.view.*

/**
 *
Created by zs
Date：2018年 07月 23日
Time：15:19
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class ContactSortAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<ContactSortAdapter.ContactHolder>() {

    var mData: MutableList<SortModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        var view = View.inflate(parent?.context, R.layout.adapter_sort,null)
        return ContactHolder(view)
    }

    override fun getItemCount(): Int {
        return if (mData == null) 0 else mData!!.size
    }

    fun initData(data: MutableList<SortModel>?){
        this.mData = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder?.bindData(position)
    }


    inner class ContactHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){
        fun bindData(position: Int) = with(itemView) {
            var sortModel = mData!![position]
            name?.text = sortModel.name
            if (!compareSection(position)){
                tv_letter?.visibility = View.VISIBLE
                tv_letter?.text = sortModel.letter
                line_view?.visibility = View.GONE
            }else{
                tv_letter?.visibility = View.GONE
                line_view?.visibility = View.VISIBLE
            }
        }
    }

    fun compareSection(position: Int): Boolean {
        return if (position == 0) {
            false
        } else {
            val current = getSectionForPosition(position)
            val previous = getSectionForPosition(position - 1)
            current == previous
        }

    }

    // 获取当前位置的首字母(int表示ascii码)
    fun getSectionForPosition(position: Int): Int {
        return mData!![position].letter[0].toInt()
    }

    // 获取字母首次出现的位置
    fun getPositionForSection(section: Int): Int {
        for (i in 0 until itemCount) {
            val s = mData!![i].letter
            val firstChar = s.toUpperCase()[0]
            if (firstChar.toInt() == section) {
                return i
            }
        }
        return -1
    }

}