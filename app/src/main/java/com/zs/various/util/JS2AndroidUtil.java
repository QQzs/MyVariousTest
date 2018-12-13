package com.zs.various.util;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import com.zs.various.activity.WebViewActivity;

/**
 * Created by zs
 * Date：2018年 12月 13日
 * Time：9:44
 * —————————————————————————————————————
 * About: JS交互操作
 * —————————————————————————————————————
 */
public class JS2AndroidUtil {

    private Activity mActivity;

    public JS2AndroidUtil(Activity activity) {
        this.mActivity = activity;
    }

    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void clickAction(String msg) {
        if (mActivity instanceof WebViewActivity){
            ((WebViewActivity) mActivity).show(msg);
        }
    }

}
