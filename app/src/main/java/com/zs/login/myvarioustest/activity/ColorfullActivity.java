package com.zs.login.myvarioustest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zs.login.myvarioustest.R;

import butterknife.ButterKnife;

/**
 * Created by zs
 * Date：2017年 05月 11日
 * Time：14:28
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class ColorfullActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colorfull_layout);
        ButterKnife.bind(this);


    }
}
