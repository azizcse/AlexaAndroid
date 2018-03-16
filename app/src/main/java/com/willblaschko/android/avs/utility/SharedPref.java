package com.willblaschko.android.avs.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/*
*  ****************************************************************************
*  * File Name: SharedPref.java
*  * Uses: For all type of SharedPreferences functionality
*
*  * Created by : Sudipto process 17-Oct-17 at 6:35 PM.
*  * Email : sudipta@w3engineers.com
*  *
*  * Last edited by : Mohd. Asfaq-E-Azam Rifat on 20-Feb-18.
*  *
*  * Last Reviewed by : <Reviewer Name> process <mm/dd/yy>
*  ****************************************************************************
*/
public class SharedPref {
    public static final String KEY_BOARD_HEIGHT = "keyboard_height";
    private static SharedPreferences preferences;
    private SharedPref() {
    }

    public static void init(Context context) {
        if (preferences == null) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    public static boolean write(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(key, value);

        return editor.commit();
    }

    public static boolean write(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(key, value);

        return editor.commit();
    }

    public static boolean write(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(key, value);

        return editor.commit();
    }

    public static boolean write(String key, long value) {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putLong(key, value);

        return editor.commit();
    }

    public static String read(String key) {
        return preferences.getString(key, "");
    }

    public static long readLong(String key) {
        return preferences.getLong(key, 0);
    }

    public static int readInt(String key) {
        return preferences.getInt(key, 0);
    }
    public static boolean readBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public static boolean readBooleanDefaultTrue(String key){
        return preferences.getBoolean(key, true);
    }

    public static boolean contains(String key) {
        return preferences.contains(key);
    }

    public static void delete(String key) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key).commit();
    }
}