package com.zs.various.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.zs.various.R;

import java.lang.ref.WeakReference;

/**
 * Created by zs
 * Date：2018年 10月 09日
 * Time：14:03
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class LeakCanaryActivity extends AppCompatActivity {

    private LeakHandler1 mHandler1;

    public static class LeakHandler1 extends Handler{

        private WeakReference<Activity> weakReference;

        public LeakHandler1(Activity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Activity activity = weakReference.get();
            if (activity != null){
                if (msg.what == 1){
                    Log.d("My_Log","msg = " + msg.obj);
                }
            }

        }
    }

    /**
     * handler post 方式
     */
    private Handler mHandler2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        mHandler1 = new LeakHandler1(this);

        mHandler2 = new Handler();
    }

    public void handler(View view){

        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // a. 定义要发送的消息
                Message msg = Message.obtain();
                msg.what = 1;// 消息标识
                msg.obj = "AA";// 消息存放
                // b. 传入主线程的Handler & 向其MessageQueue发送消息
                mHandler1.sendMessage(msg);

                // post 方式
                mHandler2.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("My_Log","post  UI操作");
                    }
                });

            }
        }.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mHandler1.removeCallbacksAndMessages(null);
    }
}
