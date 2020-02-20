package com.zs.various.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
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
            window.statusBarColor = ContextCompat.getColor(this,R.color.color_3)
        }

        val listView = ListView(this)
        layout_main?.addView(listView)

        val data = ArrayList<Class<*>>()
        data.add(SmartRefreshActivity::class.java)
        data.add(CustomViewThreeActivity::class.java)
        data.add(ProfileActivity::class.java)
        data.add(VoiceActivity::class.java)
        data.add(SleepAndWaitActivity::class.java)
        data.add(LocalBroadcastActivity::class.java)
        data.add(ViewStubActivity::class.java)
        data.add(ViewFlipperActivity::class.java)
        data.add(RadarActivity::class.java)
        data.add(TestActivity::class.java)
        data.add(TestKotlinActivity::class.java)
        data.add(ChoiceViewActivity::class.java)
        data.add(TabTitleActivity::class.java)
        data.add(NotificationActivity::class.java)
        data.add(ThreadPoolActivity::class.java)
        data.add(DispatchEventActivity::class.java)
        data.add(DispatchActivity::class.java)
        data.add(TabLayoutActivity::class.java)
        data.add(TabHomeActivity::class.java)
        data.add(LeakCanaryActivity::class.java)
        data.add(SortActivity::class.java)
        data.add(RxJavaActivity::class.java)
        data.add(SyncActivity::class.java)
        data.add(BehaviorActivity::class.java)
        data.add(CoordinatorLayoutActivity::class.java)
        data.add(OpenFileActivity::class.java)
        data.add(UpdateActivity::class.java)
        data.add(ConstraintLayoutActivity::class.java)
        data.add(DrawerLayoutActivity::class.java)
        data.add(CXRecyclerViewActivity::class.java)
        data.add(ContactViewActivity::class.java)
        data.add(ContactActivity::class.java)
        data.add(FullVideoActivity::class.java)
        data.add(DatePickerActivity::class.java)
        data.add(ButtonActivity::class.java)
        data.add(ColorfullActivity::class.java)
        data.add(ColorfullBackActivity::class.java)
        data.add(ItemActivity::class.java)
        data.add(ShowActivity::class.java)
        data.add(WebViewActivity::class.java)
        data.add(CountDownActivity::class.java)
        data.add(RelativeActivity::class.java)
        data.add(VoiceAnimActivity::class.java)
        data.add(CustomViewFirstActivity::class.java)
        data.add(CustomViewTwoActivity::class.java)
        data.add(DialogWithPopupActivity::class.java)
        data.add(SpanStringActivity::class.java)
        data.add(AnimActivity::class.java)
        data.add(ReflexActivity::class.java)

        listView.adapter = MyAdapter(data)
        //点击进入Activity
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
            val adapter = parent.adapter
            val act = adapter.getItem(position) as Class<*>
            startActivity(Intent(this@MainActivity, act))
        }

    }

}
