package com.zs.various.bean.java2;

import com.zs.various.util.LogUtil;

/**
 * Created by zs
 * Date：2019年 03月 26日
 * Time：14:33
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class Person {

    public String name = "Person";

    public Person() {
        LogUtil.Companion.logShow("Person constructor");
    }

    public void action(int age){
        LogUtil.Companion.logShow("Person" + age);
    }

    public static void print(){
        LogUtil.Companion.logShow("print Person");
    }

}
