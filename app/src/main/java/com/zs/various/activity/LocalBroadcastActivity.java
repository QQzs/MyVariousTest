package com.zs.various.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Toast;

import com.zs.various.R;
import com.zs.various.base.BaseActivity;

/**
 * Created by zs
 * Date：2019年 03月 07日
 * Time：15:16
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class LocalBroadcastActivity extends BaseActivity {

    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_local_broad;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.zs.orderlybroadcast.AnotherBroadcastReceiver");
        localReceiver = new LocalReceiver();
        //注册本地接收器
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);

    }

    public void localBroadcast(View view){
        Intent intent = new Intent("com.zs.orderlybroadcast.AnotherBroadcastReceiver");
        //发送本地广播
        localBroadcastManager.sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    private class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"收到本地广播",Toast.LENGTH_SHORT).show();
        }
    }
}
