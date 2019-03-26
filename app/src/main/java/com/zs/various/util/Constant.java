package com.zs.various.util;

/**
 * Created by zs
 * Date：2017年 12月 18日
 * Time：10:49
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class Constant {

    public static String IMAGE_URL = "http://7xi8d6.com1.z0.glb.clouddn.com/20171212083612_WvLcTr_Screenshot.jpeg";

    public static void changeData(int[] data){
        for (int i = 0 ; i< data.length ;i++){
            data[i] *= 2;
        }
    }

}
