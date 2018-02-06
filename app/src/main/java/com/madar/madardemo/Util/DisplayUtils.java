package com.madar.madardemo.Util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * Created by Ahmed on 3/29/2017.
 */

public class DisplayUtils {

    public static float getDeviceDensity(Context context){
        return context.getResources().getDisplayMetrics().density;
    }


    public static int getDeviceScreenWidthInDP(Context context){
        DisplayMetrics displayMetrics = new DisplayMetrics();

        WindowManager windowmanager = (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);

        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);

        return Math.round(displayMetrics.widthPixels / displayMetrics.density);
    }


    public static int dpToPx(float dp, Context context){
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()
        );
    }

    public static int pxToDp(float px, Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
