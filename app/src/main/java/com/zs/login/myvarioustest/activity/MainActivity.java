package com.zs.login.myvarioustest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zs.login.myvarioustest.R;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // 全屏
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

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

}
