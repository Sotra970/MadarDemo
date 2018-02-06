package com.madar.madardemo.Fragment.AddOrder;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.madar.madardemo.Fragment.Abstract.AddOrderBaseFragment;
import com.madar.madardemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ahmed on 8/15/2017.
 */

public class ReceivingPlace2Fragment extends AddOrderBaseFragment implements OnMapReadyCallback {

    @BindView(R.id.progressView)
    View progrssView;
    @BindView(R.id.container)
    View containerHolder;

    private GoogleMap mMap;

    private Marker currentPosIcon;

        String TAG  = "receiving_2" ;


    public static ReceivingPlace2Fragment getInstance() {
        return new ReceivingPlace2Fragment();
    }
    View v ;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (v == null) {
            v = inflater.inflate(R.layout.fragment_reciving_place2, container, false);
        }


        return v;
    }

    @Override
    public void onPageSelected(int pos) {
        Log.e(TAG , "onPageSelected");
        try {
            ButterKnife.bind(this, v);
            initLoading(this.progrssView, this.containerHolder);
            showLoading(true);
            setupPagerPullet(true , pos);
            setupMap();

        }catch (Exception e){

        }

    }

    private void setupMap() {

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.receiving_place_2_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onResume() {
        Log.e(TAG  , "on resume");
        super.onResume();
    }


    @Override
    public void onPause() {
        Log.e(TAG  , "on pause");
        super.onPause();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.e(TAG , "onMapReady");
        showLoading(false);
        mMap = googleMap;
        inti_map(mMap);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.setBuildingsEnabled(true);
        mMap.setIndoorEnabled(true);


        LatLng recipient_LatLng = new LatLng(getOrderModel().getRecipient_LAT() , getOrderModel().getRecipient_LUNG()) ;
        Log.e("receiving2" , "latlung " + recipient_LatLng);
        createCurrentPosMarker(recipient_LatLng);
        moveCamera(recipient_LatLng);

    }



    private void createCurrentPosMarker(LatLng recipient_LatLng) {
        BitmapDrawable home =(BitmapDrawable) ContextCompat.getDrawable(getActivity(), R.drawable.location);

        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(home.getBitmap());

        MarkerOptions markerOptions = null;
        markerOptions = new MarkerOptions().position(recipient_LatLng).icon(icon);


        currentPosIcon = mMap.addMarker(markerOptions);
    }




    @BindView(R.id.call_me_radio)
    RadioButton call_me_radio  ;

    @BindView(R.id.collect_the_package_button)
    RadioButton collect_the_package_radio  ;

    @BindView(R.id.driver_extra_info_input)
    EditText driver_extra_info_input  ;


    @OnClick(R.id.call_me_radio_con)
    void callme_listener(){
        if (call_me_radio.isChecked()){
            call_me_radio.setChecked(false);
        }else call_me_radio.setChecked(true);
    }
    @OnClick(R.id.collect_the_package_button_con)
    void collect_listener(){
        if (collect_the_package_radio.isChecked()){
            collect_the_package_radio.setChecked(false);
        }else collect_the_package_radio.setChecked(true);
    }





    @OnClick(R.id.next)
    void next(){
        showLoading(true);
        String driver_extra_info = driver_extra_info_input.getText().toString()  ;
        getOrderModel().setMore_Place_Details(driver_extra_info);
        if (call_me_radio.isChecked()){
            getOrderModel().setContact_Before_Arrival(1);
        }else{
            getOrderModel().setContact_Before_Arrival(0);
        }

        if (collect_the_package_radio.isChecked()){
            getOrderModel().setCapture_The_Package(1);
        }else{
            getOrderModel().setCapture_The_Package(0);
        }

        if (!TextUtils.isEmpty(driver_extra_info_input.getText().toString().trim())){
            getOrderModel().setMore_Place_Details(driver_extra_info_input.getText().toString().trim());
        }
        getOnNextClick().next(getOrderModel());
        showLoading(false);
    }


    @OnClick(R.id.cancel)
    void cancel_order(){
        getAddOrderActivity().backClick();
    }

}
