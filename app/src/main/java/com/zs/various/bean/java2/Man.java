package com.zs.various.bean.java2;

import com.zs.various.util.LogUtil;

/**
 * Created by zs
 * Date：2019年 03月 26日
 * Time：14:34
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class Man extends Person{

    int age = 20;

    public String name = "Man";

    public Man() {
        LogUtil.Companion.logShow("Man constructor");
    }

    public void action(){
        LogUtil.Companion.logShow("Man" + age);
    }
    public static void print(){
        LogUtil.Companion.logShow("print Man");
    }

}
