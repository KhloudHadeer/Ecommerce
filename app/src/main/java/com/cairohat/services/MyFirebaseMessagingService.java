package com.cairohat.services;

import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import com.cairohat.activities.SplashScreen;
import com.cairohat.utils.NotificationHelper;


/**
 * MyFirebaseMessagingService receives notification Firebase Cloud Messaging Server
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    
    
    //*********** Called when the Notification is Received ********//
    
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Intent notificationIntent = new Intent(getApplicationContext(), SplashScreen.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        NotificationHelper.showNewNotification
                (
                        getApplicationContext(),
                        notificationIntent,

                        remoteMessage.getData().get("type"),
                        Integer.parseInt(remoteMessage.getData().get("id")),
                        remoteMessage.getData().get("title"),
                        remoteMessage.getData().get("message")
                );
        
    }
    
}
