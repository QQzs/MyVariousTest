package com.zs.various.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zs.various.util.NotificationUtil;

/**
 * Created by kris on 16/4/14.
 */
public class CloseNoticeBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int noticeId = intent.getIntExtra(NotificationUtil.NOTICE_ID_KEY, -1);
        if(noticeId != -1){
            NotificationUtil.clearNotification(context, noticeId);
        }
    }
}
