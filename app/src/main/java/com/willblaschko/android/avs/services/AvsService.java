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
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.willblaschko.android.avs.R;
import com.willblaschko.android.avs.activity.IActivity;
import com.willblaschko.android.avs.controllers.RightMeshController;
import com.willblaschko.android.avs.models.User;

import java.util.List;


public class AvsService extends Service{
    public static final String START_FOREGROUND_ACTION = "io.left.meshim.action.startforeground";
    public static final String STOP_FOREGROUND_ACTION = "io.left.meshim.action.stopforeground";
    public static final int FOREGROUND_SERVICE_ID = 101;

    private Notification mServiceNotification;
    private boolean mIsBound = false;
    private boolean mIsForeground = false;
    private RightMeshController mMeshConnection;

    @Override
    public void onCreate() {
        super.onCreate();
        Intent stopForegroundIntent = new Intent(this, AvsService.class);
        stopForegroundIntent.setAction(STOP_FOREGROUND_ACTION);
        PendingIntent pendingIntent = PendingIntent.getService(this,0,stopForegroundIntent,0);

        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(this,NotificationChannel.DEFAULT_CHANNEL_ID);
        } else {
            //noinspection deprecation
            builder = new NotificationCompat.Builder(this);
        }
        mServiceNotification = builder.setAutoCancel(false)
                .setTicker("AVS Voice")
                .setContentTitle("AVS Voice Running")
                .setContentText("Tap to go offline.")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .setNumber(100)
                .build();




        User user = User.fromDisk(this);
        mMeshConnection = new RightMeshController(user);
        mMeshConnection.connect(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMeshConnection.disconnect();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mIsBound = true;
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mIsBound = false;
        mMeshConnection.setCallback(null);
        return false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        String action = null;
        if (intent != null) {
            action = intent.getAction();
        }
        if (action != null && action.equals(STOP_FOREGROUND_ACTION)) {
            if (mIsBound) {
                stopForeground(true);
            } else {
                stopSelf();
            }
        } else if (action != null && action.equals(START_FOREGROUND_ACTION)) {
            startInForeground();
        }
        return START_STICKY;
    }

    private void startInForeground() {
        startForeground(FOREGROUND_SERVICE_ID, mServiceNotification);
    }

    private final IAvsService.Stub mBinder = new IAvsService.Stub() {
        @Override
        public void setForeground(boolean value) throws RemoteException {
            if(value){
                startInForeground();
                mIsForeground = true;
            }else {
                stopForeground(true);
                mIsForeground = false;
            }
        }

        @Override
        public List<User> getOnlineUsers() throws RemoteException {
            return mMeshConnection.getUserList();
        }

        @Override
        public void registerActivityCallback(IActivity callback) throws RemoteException {
            mMeshConnection.setCallback(callback);
        }

        @Override
        public void sendTextMessage(User recipient, String message) throws RemoteException {

        }

        @Override
        public void showRightMeshSettings() throws RemoteException {

        }

        @Override
        public User fetchUserById(int id) throws RemoteException {
            return null;
        }
    };
}
