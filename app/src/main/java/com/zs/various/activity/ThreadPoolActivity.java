package com.zs.various.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zs.various.R;

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

    private TextView tv_show;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool_layout);
        tv_show = findViewById(R.id.tv_show);

    }

    public void clickListener(View view){
//        cacheThreadPool();
        fixedThreadPool();
//        scheduledThreadPool();
//        initImTimeThreadPool();
//        singleThreadExecutor();

    }

    /**
     * newCacheThreadPool一个无限大并可以缓存的线程池。在之前创建新线程的时刻首先复用已经创建过的空闲线程。
     */
    private void cacheThreadPool(){
        ExecutorService cacheThreadPool = Executors.newCachedThreadPool();
        for ( int i = 0 ; i<10 ; i++ ){
            final  int index = i;
            //拿到线程的对象的时候将线程扔到线程池中
            cacheThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.d( "newCachedThreadPool" , "" + index );
                        Thread.sleep((10 -index) * 200);
                        setTvShow(index);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
                        setTvShow(index);
                    } catch (Exception e) {
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
                Log.d("newScheduledThreadPool","延期1秒执行");
            }
        },1, TimeUnit.SECONDS);

    }

    public void initImTimeThreadPool(){

        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.d("newScheduledThreadPool","延时1s后每1s执行一次");
            }
        },1,1,TimeUnit.SECONDS);

        scheduledThreadPool.shutdown();
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
                        setTvShow(index);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void setTvShow(int index){
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                tv_show.append("\nindex = " + index);
            }
        });

    }

}
