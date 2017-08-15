package com.zs.various.application;

import android.app.Application;
import android.util.Log;

import com.meituan.android.walle.WalleChannelReader;

/**
 * Created by zs
 * Date：2017年 05月 12日
 * Time：18:58
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        String channel = WalleChannelReader.getChannel(this.getApplicationContext());
        Log.d("My_Channel","channel = " + channel);
    }
}
