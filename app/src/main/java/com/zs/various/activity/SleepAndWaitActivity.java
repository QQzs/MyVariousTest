package com.zs.various.activity;

import com.zs.various.R;
import com.zs.various.base.BaseActivity;
import com.zs.various.util.LogUtil;

/**
 * Created by zs
 * Date：2019年 03月 11日
 * Time：16:11
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class SleepAndWaitActivity extends BaseActivity {


    @Override
    protected int setLayoutId() {
        return R.layout.activity_null;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

        new Thread(new Thread1()).start();
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        synchronized (SleepAndWaitActivity.class){
            SleepAndWaitActivity.class.notify();
        }
        new Thread(new Thread2()).start();

    }

    private static class Thread1 implements Runnable {

        @Override
        public void run() {
            synchronized (SleepAndWaitActivity.class){

                LogUtil.Companion.logShow("Thread1 enter");

                LogUtil.Companion.logShow("Thread1 waiting  ====== ");

                try {
                    SleepAndWaitActivity.class.wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                LogUtil.Companion.logShow("Thread1 going on ");

                LogUtil.Companion.logShow("Thread1 is over");

            }
        }
    }


    private static class Thread2 implements Runnable{

        @Override
        public void run() {

            LogUtil.Companion.logShow("Thread2 enter");

            LogUtil.Companion.logShow("Thread2 sleep ==== ");

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            LogUtil.Companion.logShow("Thread2 going on ");

            LogUtil.Companion.logShow("Thread2 is over");

//            synchronized (SleepAndWaitActivity.class){
//            }

        }
    }

}
