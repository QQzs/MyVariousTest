package com.zs.various.bean.java;

/**
 * Created by zs
 * Date：2019年 03月 26日
 * Time：13:31
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class SubClass extends Parent {

    public static String s_StaticField = "pgr";
    public String s_Field = "qaz";

    static{
        System.out.println(s_StaticField);
        System.out.println("ony");
    }
    {
        System.out.println(s_Field);
        System.out.println("tef");
    }

    public SubClass(){
        System.out.println("fye");
    }

}
