package com.zs.various.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zs.various.R
import com.zs.various.bean.IroMan
import com.zs.various.listener.TestHero

class ReflexActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reflex)

        var hero1 = createHero("com.zs.various.bean.IroMan")
        hero1.attack()

        var hero2 = createHero("com.zs.various.bean.Hulk")
        hero2.attack()

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

