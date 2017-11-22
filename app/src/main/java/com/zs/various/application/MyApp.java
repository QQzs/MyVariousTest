package com.zs.various.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.meituan.android.walle.WalleChannelReader;
import com.zs.various.util.SharedPreferencesMgr;

/**
 * Created by zs
 * Date：2017年 05月 12日
 * Time：18:58
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class MyApp extends Application {

    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        String channel = WalleChannelReader.getChannel(this.getApplicationContext());
        Log.d("My_Channel","channel = " + channel);
        SharedPreferencesMgr.init(this,"my_data");
    }

    public static Context getAppContext() {
        return mContext;
    }
}
