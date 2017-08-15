package com.zs.various.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

/**
 * Created by zs
 * Date：2017年 06月 15日
 * Time：15:06
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

class MyAdapter2(private val acts: List<Class<*>>?) : BaseAdapter() {

    private var inflater: LayoutInflater? = null

    override fun getCount(): Int {
        return acts?.size ?: 0
    }

    override fun getItem(position: Int): Any {
        return acts!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            if (inflater == null) {
                inflater = LayoutInflater.from(parent.context)
            }
            convertView = inflater!!.inflate(android.R.layout.simple_list_item_1, parent, false)
        }
        val textView = convertView as TextView?
        textView!!.text = acts!![position].simpleName
        return textView
    }
}
