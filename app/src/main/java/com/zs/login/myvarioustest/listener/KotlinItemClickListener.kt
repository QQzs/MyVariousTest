package com.zs.kotlin.mykotlin.listener

import android.support.v7.widget.RecyclerView


interface KotlinItemClickListener {

    fun onItemClick(viewHolder: RecyclerView.ViewHolder, data: Any)
}
