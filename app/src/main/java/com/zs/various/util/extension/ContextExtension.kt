package com.zs.various.util.extension

import android.content.Context
import android.widget.Toast

/**
 *
Created by zs
Date：2019年 03月 28日
Time：10:22
—————————————————————————————————————
About:
—————————————————————————————————————
 */
fun Context.myToast(message: String, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, message , duration).show()
}







