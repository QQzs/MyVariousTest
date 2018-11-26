package com.zs.various.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jaeger.library.StatusBarUtil
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

class ItemActivity : AppCompatActivity() , View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_layout)

//        StatusBarUtil.setTransparent(this)
//        StatusBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(this,R.color.color_3))
        StatusBarUtil.setTranslucentForImageView(this,0 , null)

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
