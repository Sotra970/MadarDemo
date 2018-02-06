package com.madar.madardemo.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.madar.madardemo.Fragment.ConnectionFailedFragment;
import com.madar.madardemo.Fragment.MainFragment;
import com.madar.madardemo.Interface.NoConn;
import com.madar.madardemo.R;
import com.madar.madardemo.Util.LocaleUtils;

public class MainActivity extends FragmentSwitchActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocaleUtils.changeDefaultLanguage(
                LocaleUtils.LANGUAGE_ARABIC
        );

        Fragment f = MainFragment.getInstance();
        showFragment(f,false);
    }

}
