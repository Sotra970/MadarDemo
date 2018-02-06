package com.madar.madardemo.Fragment.AddOrder.ReciverInfo;


import android.graphics.drawable.BitmapDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.madar.madardemo.Fragment.Abstract.AddOrderBaseFragment;
import com.madar.madardemo.Interface.NoConn;
import com.madar.madardemo.Model.ServerResponse.AddressDetailsResponse;
import com.madar.madardemo.Model.ServerResponse.MapLinkFetchResponse;
import com.madar.madardemo.R;
import com.madar.madardemo.Service.CallbackWithRetry;
import com.madar.madardemo.Service.Injector;
import com.madar.madardemo.Service.onRequestFailure;
import com.madar.madardemo.Util.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapLinkFragment extends AddOrderBaseFragment implements OnMapReadyCallback {

    String TAG = "MapLinkFragment" ;

    @BindView(R.id.progressView)
    View progrssView;
    @BindView(R.id.container)
    View containerHolder;
    private GoogleMap mMap;

    @BindView(R.id.map_container)
    View map_container ;

    public MapLinkFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.map_link_input)
    EditText map_link_input ;


    View v ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (v == null){

            v =  inflater.inflate(R.layout.fragment_map_link, container, false); ;
            ButterKnife.bind(this, v);
            initLoading(this.progrssView, this.containerHolder);
            setupMap();
//            map_link_input.addTextChangedListener(new MyTextWatcher(map_link_input) );
        }
        return v ;
    }


    private void setupMap() {

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map_link_map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        inti_map(mMap);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.setBuildingsEnabled(true);
        mMap.setIndoorEnabled(true);

    }


    private Marker currentPosIcon;
    private void createCurrentPosMarker(LatLng recipient_LatLng) {
        BitmapDrawable home =(BitmapDrawable) ContextCompat.getDrawable(getActivity(), R.drawable.location);

        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(home.getBitmap());

        MarkerOptions markerOptions = null;
        markerOptions = new MarkerOptions().position(recipient_LatLng).icon(icon);


        currentPosIcon = mMap.addMarker(markerOptions);
        showLoading(false);
    }

    @Override
    public void onPageSelected(int pos) {

    }


    private class MyTextWatcher implements TextWatcher {

        private View view;
        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.map_link_input:
                    show_map();
                    break;

            }
        }


    }

    private void show_map() {
        showLoading(true);
        if (Validation.isEditTextEmpty(map_link_input))
            return;
        Call<MapLinkFetchResponse> call  = Injector.Api().mapLinkFetch(map_link_input.getText().toString());
        call.enqueue(new CallbackWithRetry<MapLinkFetchResponse>(5, 30000, call, new onRequestFailure() {
            @Override
            public void onFailure() {
                showNoConn(new NoConn() {
                    @Override
                    public void onRetry() {
                        show_map();
                        showLoading(false);
                    }
                });
            }
        }) {
            @Override
            public void onResponse(Call<MapLinkFetchResponse> call, Response<MapLinkFetchResponse> response) {

                if (response.body().getResponseItem().isSuccessful()){
                    final MapLinkFetchResponse.LatLungModel latLungModel = response.body().getData() ;
                    final LatLng LatLng = new LatLng(latLungModel.getLatitude() , latLungModel.getLongitude()) ;
                   getAddressDetails(LatLng, new FetchLocation() {
                       @Override
                       public void onFinish(AddressDetailsResponse.AddressDetailsData data) {
                           Log.e(TAG, "latlung " + LatLng);
                           createCurrentPosMarker(LatLng);
                           moveCamera(LatLng , 10);
                           getOrderModel().setReciverMapCity(data.getCountry());
                           getOrderModel().setDeleviry_Place(latLungModel.getLatitude()+","+latLungModel.getLongitude());

                       }
                   });
                }else {
                    showLoading(false);
                }

            }
        });

    }

    @OnClick(R.id.search)
    void search_link(){
        show_map();
    }

}
