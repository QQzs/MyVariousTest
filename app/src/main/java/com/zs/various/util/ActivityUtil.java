package com.zs.various.util;

import android.app.Activity;

/**
 * Created by zs
 * Date：2018年 09月 19日
 * Time：16:51
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class ActivityUtil {

    public static boolean isFinish(Activity activity){
        if (activity == null || activity.isDestroyed() || activity.isFinishing()) {
            return true;
        }else{
            return false;
        }
    }


}
