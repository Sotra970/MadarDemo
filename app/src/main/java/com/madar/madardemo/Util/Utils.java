package com.madar.madardemo.Util;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.widget.TextView;

import com.madar.madardemo.AppManger.MadarApplication;

import java.util.Calendar;

/**
 * Created by Ahmed on 8/15/2017.
 */

public class Utils {

    public static int getSystemApiNumber(){
        return Build.VERSION.SDK_INT;
    }

    public static long getSystemTime(){
        try {
            return Calendar.getInstance().getTime().getTime();
        }
        catch (Exception e){
            return -1L;
        }
    }

    public static void loadFont(TextView v, int type, Context context){
        try {
            int flags = v.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG
                    | Paint.ANTI_ALIAS_FLAG;
            v.setPaintFlags(flags);

            String f = null;

            switch (type){
                case MadarApplication.FONT_CHANGA_REGULAR:
                    f = "fonts/Changa-Regular.ttf";
                    break;

                case MadarApplication.FONT_CHANGA_BOLD:
                    f = "fonts/Changa-Bold.ttf";
                    break;

                case MadarApplication.FONT_CHANGA_LIGHT:
                    f = "fonts/Changa-Light.ttf";
                    break;

                case MadarApplication.FONT_CHANGA_EXTRA_BOLD:
                    f = "fonts/Changa-ExtraBold.ttf";
                    break;

                case MadarApplication.FONT_CHANGA_EXTRA_LIGHT:
                    f = "fonts/Changa-ExtraLight.ttf";
                    break;

                case MadarApplication.FONT_CHANGA_MEDIUM:
                    f = "fonts/Changa-Medium.ttf";
                    break;

                case MadarApplication.FONT_CHANGA_SEMI_BOLD:
                    f = "fonts/Changa-SemiBold.ttf";
                    break;
            }

            if(f != null && !f.isEmpty()){
                Typeface tf = Typeface.createFromAsset(
                        context.getAssets(),
                        f
                );

                v.setTypeface(tf);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
