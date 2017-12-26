package com.zs.various.bean;

import android.util.Log;

import com.zs.various.listener.TestHero;

/**
 * Created by zs
 * Date：2017年 12月 25日
 * Time：17:31
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class Hulk implements TestHero {
    @Override
    public void attack() {
        Log.d("My_Log","First");
    }
}
