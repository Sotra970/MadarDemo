package com.madar.madardemo.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.madar.madardemo.Util.LocaleUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Ahmed on 8/15/2017.
 */

public abstract class LocalizedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocaleUtils.changeDefaultLanguage(
                LocaleUtils.LANGUAGE_ARABIC
        );
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
