package com.zs.various.bean.java1;

/**
 * Created by zs
 * Date：2019年 03月 26日
 * Time：13:31
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class SubClass extends Parent {

    static{
        System.out.println("d");
    }

    {
        System.out.println("e");
    }

    public SubClass(){
        System.out.println("f");
    }

}
