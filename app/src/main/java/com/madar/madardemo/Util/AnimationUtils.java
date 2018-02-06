package com.madar.madardemo.Util;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

import com.madar.madardemo.R;

/**
 * Created by Ahmed on 9/15/2017.
 */

public class AnimationUtils {

    public static void slideUpView(View view, Context context, Animation.AnimationListener listener, long duration){
        if(view != null){
            Animation animation=
                    android.view.animation.AnimationUtils
                            .loadAnimation(context, R.anim.view_slide_up);

            animation.setAnimationListener(listener);
            animation.setDuration(duration);

            view.startAnimation(animation);
        }
    }

    public static void slideDownView(View view, Context context, Animation.AnimationListener listener, long duration){
        if(view != null){
            Animation animation=
                    android.view.animation.AnimationUtils
                            .loadAnimation(context, R.anim.view_slide_down);

            animation.setAnimationListener(listener);
            animation.setDuration(duration);

            view.startAnimation(animation);
        }
    }
}
