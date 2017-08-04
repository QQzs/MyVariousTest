package com.zs.login.myvarioustest.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.zs.login.myvarioustest.bean.User
import java.util.*

/**
 * Created by zs
 * Date：2017年 06月 30日
 * Time：14:59
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

class NewActivity : Activity() {

    private val mList = ArrayList<User>()
    private val mMap = HashMap<Int, User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        for (i in 0..5) {
            val user = User("aa", i)
            mList.add(user)
        }

        val user1 = User("aa", 10)
        val user2 = User("bb", 20)
        val user3 = User("bb", 30)

        mMap.put(1, user1)

        val user = User()
        user.name = "abc"
        user.age = 12
        Log.d("My_Log", "user = " + user.toString())


    }
}
