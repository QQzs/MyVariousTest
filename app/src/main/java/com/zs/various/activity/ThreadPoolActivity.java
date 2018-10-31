package com.zs.various.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by zs
 * Date：2018年 10月 30日
 * Time：17:10
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class ThreadPoolActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * newCacheThreadPool一个无限大并可以缓存的线程池。在之前创建新线程的时刻首先复用已经创建过的空闲线程。
     */
    private void cacheThreadPool(){
        ExecutorService cacheThreadPool = Executors.newCachedThreadPool();
        for ( int i = 0 ; i<10 ; i++ ){
            final  int index = i;
            try {
                Thread.sleep(index*10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //拿到线程的对象的时候将线程扔到线程池中
            cacheThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d( "newCachedThreadPool" , "" + index );
                }
            });
        }
    }

    /**
     * newFixedThreadPool 可以控制最大并发数的线程池。超过最大并发数的线程进入等待队列。
     */
    private void fixedThreadPool(){
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i= 0;i<10;i++){
            final  int index = i;
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.d("newFixedThreadPool",index+"");
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * newScheduledThreadPool 一个可以定时执行的线程池。
     */
    private void scheduledThreadPool(){

        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.schedule(new Runnable() {
            @Override
            public void run() {
                Log.d("newScheduledThreadPool","延期3秒执行");
            }
        },3, TimeUnit.SECONDS);

    }

    public void initImTimeThreadPool(){

        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.d("newScheduledThreadPool","延时1s后每3s执行一次");
            }
        },1,3,TimeUnit.SECONDS);
    }

    /**
     * newSingleThreadExecutor 单线程化线程池。支持FIFO、LIFO的优先级执行。
     */
    private void singleThreadExecutor(){

        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i<10 ;i++){
            final int index = i ;
            singleThreadExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.d("newSingleThreadExecutor",""+index);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}
