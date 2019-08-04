package com.zs.various.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zs.various.bean.NotificationModel;
import com.zs.various.util.NotificationUtil;

import java.io.Serializable;

/**
 *
 * @author kris
 * @date 16/4/14
 */
public class CloseNoticeBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        int noticeId = intent.getIntExtra(NotificationUtilCopy.NOTICE_ID_KEY, -1);
//        if(noticeId != -1){
////            NotificationUtilCopy.clearNotification(context, noticeId);
////            NotificationUtil.clearNotificationById(context , noticeId);
//            LogUtil.Companion.logShow("My_Log id = " + noticeId);
//        }


        NotificationModel model = intent.getParcelableExtra(NotificationUtil.NOTIFICATION_DATA);
        NotificationUtil.clearNotificationById(context , model.notificationId);

        Intent next = new Intent(context , model.nextPage);
        next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        next.putExtra(NotificationUtil.NOTIFICATION_DATA , (Serializable) model.params);
        context.startActivity(next);

    }
}
