package com.cairohat.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.cairohat.activities.ActivityDialogNotification;
import com.cairohat.activities.MainActivity;
import com.cairohat.R;


/**
 * NotificationHelper is used to create new Notifications
 **/

public class NotificationHelper {
    Context context;
    
    
    public static final int NOTIFICATION_REQUEST_CODE = 100;
    
    
    //*********** Used to create Notifications ********//
    
    public static void showNewNotification(Context context, Intent intent ,  String type , int id ,  String title, String msg ) {
        com.cairohat.models.Notification notif = new com.cairohat.models.Notification();
        notif.type = type;
        notif.title = title;
        notif.id = Long.valueOf(id);
      //  notif.image = image;
        notif.created_at = System.currentTimeMillis();
        notif.content = msg;


        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent1 = ActivityDialogNotification.navigateBase(context, notif, true);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent1, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(notif.title);
        builder.setContentText(notif.content);
        builder.setSmallIcon(R.drawable.ic_logo);
        builder.setSound(notificationSound);
        builder  .setLights(Color.RED, 3000, 3000);
        builder.setVibrate(new long[] { 1000, 1000 });
      //  builder .setWhen(System.currentTimeMillis());
       // builder .setDefaults(Notification.DEFAULT_SOUND);
       // builder .setAutoCancel(true);
        builder.setDefaults(android.app.Notification.DEFAULT_LIGHTS);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setPriority(android.app.Notification.PRIORITY_HIGH);
        }
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(notif.content));
//        if (bitmap != null) {
//            builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).setSummaryText(notif.content));
//        }

        // display push notif
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int unique_id = (int) System.currentTimeMillis();
        notificationManager.notify(unique_id, builder.build());

//        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        Notification.Builder builder = new Notification.Builder(context);
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//
//        Intent notificationIntent;
//
//        if (intent != null) {
//            notificationIntent = intent;
//        }
//        else {
//            notificationIntent = new Intent(context, MainActivity.class);
//        }
//
//        PendingIntent pendingIntent = PendingIntent.getActivity((context), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//
//        // Create Notification
//        Notification notification = builder
//                .setContentTitle(title)
//                .setContentText(msg)
//                .setTicker(context.getString(R.string.app_name))
//                .setSmallIcon(R.drawable.ic_logo)
//                .setSound(notificationSound)
//                .setLights(Color.RED, 3000, 3000)
//                .setVibrate(new long[] { 1000, 1000 })
//                .setWhen(System.currentTimeMillis())
//                .setDefaults(Notification.DEFAULT_SOUND)
//                .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
//                .build();
//
//
//        notificationManager.notify(NOTIFICATION_REQUEST_CODE, notification);
        
    }
    
    
}

