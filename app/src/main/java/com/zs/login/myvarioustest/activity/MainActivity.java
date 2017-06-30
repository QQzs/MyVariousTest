package com.zs.login.myvarioustest.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.zs.login.myvarioustest.R;
import com.zs.login.myvarioustest.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#3300ff00"), true);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_main);
        ListView listView = new ListView(this);
        layout.addView(listView);

        List<Class> acts = new ArrayList<>();
        acts.add(ButtonActivity.class);
        acts.add(ColorfullActivity.class);
        acts.add(ColorfullBackActivity.class);
        acts.add(ItemActivity.class);
        acts.add(RadarActivity.class);
        acts.add(ShowActivity.class);
        acts.add(WebViewActivity.class);
        listView.setAdapter(new MyAdapter(acts));

        //点击进入Activity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Adapter adapter = parent.getAdapter();
                Class act = (Class) adapter.getItem(position);
                startActivity(new Intent(MainActivity.this, act));
            }
        });

    }

}
