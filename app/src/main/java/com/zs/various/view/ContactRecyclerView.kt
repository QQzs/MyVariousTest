package com.zs.various.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.zs.various.R
import com.zs.various.bean.SortModel
import kotlinx.android.synthetic.main.contact_list_layout.view.*

/**
 * Created by zs
 * Date：2018年 07月 09日
 * Time：13:36
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
class ContactRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) , SideBarView.onTouchChangeListener, SideBarView.onTouchListener{
    init {
        LayoutInflater.from(context).inflate(R.layout.contact_list_layout, this)
    }

    fun initListener(){

    }


    fun initData(data: MutableList<SortModel>){

    }

    fun getRecycler(): RecyclerView{
        return recycler_view
    }

    override fun setLetter(letter: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
