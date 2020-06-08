package com.zs.various.bean.java1;

/**
 * Created by zs
 * Date：2019年 03月 26日
 * Time：13:27
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class Parent {

    static{
        System.out.println("a");
    }

    {
        System.out.println("b");
    }

    public Parent(){
        System.out.println("c");
    }


}
