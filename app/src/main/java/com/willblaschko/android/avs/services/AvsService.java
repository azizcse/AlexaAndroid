package com.willblaschko.android.avs.services;

/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 3/16/2018 at 10:16 AM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Last edited by : Md. Azizul Islam on 3/16/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.willblaschko.android.avs.R;


public class AvsService extends Service{
    public static final String START_FOREGROUND_ACTION = "io.left.meshim.action.startforeground";
    public static final String STOP_FOREGROUND_ACTION = "io.left.meshim.action.stopforeground";
    public static final int FOREGROUND_SERVICE_ID = 101;

    private Notification mServiceNotification;
    private boolean mIsBound = false;
    private boolean mIsForeground = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Intent stopForegroundIntent = new Intent(this, AvsService.class);
        stopForegroundIntent.setAction(STOP_FOREGROUND_ACTION);
        PendingIntent pendingIntent = PendingIntent.getService(this,0,stopForegroundIntent,0);

        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(getApplicationContext(),NotificationChannel.DEFAULT_CHANNEL_ID);
        } else {
            //noinspection deprecation
            builder = new NotificationCompat.Builder(this);
        }
        mServiceNotification = builder.setAutoCancel(false)
                .setTicker("Mesh IM")
                .setContentTitle("Mesh IM is Running")
                .setContentText("Tap to go offline.")
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .setNumber(100)
                .build();


        /*mDatabase = Room.databaseBuilder(getApplicationContext(), MeshIMDatabase.class, "MeshIM")
                .addMigrations(Migrations.ALL_MIGRATIONS)
                .build();

        User user = User.fromDisk(this);
        mMeshConnection = new RightMeshController(user, mDatabase,this);
        mMeshConnection.connect(this);*/
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
