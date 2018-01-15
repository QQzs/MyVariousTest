package com.zs.various.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.zs.various.R
import kotlinx.android.synthetic.main.item_layout.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_layout)

//        if (Build.VERSION.SDK_INT >= 21) {
//            val decorView = window.decorView
//            val option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            decorView.systemUiVisibility = option
//            window.navigationBarColor = Color.TRANSPARENT
//            window.statusBarColor = Color.TRANSPARENT
//
//        }
        item_1?.setOnClickListener(this)

    }

    override fun onClick(v: View) {

        when(v.id){
            R.id.item_1 ->{
                item_1.content = "ssssss"
                toast("item1")
            }

        }

    }
}
