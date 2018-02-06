package com.madar.madardemo.Activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.madar.madardemo.AppManger.MadarApplication;
import com.madar.madardemo.Fragment.MainFragment;
import com.madar.madardemo.Fragment.SplashFragment;
import com.madar.madardemo.R;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Ahmed on 8/17/2017.
 */

public class SplashActivity extends SocialActivity {

    private final static int PERMISSION_REQUEST_INTERNET = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        showFragment(SplashFragment.getInstance(), null);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (MadarApplication.getInstance().getPrefManager().getUser() == null)
                startLogin();
                else startHome();
            }
        } , 3000);


      //  requestInternetPermissions();
    }




    private void startHome() {
        Intent i = new Intent(getApplicationContext(), NavDrawerActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }


    private void startLogin() {
        showFragment(MainFragment.getInstance(), null);
    }

    private void requestInternetPermissions(){
        String[] perm = new String[]{
                Manifest.permission.INTERNET
        };
        if(!EasyPermissions.hasPermissions(
                this,
                perm
        ))
        {
            EasyPermissions.requestPermissions(this, null, PERMISSION_REQUEST_INTERNET, perm);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
