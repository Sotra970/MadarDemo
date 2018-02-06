package com.madar.madardemo.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.madar.madardemo.Activity.AddOrderActivity;
import com.madar.madardemo.Activity.NavDrawerActivity;
import com.madar.madardemo.Adapter.PagerViewAdapter;
import com.madar.madardemo.Fragment.Abstract.TitledFragment;
import com.madar.madardemo.R;
import com.madar.madardemo.Transition.PagerSwitcher;
import com.madar.madardemo.View.SmoothViewPager;
import com.rd.PageIndicatorView;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ahmed on 8/16/2017.
 */

public class DeliveryDestinationFragment extends TitledFragment implements DrawerLayout.DrawerListener{

    private static final int MY_PERMISSIONS_REQUEST_GPS = 66;

    private SmoothViewPager viewPager;
    private PageIndicatorView indicator;

    private View sameCityDeliveryButton;
    private View anotherCityDeliveryButton;

    private PagerSwitcher switcher;

    public static DeliveryDestinationFragment getInstance() {
        return new DeliveryDestinationFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        initViewPager();
                    }
                },
                1000L
        );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_delivery_destination, container, false);

        ButterKnife.bind(this , v) ;

        viewPager = (SmoothViewPager)
                v.findViewById(R.id.fragment_delivery_dest_view_pager);
        indicator = (PageIndicatorView)
                v.findViewById(R.id.fragment_delivery_dest_indicator);

        sameCityDeliveryButton =
                v.findViewById(R.id.fragment_delivery_dest_same_city_delivery);
        anotherCityDeliveryButton =
                v.findViewById(R.id.fragment_delivery_dest_other_city_delivery);

        return v;
    }

    private void initViewPager(){
        if(viewPager != null && indicator != null){
            viewPager.setAdapter(new PagerViewAdapter(getContext()));
            indicator.setViewPager(viewPager);

            switcher = new PagerSwitcher(getContext(), viewPager);
            switcher.setRun(true);
            switcher.startSwitching(3000);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle(R.string.fragment_title_delivery_destination);

        try {
            NavDrawerActivity navDrawerActivity = (NavDrawerActivity) getActivity();
            navDrawerActivity.addDrawerListener(this);
        }
        catch (Exception e){

        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }



    @OnClick(R.id.fragment_delivery_dest_same_city_delivery)
    void delver_2_same_city(){
        start_OrderAc(AddOrderActivity.extra_type_same);

    }


    @OnClick(R.id.fragment_delivery_dest_other_city_delivery)
    void delver_2_other_city(){
        start_OrderAc(AddOrderActivity.extra_type_other);
    }


    Intent intent ;
    void start_OrderAc(String extra){
         intent = new Intent(getActivity() , AddOrderActivity.class) ;
        intent.putExtra("extra_type" , extra) ;
        if (check_gps_permition())
        startActivity(intent);
    }


    private boolean check_gps_permition() {
        // Here, thisActivity is the current activity
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {



            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_GPS);
            return false ;

        }
        return  true ;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_GPS:
            {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    startActivity(intent);
                }
                else {
                    //  check_gps_permition();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "Permission needed to run your app", Toast.LENGTH_SHORT).show();
                }

                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



    @Override
    public void onStop() {
        super.onStop();
        if(switcher != null){
            switcher.setRun(false);
            switcher = null;
        }
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {
        if(switcher != null){
            switcher.setRun(false);
        }
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        if(switcher != null){
            switcher.setRun(true);
        }
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
