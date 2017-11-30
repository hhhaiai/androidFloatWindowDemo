package com.example.floas.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * @Copyright © 2017 Umeng Inc. All rights reserved.
 * @Description: TODO
 * @Version: 1.0
 * @Create: 2017/11/15 19:21
 * @author: safei
 * @mail: yongxu.xyf@alibaba-inc.com
 */
public class NotificationUtils {

    private static boolean isChannelSet = false;
    private static NotificationManager manager = null;

    static Notification.Builder getBuilder(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            if (!isChannelSet) {
                isChannelSet = true;
                NotificationChannel chan = new NotificationChannel("PRIMARY_CHANNEL", "NotificationChannel",
                    NotificationManager.IMPORTANCE_DEFAULT);
                if (manager == null) {
                    manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                }
                manager.createNotificationChannel(chan);
            }
            return new Notification.Builder(Content.CONTEXT, "PRIMARY_CHANNEL");
        } else {
            return new Notification.Builder(context);
        }
    }

    /**
     * 显示
     *
     * @param title
     * @param text
     * @param smallIconR 小图标R值 eg:R.drawable.small
     * @param bigIconR   大图标R值 eg.drawable.big
     */
    public static void show(String title, String text, int smallIconR, int bigIconR) {

        Notification.Builder builder = getBuilder(Content.CONTEXT);
        builder
            //.setContent("content")
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(smallIconR)
            .setLargeIcon(BitmapFactory.decodeResource(Content.CONTEXT.getResources(), bigIconR))
            //.setTicker("ticker")
            .setAutoCancel(true);
        if (manager == null) {
            manager = (NotificationManager)Content.CONTEXT.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        manager.notify(1, builder.getNotification());
    }
}
