package com.cairohat.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cairohat.activities.SplashScreen;
import com.cairohat.app.MyAppPrefsManager;
import com.cairohat.utils.NotificationHelper;
import com.cairohat.utils.NotificationScheduler;


/**
 * AlarmReceiver receives the Broadcast Intent
 */

public class AlarmReceiver extends BroadcastReceiver {
    
    
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction() != null && context != null) {
            if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
        
                NotificationScheduler.setReminder(context, AlarmReceiver.class);
            }
        }


        Intent notificationIntent = new Intent(context, SplashScreen.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(context);


        //Trigger the notification
        NotificationHelper.showNewNotification
                (
                    context,
                    notificationIntent,"",0,
                    myAppPrefsManager.getLocalNotificationsTitle(),
                    myAppPrefsManager.getLocalNotificationsDescription()
                );
        
    }
    
}

