package com.zs.various.activity

import android.os.Bundle
import android.view.View
import com.zs.various.R
import com.zs.various.bean.IroMan
import com.zs.various.fragment.TestFragment
import com.zs.various.listener.TestHero
import com.zs.various.util.LogUtil
import kotlinx.android.synthetic.main.activity_reflex.*

class ReflexActivity : androidx.fragment.app.FragmentActivity() {

    var fragmentmanager : androidx.fragment.app.FragmentManager?= null
    var transaction : androidx.fragment.app.FragmentTransaction?= null
    var fragment = TestFragment()
    var flag : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reflex)

        var hero1 = createHero("com.zs.various.bean.IroMan")
        hero1.attack()

        var hero2 = createHero("com.zs.various.bean.Hulk")
        hero2.attack()

        fragment_layout.visibility = View.GONE

        fragmentmanager = this.supportFragmentManager
        transaction = fragmentmanager?.beginTransaction()
        transaction?.add(R.id.fragment_layout,fragment)
        transaction?.commit()

        btn_fragment.setOnClickListener {
            if (!flag){
                fragment_layout.visibility = View.VISIBLE
            }else{
                fragment_layout.visibility = View.GONE
            }
            flag = !flag
        }


        tv_line_1?.post {
            LogUtil.logShow("height 1 = ${tv_line_1.height}")
        }

        tv_line_2?.post {
            LogUtil.logShow("height 2 = ${tv_line_2.height}")
        }

    }

    private fun createHero(name: String): TestHero {
        try {
            val cls = Class.forName(name)
            return cls.newInstance() as TestHero
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return IroMan()
    }
}

