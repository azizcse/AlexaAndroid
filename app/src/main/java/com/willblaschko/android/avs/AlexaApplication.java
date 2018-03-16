package com.willblaschko.android.avs;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.willblaschko.android.avs.utility.SharedPref;
import com.willblaschko.android.avs.utility.SigningKey;

/**
 * An application to handle all our initialization for the Alexa library before we
 * launch our VoiceLaunchActivity
 */
public class AlexaApplication extends Application {

    //Our Amazon application product ID, this is passed to the server when we authenticate
    private static final String PRODUCT_ID = "interactive_conversation";
    private static Context context;

    //Our Application instance if we need to reference it directly
    private static AlexaApplication mInstance;

    public static Context toContext(){
        return context;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        mInstance = this;
        SharedPref.init(getApplicationContext());
        //if we run in DEBUG mode, we can get our signing key in the LogCat
        if(BuildConfig.DEBUG){
            Log.i("AlexaApplication", SigningKey.getCertificateMD5Fingerprint(this));
        }
    }

    /**
     * Return a reference to our mInstance instance
     * @return our current application instance, created in onCreate()
     */
    public static AlexaApplication getInstance(){
        return mInstance;
    }



}
