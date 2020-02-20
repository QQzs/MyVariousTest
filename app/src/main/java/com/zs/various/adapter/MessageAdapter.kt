package com.zs.various.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.zs.various.R
import com.zs.various.util.LogUtil
import kotlinx.android.synthetic.main.item_mesage.view.*

/**
 *
Created by zs
Date：2018年 07月 25日
Time：16:52
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class MessageAdapter(): androidx.recyclerview.widget.RecyclerView.Adapter<MessageAdapter.MyHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var view = View.inflate(parent?.context, R.layout.item_mesage,null)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return 20
    }

    fun startMessage(msg: String){

        notifyItemChanged(2)
        notifyItemChanged(19)

    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        LogUtil.logShow("onBindViewHolder position = $position")

        holder.itemView?.apply {

            if (position % 2 == 0){

                message_view_flipper?.visibility = View.GONE
                message_view_flipper?.stopFlipping()

                message_view?.visibility = View.VISIBLE
                message_view?.startAnim()
            }else{

                message_view_flipper?.visibility = View.VISIBLE
                message_view_flipper?.startFlipping()

                message_view?.visibility = View.GONE
                message_view?.stopAnim()
            }
        }
    }

    inner class MyHolder(itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)
}