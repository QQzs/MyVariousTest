package com.zs.kotlin.mykotlin.listener

import androidx.recyclerview.widget.RecyclerView


interface KotlinItemClickListener {

    fun onItemClick(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, data: Any)
}
