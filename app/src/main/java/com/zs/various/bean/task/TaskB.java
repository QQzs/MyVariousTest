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
public class TaskB extends TaskA{

    public int a = 10;

    public TaskB() {
        Log.d("My_Log","TaskB");
    }

    public void print(){
        Log.d("My_Log","456");
    }
}
