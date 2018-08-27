package com.zs.various.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ListView
import com.zs.various.R
import com.zs.various.adapter.MyAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            window.statusBarColor = Color.parseColor("#D2691E")
        }

        val listView = ListView(this)
        layout_main?.addView(listView)

        val acts = ArrayList<Class<*>>()
        acts.add(CoordinatorLayoutActivity::class.java)
        acts.add(OpenFileActivity::class.java)
        acts.add(UpdateActivity::class.java)
        acts.add(ConstraintLayoutActivity::class.java)
        acts.add(DrawerLayoutActivity::class.java)
        acts.add(CXRecyclerViewActivity::class.java)
        acts.add(ContactViewActivity::class.java)
        acts.add(ContactActivity::class.java)
        acts.add(FullVideoActivity::class.java)
        acts.add(DatePickerActivity::class.java)
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
        acts.add(CustomViewActivity::class.java)
        acts.add(DialogActivity::class.java)
        acts.add(SpanStringActivity::class.java)
        acts.add(AnimActivity::class.java)
        acts.add(ReflexActivity::class.java)

        listView.adapter = MyAdapter(acts)

        //点击进入Activity
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val adapter = parent.adapter
            val act = adapter.getItem(position) as Class<*>
            startActivity(Intent(this@MainActivity, act))

        }

    }

}
