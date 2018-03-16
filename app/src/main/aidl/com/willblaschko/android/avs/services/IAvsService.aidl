// IAvsService.aidl
package com.willblaschko.android.avs.services;

// Declare any non-default types here with import statements
import com.willblaschko.android.avs.models.User;
import com.willblaschko.android.avs.activity.IActivity;

interface IAvsService {

 void setForeground(in boolean value);

     List<User> getOnlineUsers();

    void registerActivityCallback(in IActivity callback);

    void sendTextMessage(in User recipient, in String message);

    void showRightMeshSettings();

    User fetchUserById(in int id);
}
