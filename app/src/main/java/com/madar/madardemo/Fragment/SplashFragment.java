package com.madar.madardemo.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madar.madardemo.Fragment.Abstract.BaseFragment;
import com.madar.madardemo.R;

/**
 * Created by Ahmed on 9/16/2017.
 */

public class SplashFragment extends BaseFragment{

    public static SplashFragment getInstance() {
        return new SplashFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_splash, container, false);
        return v;
    }
}
