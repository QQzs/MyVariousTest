package com.zs.various.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.zs.various.R;
import com.zs.various.activity.BehaviorActivity;

import static android.app.Notification.VISIBILITY_SECRET;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by zs
 * Date：2018年 11月 06日
 * Time：10:24
 * —————————————————————————————————————
 * About: Notification工具
 * —————————————————————————————————————
 */
public class NotificationUtilCopy {

    public static String CHANNEL_ID = "channel_id";
    public static String CHANANL_NAME = "channel_name";
    public static final String NOTICE_ID_KEY = "NOTICE_ID";
    public static final String ACTION_CLOSE_NOTICE = "com.zs.various.close";
    public static int notificationId = 111;

    public static void showNotification(Context context){

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);

        /**
         * 8.0 设置 NotificationChannel
         */
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANANL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setLockscreenVisibility(VISIBILITY_SECRET);//锁屏显示通知
            notificationManager.createNotificationChannel(channel);
        }

        /**
         * 默认情况下，从通知启动一个Activity，按返回键会回到主屏幕。
           但某些时候有按返回键仍然留在当前应用的需求，这就要用到TaskStackBuilder了
           清单文件要配置 android:parentActivityName=".MainActivity"
         */
//        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
//        taskStackBuilder.addParentStack(BehaviorActivity.class);
//        taskStackBuilder.addNextIntent(new Intent(context, BehaviorActivity.class));
//        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(1,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intent = new Intent(context, BehaviorActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setSmallIcon(R.mipmap.icon_star);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher));
        builder.setContentTitle("title");
        builder.setContentText("content");
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true); //点击自动删除通知
        builder.setLights(Color.GREEN, 1000, 1000);//设置呼吸灯 参数：颜色  亮起时长 暗去时长
        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(context.getResources(),R.mipmap.timg)));//设置显示大图片

        Notification notification = builder.build();
//        notification.flags = Notification.FLAG_NO_CLEAR; // 通知不可清除
        notificationManager.notify(notificationId,notification);

    }

    public static void showNotification2(Context context){

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);

        /**
         * 8.0 设置 NotificationChannel
         */
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANANL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setLockscreenVisibility(VISIBILITY_SECRET);//锁屏显示通知
            notificationManager.createNotificationChannel(channel);
        }

        // 点击打开通知
        Intent intent = new Intent(context, BehaviorActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        // 通知关闭按钮
        Intent closeIntent = new Intent();
        closeIntent.setAction(ACTION_CLOSE_NOTICE);
        closeIntent.putExtra(NOTICE_ID_KEY, notificationId);

        /**
         * 适配8.0 广播 添加 setComponent
         */
        closeIntent.setComponent(new ComponentName("com.zs.various",
                "com.zs.various.receiver.CloseNoticeBroadcastReceiver"));
        PendingIntent pendingClose = PendingIntent.getBroadcast(context, 0, closeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 通知的大布局 最高256dp
        RemoteViews remoteBigViews = new RemoteViews(context.getPackageName(), R.layout.notification_big_layout);
        remoteBigViews.setImageViewResource(R.id.iv_icon,R.mipmap.timg);
        remoteBigViews.setOnClickPendingIntent(R.id.iv_close, pendingClose);

        // 通知默认布局 高度64dp
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
        remoteViews.setImageViewResource(R.id.iv_icon,R.mipmap.timg);
        remoteViews.setOnClickPendingIntent(R.id.iv_close, pendingClose);

        builder.setSmallIcon(R.mipmap.icon_star);
        builder.setContentTitle("title");
        builder.setContentText("content");
        builder.setAutoCancel(true); //点击自动删除通知
        builder.setContentIntent(pendingIntent);
        builder.setLights(Color.GREEN, 1000, 1000);//设置呼吸灯 参数：颜色  亮起时长 暗去时长

        Notification notification = builder.build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification.bigContentView = remoteBigViews;
        }
        notification.contentView = remoteViews;
        notificationManager.notify(notificationId,notification);

    }

    public static void clearNotification(Context context, int noticeId) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(noticeId);
    }

}
