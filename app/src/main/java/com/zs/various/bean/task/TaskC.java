package com.zs.various.bean.task;

import android.util.Log;

/**
 * Created by zs
 * Date：2018年 10月 29日
 * Time：9:14
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class TaskC extends TaskB{


    public TaskC() {

    }

    public void print(TaskA a){
        Log.d("My_Log","789");
    }

    public void print(TaskB b){
        Log.d("My_Log","101112");
    }
}
