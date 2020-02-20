package com.zs.various.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 *
Created by zs
Date：2018年 07月 25日
Time：16:52
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class ActivityAdapter(var mData: MutableList<Class<*>>): androidx.recyclerview.widget.RecyclerView.Adapter<ActivityAdapter.ActivityHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityHolder {
        var view = View.inflate(parent?.context,android.R.layout.simple_list_item_1,null)
        return ActivityHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: ActivityHolder, position: Int) {
        holder?.bindData(position)
    }

    inner class ActivityHolder(itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){
        fun bindData(position: Int) = with(itemView){
            (itemView as TextView).text = mData[position].simpleName

        }
    }
}