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
import com.madar.madardemo.Fragment.Abstract.BaseFragment;
import com.madar.madardemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ahmed on 8/15/2017.
 */

public class ShowLocationFragment extends AddOrderBaseFragment implements OnMapReadyCallback {

    @BindView(R.id.progressView)
    View progrssView;
    @BindView(R.id.container)
    View containerHolder;

    private GoogleMap mMap;

    private Marker currentPosIcon;

        String TAG  = "show_location" ;

    LatLng extra_lat_lung ;

    public void setExtra_lat_lung(LatLng extra_lat_lung) {
        this.extra_lat_lung = extra_lat_lung;
    }

    public static ShowLocationFragment getInstance() {
        return new ShowLocationFragment();
    }
    View v ;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (v == null) {
            v = inflater.inflate(R.layout.show_location_fragment, container, false);
            ButterKnife.bind(this, v);
            initLoading(this.progrssView, this.containerHolder);
            setupMap();

        }


        return v;
    }


    private void setupMap() {

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.show_location_map);
        mapFragment.getMapAsync(this);
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


        Log.e("receiving2" , "latlung " + extra_lat_lung);
        createCurrentPosMarker(extra_lat_lung);
        moveCamera(extra_lat_lung);

    }



    private void createCurrentPosMarker(LatLng recipient_LatLng) {
        BitmapDrawable home =(BitmapDrawable) ContextCompat.getDrawable(getActivity(), R.drawable.location);

        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(home.getBitmap());

        MarkerOptions markerOptions = null;
        markerOptions = new MarkerOptions().position(recipient_LatLng).icon(icon);


        currentPosIcon = mMap.addMarker(markerOptions);
    }





    @OnClick(R.id.next)
    void next(){
       getActivity().onBackPressed();
    }


    @Override
    public void onPageSelected(int pos) {

    }
}
