package com.zs.login.myvarioustest.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zs.login.myvarioustest.R;
import com.zs.login.myvarioustest.view.ItemView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zs
 * Date：2017年 05月 19日
 * Time：15:58
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class ItemActivity extends Activity implements View.OnClickListener{

    @Bind(R.id.item_1)
    ItemView itemView1;
    @Bind(R.id.item_4)
    ItemView itemView4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_layout);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);

        }

        itemView1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
