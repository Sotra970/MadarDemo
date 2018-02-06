package com.madar.madardemo.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.madar.madardemo.Model.PreferenceItem;
import com.madar.madardemo.R;

import java.util.ArrayList;

/**
 * Created by Ahmed on 8/21/2017.
 */

public class PreferenceManager {

    private final static String PREFERENCE_FILE_SETTINGS = "PREFERENCE_FILE_SETTINGS";

    private final static String PREFERENCE_KEY_ENABLE_NOTIFICATIONS =
            "PREFERENCE_KEY_ENABLE_NOTIFICATIONS";

    private static SharedPreferences sharedPreferences;

    public static SharedPreferences initSharedPreferences(Context context){
        if(sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences(
                    PREFERENCE_FILE_SETTINGS,
                    Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public static void savePreference(int titleResId, boolean active, Context context){
        SharedPreferences preferences = initSharedPreferences(context);
        String key = null;
        switch (titleResId){
            case R.string.preference_notifications:
                key = PREFERENCE_KEY_ENABLE_NOTIFICATIONS;
                break;
        }
        if(key != null){
            preferences
                    .edit()
                    .putBoolean(key, active)
                    .apply();
        }
    }

    public static boolean isPreferenceEnabled(int titleResId, Context context){
        SharedPreferences preferences = initSharedPreferences(context);
        String key = null;
        switch (titleResId){
            case R.string.preference_notifications:
                key = PREFERENCE_KEY_ENABLE_NOTIFICATIONS;
                break;
        }
        if(key != null){
            return preferences
                    .getBoolean(key, true);
        }
        return true;
    }

    public static ArrayList<PreferenceItem> getPreferences(Context context){
        ArrayList<PreferenceItem> preferenceItems = new ArrayList<>();
        preferenceItems.add(
                new PreferenceItem(
                        R.string.preference_notifications,
                        R.drawable.notification_bell,
                        isPreferenceEnabled(R.string.preference_notifications, context)
                )
        );
        return preferenceItems;
    }
}
