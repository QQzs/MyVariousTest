package com.zs.various.util

import android.util.Log
import com.zs.various.bean.User

/**
 *
Created by zs
Date：2019年 01月 15日
Time：13:45
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class Test{

    var TAG = "Test"

    fun forMethod(){

        //顺序 遍历0-100 包括0和100
        for (index in 0..100) {
            Log.d(TAG, "- $index")
        }

        //倒序 遍历0-100 包括0和100
        for (index in 100 downTo 0) {
            Log.d(TAG, "$index")
        }

        //顺序 遍历0-99 注意：不包括100(until 排除结束元素)
        for (index in 0 until 100) {
            Log.d(TAG, "$index")
        }

        //遍历0-100 步长为5 输出0 5 10 15 20..
        for (index in 0..100 step 5) {
            Log.d(TAG, "$index")
        }

        val list_test = listOf(User("张三", 25), User("李四", 26), User("王五", 27))

        //取出集合中的每个元素  输出：User(name=张三, age=25) User(name=李四, age=26) User(name=王五, age=27)
        for (user in list_test) {
            Log.d(TAG, "$user")
        }

        //取出下标 输出 0 1 2
        for (index in list_test.indices) {
            Log.d(TAG, "$index")
        }

        /**
         *   取出下标和值  输出： IndexedValue(index=0, value=User(name=张三, age=25)) IndexedValue(index=1, value=User(name=李四, age=26))...
         *   使用"index.index"取出下标
         *   使用"index.value"取出User
         */
        for (index in list_test.withIndex()) {
            Log.d(TAG, "${index.value}")
        }
    }


}