package com.zs.various.util;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by zs
 * Date：2018年 10月 09日
 * Time：14:59
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class BaseHandler<T extends BaseHandler.BaseHandlerCallBack> extends Handler {

    WeakReference<T> weakReference;

    public BaseHandler(T t) {
        weakReference = new WeakReference<>(t);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        T t = weakReference.get();
        if (t != null){
            t.callBack(msg);
        }
    }

    public interface BaseHandlerCallBack{
        void callBack(Message msg);
    }
}
