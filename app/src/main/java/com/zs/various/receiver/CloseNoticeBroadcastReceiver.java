package com.zs.various.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zs.various.util.LogUtil;
import com.zs.various.util.NotificationUtil;
import com.zs.various.util.NotificationUtilCopy;

/**
 *
 * @author kris
 * @date 16/4/14
 */
public class CloseNoticeBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int noticeId = intent.getIntExtra(NotificationUtilCopy.NOTICE_ID_KEY, -1);
        if(noticeId != -1){
//            NotificationUtilCopy.clearNotification(context, noticeId);
            NotificationUtil.clearNotificationById(context , noticeId);
            LogUtil.Companion.logShow("My_Log id = " + noticeId);

        }
    }
}
