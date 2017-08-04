package com.zs.login.myvarioustest.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import butterknife.ButterKnife
import com.zs.login.myvarioustest.R
import com.zs.login.myvarioustest.util.bindView
import com.zs.login.myvarioustest.view.ItemView
import org.jetbrains.anko.toast

/**
 * Created by zs
 * Date：2017年 05月 19日
 * Time：15:58
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

class ItemActivity : Activity(), View.OnClickListener {

    val itemView1: ItemView by bindView(R.id.item_1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_layout)
        ButterKnife.bind(this)

//        if (Build.VERSION.SDK_INT >= 21) {
//            val decorView = window.decorView
//            val option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            decorView.systemUiVisibility = option
//            window.navigationBarColor = Color.TRANSPARENT
//            window.statusBarColor = Color.TRANSPARENT
//
//        }
        itemView1!!.setOnClickListener(this)

    }

    override fun onClick(v: View) {

        when(v.id){
            R.id.item_1 ->{
                itemView1.content = "ssssss"
                toast("item1")
            }

        }

    }
}
