package com.zs.login.myvarioustest.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.zs.login.myvarioustest.R;

import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#3300ff00"), true);


        // 全屏
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
////            getWindow().setNavigationBarColor(Color.TRANSPARENT);
////            getWindow().setStatusBarColor(Color.TRANSPARENT);
//
//            getWindow().setNavigationBarColor(getResources().getColor(R.color.tran_black));
//            getWindow().setStatusBarColor(getResources().getColor(R.color.tran_black));
//        }
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
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

    }

    public void animButton(View view){
        Intent intent = new Intent(this,ButtonActivity.class);
        startActivity(intent);
    }

    public void radarView(View view){
        Intent intent = new Intent(this,RadarActivity.class);
        startActivity(intent);
    }

    public void colorfullView(View view){
        Intent intent = new Intent(this,ColorfullActivity.class);
        startActivity(intent);
    }

    public void colorfullBackView(View view){
        Intent intent = new Intent(this,ColorfullBackActivity.class);
        startActivity(intent);
    }

    public void itemView(View view){
        Intent intent = new Intent(this,ItemActivity.class);
        startActivity(intent);
    }

    public void webView(View view){
        Intent intent = new Intent(this,WebViewActivity.class);
        startActivity(intent);
    }

}
