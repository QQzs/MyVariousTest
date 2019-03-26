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

    public static String p_StaticField = "khg";
    public String p_Field = "yhr";

    static{
        System.out.println(p_StaticField);
        System.out.println("ign");
    }
    {
        System.out.println(p_Field);
        System.out.println("ngc");
    }

    public Parent(){
        System.out.println("ija");
    }


}
