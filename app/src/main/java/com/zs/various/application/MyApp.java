package com.zs.various.application;

import android.content.Context;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import android.util.Log;

import com.meituan.android.walle.WalleChannelReader;
import com.zs.various.util.SpUtil;

/**
 * Created by zs
 * Date：2017年 05月 12日
 * Time：18:58
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class MyApp extends MultiDexApplication {

    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        String channel = WalleChannelReader.getChannel(this.getApplicationContext());
        Log.d("My_Channel","channel = " + channel);
        SpUtil.init(this,SpUtil.APP_DATA);

    }

    public static Context getAppContext() {
        return mContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
