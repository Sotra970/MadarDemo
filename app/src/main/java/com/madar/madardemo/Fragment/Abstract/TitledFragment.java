package com.madar.madardemo.Fragment.Abstract;

import android.app.Activity;

import com.madar.madardemo.Activity.NavDrawerActivity;

/**
 * Created by Ahmed on 8/16/2017.
 */

public class TitledFragment extends BaseFragment {

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
