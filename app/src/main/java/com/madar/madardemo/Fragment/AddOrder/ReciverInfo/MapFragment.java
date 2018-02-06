package com.madar.madardemo.Fragment.AddOrder.ReciverInfo;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.madar.madardemo.Activity.ReciverInfoGetLocationActivity;
import com.madar.madardemo.AppManger.Config;
import com.madar.madardemo.Fragment.Abstract.AddOrderBaseFragment;
import com.madar.madardemo.R;
import com.madar.madardemo.Util.MapUtils.LatLngInterpolator;
import com.madar.madardemo.Util.MapUtils.markerAnimator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment  extends AddOrderBaseFragment implements OnMapReadyCallback {


    public MapFragment() {
        // Required empty public constructor
    }




    @BindView(R.id.progressView)
    View progrssView;
    @BindView(R.id.container)
    View containerHolder;
    private GoogleMap mMap;



    View v ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (v == null){

            v =  inflater.inflate(R.layout.fragment_map, container, false); ;
            ButterKnife.bind(this, v);
            initLoading(this.progrssView, this.containerHolder);
            showLoading(true);
            setupMap();
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                    broadcastReceiver , new IntentFilter(Config.REFRESH_Receive_info_MAp)
            );
        }
        return v ;
    }


    private void setupMap() {

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map_fragment_map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        inti_map(mMap);
        showLoading(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.setBuildingsEnabled(true);
        mMap.setIndoorEnabled(true);
        createCurrentPosMarker(new LatLng(gpsTracker.getLongitude() , gpsTracker.getLongitude()));

    }


    private Marker currentPosIcon;
    private void createCurrentPosMarker(LatLng recipient_LatLng) {
        BitmapDrawable home =(BitmapDrawable) ContextCompat.getDrawable(getActivity(), R.drawable.location);

        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(home.getBitmap());

        MarkerOptions markerOptions = null;
        markerOptions = new MarkerOptions().position(recipient_LatLng).icon(icon);


        currentPosIcon = mMap.addMarker(markerOptions);
    }


    @OnClick(R.id.get_location)
    void get_location(){
        startActivity(new Intent(getActivity() , ReciverInfoGetLocationActivity.class));
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LatLng currentLoc = new LatLng(getOrderModel().getDeleviry_LAT() ,getOrderModel().getDeleviry_LUNG());
            new markerAnimator().animateMarkerToICS(
                    currentPosIcon, currentLoc
                    , new LatLngInterpolator.Spherical()
                    , 1200);
            moveCamera(currentLoc , 14);
        }
    };


    @Override
    public void onPageSelected(int pos) {

    }
}
