package com.zs.various.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.ListView
import butterknife.ButterKnife
import com.zs.various.R
import com.zs.various.adapter.MyAdapter
import java.util.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        //        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#3300ff00"), true);

        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        //            //透明状态栏
        //            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //            //透明导航栏
        //            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //
        //        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }

        val layout = findViewById(R.id.layout_main) as LinearLayout
        val listView = ListView(this)
        layout.addView(listView)

        val acts = ArrayList<Class<*>>()
        acts.add(ButtonActivity::class.java)
        acts.add(ColorfullActivity::class.java)
        acts.add(ColorfullBackActivity::class.java)
        acts.add(ItemActivity::class.java)
        acts.add(RadarActivity::class.java)
        acts.add(ShowActivity::class.java)
        acts.add(WebViewActivity::class.java)
        acts.add(CountDownActivity::class.java)
        acts.add(RelativeActivity::class.java)
        acts.add(VoiceAnimActivity::class.java)
        listView.adapter = MyAdapter(acts)

        //点击进入Activity
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val adapter = parent.adapter
            val act = adapter.getItem(position) as Class<*>
            startActivity(Intent(this@MainActivity, act))

        }

    }

}
