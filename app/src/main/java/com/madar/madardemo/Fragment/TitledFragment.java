package com.madar.madardemo.Fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.madar.madardemo.Activity.NavDrawerActivity;

/**
 * Created by Ahmed on 8/16/2017.
 */

public class TitledFragment extends Fragment {

    protected void setTitle(int titleResId){
        Activity activity = getActivity();
        if(activity != null){
            NavDrawerActivity navDrawerActivity = (NavDrawerActivity) activity;
            if(navDrawerActivity != null){
                navDrawerActivity.setFragmentTitle(titleResId);
            }
        }
    }
}
