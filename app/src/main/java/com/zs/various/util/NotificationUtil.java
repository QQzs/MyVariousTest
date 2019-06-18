package com.zs.various.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.zs.various.R;
import com.zs.various.activity.BehaviorActivity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static android.app.Notification.VISIBILITY_SECRET;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * @Author: zs
 * @Date: 2019-06-17 15:03
 * @Description:
 */
public class NotificationUtil{

    public static final String NOTIFICATION_DATA = "notification_data";
    public static final int TYPE_DEFAULT = 0;
    public static final int TYPE_MORE = 1;
    public static final int TYPE_PICTURE = 2;
    public static final int TYPE_INBOX = 3;

    private Context mContext;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mNotificationBuilder;
    private final Builder mBuilder;

    public NotificationUtil(Context context, Builder builder) {

        mContext = context;
        mBuilder = builder;
        getNotificationManager(context);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (TextUtils.isEmpty(mBuilder.channelId)){
                NotificationChannel channel = new NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_HIGH);
                channel.setLockscreenVisibility(VISIBILITY_SECRET);
                mNotificationManager.createNotificationChannel(channel);
                mNotificationBuilder = new NotificationCompat.Builder(context, "channel_id");
            }else{
                NotificationChannel channel = new NotificationChannel(mBuilder.channelId, mBuilder.channelName, NotificationManager.IMPORTANCE_HIGH);
                channel.setLockscreenVisibility(VISIBILITY_SECRET);
                mNotificationManager.createNotificationChannel(channel);
                mNotificationBuilder = new NotificationCompat.Builder(context, mBuilder.channelId);
            }
        }else{
            mNotificationBuilder = new NotificationCompat.Builder(context, "channel_default_id");
        }

    }

    private NotificationManager getNotificationManager(Context context){

        if (mNotificationManager == null){
            mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        }
        return mNotificationManager;

    }

    public void notification(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (mBuilder.smallIcon == 0){
                mNotificationBuilder.setSmallIcon(R.mipmap.icon_small);
            }else{
                mNotificationBuilder.setSmallIcon(mBuilder.smallIcon);
            }
        }
        if (mBuilder.largeIcon == null){
            mNotificationBuilder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.icon_small));
        }else{
            mNotificationBuilder.setLargeIcon(mBuilder.largeIcon);
        }
        if (!TextUtils.isEmpty(mBuilder.title)){
            mNotificationBuilder.setContentTitle(mBuilder.title);
        }
        if (!TextUtils.isEmpty(mBuilder.content)){
            mNotificationBuilder.setContentText(mBuilder.content);
        }
        mNotificationBuilder.setWhen(System.currentTimeMillis());

        //点击自动删除通知
        mNotificationBuilder.setAutoCancel(true);

        // 通知优先级
        mNotificationBuilder.setPriority(NotificationManager.IMPORTANCE_HIGH);

        // 设置通知 提示音 震动
        mNotificationBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

        //设置呼吸灯 参数：颜色  亮起时长 暗去时长
        mNotificationBuilder.setLights(Color.GREEN, 1000, 1000);

        // 设置角标
        mNotificationBuilder.setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL);

        // 设置样式
        if (mBuilder.type == TYPE_DEFAULT){
            // 单行
        }else if (mBuilder.type == TYPE_MORE){
            // 多行文本
            mNotificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(mBuilder.content));
        } else if (mBuilder.type == TYPE_PICTURE){
            // 设置显示大图片
            if (mBuilder.bigPicture != null){
                mNotificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(mBuilder.bigPicture));
            }else{
                mNotificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.icon_small)));
            }
        } else if (mBuilder.type == TYPE_INBOX){
            // 多条通知
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            if (mBuilder.contentList != null){
                for (String content: mBuilder.contentList){
                    inboxStyle.addLine(content);
                }
                inboxStyle.setSummaryText("+" + mBuilder.contentList.size() + " " + mBuilder.title);
                mNotificationBuilder.setStyle(inboxStyle);
            }
        }

        // 通知落地页
        Intent intent;
        if (mBuilder.nextPage != null){
            intent = new Intent(mContext, mBuilder.nextPage);
        } else{
            intent = new Intent(mContext, BehaviorActivity.class);
        }
        if (mBuilder.params != null){
             intent.putExtra(NOTIFICATION_DATA, (Serializable)mBuilder.params);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        mNotificationBuilder.setContentIntent(pendingIntent);

        Notification notification = mNotificationBuilder.build();
        if (mBuilder.notificationId != 0){
            mNotificationManager.notify(mBuilder.notificationId,notification);
        }

    }

    /**
     * 清除一条通知
     * @param context
     * @param notificationId
     */
    public static void clearNotificationById(Context context, int notificationId){
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(notificationId);
    }

    /**
     * 清除所有通知
     * @param context
     */
    public void clearNotification(Context context){
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
    }

    public static class Builder{

        private Context context;

        private int notificationId;

        private String channelId;

        private String channelName;

        private int type;

        private int smallIcon;

        private Bitmap largeIcon;

        private Bitmap bigPicture;

        private String title;

        private String content;

        private List<String> contentList;

        private Class<?> nextPage;

        private Map<String , String> params;

        public Builder(Context context){
            this.context = context;
        }

        public Builder setNotificationId(int notificationId) {
            this.notificationId = notificationId;
            return this;
        }

        public Builder setChannel(String channelId , String channelName) {
            this.channelId = channelId;
            this.channelName = channelName;
            return this;
        }

        public Builder setSmallIcon(int smallIcon) {
            this.smallIcon = smallIcon;
            return this;
        }

        public Builder setLargeIcon(Bitmap largeIcon) {
            this.largeIcon = largeIcon;
            return this;
        }

        public Builder setBigPicture(Bitmap bigPicture) {
            this.bigPicture = bigPicture;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setContentList(List<String> contentList) {
            this.contentList = contentList;
            return this;
        }

        public Builder setType(int type) {
            this.type = type;
            return this;
        }

        public Builder setNextPage(Class<?> nextPage , Map<String, String> params) {
            this.nextPage = nextPage;
            this.params = params;
            return this;
        }

        public NotificationUtil build(){
            return new NotificationUtil(context , this);
        }

    }

}
