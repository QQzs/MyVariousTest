package com.zs.various.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.meituan.android.walle.WalleChannelReader;
import com.tencent.smtt.sdk.QbSdk;
import com.zs.various.util.SpUtil;

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
        SpUtil.init(this,SpUtil.APP_DATA);

        //初始化X5内核
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                //x5内核初始化完成回调接口，此接口回调并表示已经加载起来了x5，有可能特殊情况下x5内核加载失败，切换到系统内核。
            }

            @Override
            public void onViewInitFinished(boolean b) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.e("print","加载内核是否成功:"+b);
            }
        });

    }

    public static Context getAppContext() {
        return mContext;
    }
}
