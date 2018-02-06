package com.madar.madardemo.Fragment;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.madar.madardemo.Activity.FragmentSwitchActivity;
import com.madar.madardemo.AppManger.Config;
import com.madar.madardemo.AppManger.MadarApplication;
import com.madar.madardemo.Fragment.Abstract.AddOrderBaseFragment;
import com.madar.madardemo.Interface.NoConn;
import com.madar.madardemo.Model.FavLocationModel;
import com.madar.madardemo.Model.ServerResponse.AddressDetailsResponse;
import com.madar.madardemo.Model.ServerResponse.FavLocationResponse;
import com.madar.madardemo.R;
import com.madar.madardemo.Service.CallbackWithRetry;
import com.madar.madardemo.Service.Injector;
import com.madar.madardemo.Service.onRequestFailure;
import com.madar.madardemo.Util.MapUtils.LatLngInterpolator;
import com.madar.madardemo.Util.MapUtils.markerAnimator;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Ahmed on 8/15/2017.
 */

public abstract class GetLocationWithPlaceSearchFragment extends AddOrderBaseFragment implements OnMapReadyCallback  {

    @BindView(R.id.progressView)
    View progrssView;
    @BindView(R.id.container)
    View containerHolder;
//    @BindView(R.id.fav)
//    ImageView fav_icon;

    @BindView(R.id.address_text)
    TextView address_text;

    private GoogleMap mMap;

    private Marker currentPosIcon;
    private Drawable drawable;
    ImageButton current;
    private boolean manual = false;
    private boolean map_load  = false;

    String TAG  = "GetLocationFragment" ;
//    GetLocationInterface getLocationInterface ;





    View v;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (v == null) {
            v = inflater.inflate(R.layout.fragment_get_location_with_place_search, container, false);
            ButterKnife.bind(this, v);
            initLoading(this.progrssView, this.containerHolder);
            showLoading(true);

            current = (ImageButton) v.findViewById(R.id.current_location);
            drawable = ContextCompat.getDrawable(getActivity(), R.drawable.ic_my_location_black_36dp);
            getActivity().registerReceiver(gpsProviderReciver, new IntentFilter("android.location.PROVIDERS_CHANGED"));
            checkGpsProvider();
            setupMap();

            setupCurrentPostion();

        }

        return v;
    }


    @Override
    protected void onCurrentLocationUpdated(Location location) {
        super.onCurrentLocationUpdated(location);

        try {
            if (location.getLatitude() != 0) {
               final LatLng currentLoc = new LatLng(location.getLatitude(), location.getLongitude());
                //currentPosIcon.setRotation((float) SphericalUtil.computeHeading(currentPosIcon.getPosition(),currentLoc));


                drawable.setColorFilter(ContextCompat.getColor(getActivity(), R.color.blue), PorterDuff.Mode.SRC_ATOP);
                current.setImageDrawable(drawable);

                if (!manual) {
                    new markerAnimator().animateMarkerToICS(currentPosIcon, currentLoc, new LatLngInterpolator.Spherical(), 1200);
//                moveCamera(currentLoc);
//                    update_place_req();
                }

                // // TODO: 10/17/2017  request default
                // bind instead of currentLoc

                if (!map_load)
                getDefaultFav();



            } else {
                drawable.clearColorFilter();
                current.setImageDrawable(drawable);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void getDefaultFav() {
        map_load = true ;
        get_favs(new FavFetch() {
            @Override
            public void onFavFetch(ArrayList<FavLocationModel> favs) {
                if (favs !=null && !favs.isEmpty())
                    for (FavLocationModel favLocationModel : favs){
                    if (favLocationModel.getDefault() == 1){
                        mapDefaultMoveCameraAction(favLocationModel ,new LatLng( gpsTracker.getLatitude() , gpsTracker.getLongitude()));
                    return;
                    }
                }
                mapDefaultMoveCameraAction(null,new LatLng( gpsTracker.getLatitude() , gpsTracker.getLongitude()));

            }
        },false);
    }

    private void setupMap() {

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        inti_map(mMap);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.setBuildingsEnabled(true);
        mMap.setIndoorEnabled(true);



        if (gpsTracker.getLatitude() !=-0 ){
            moveCamera(new LatLng(gpsTracker.getLongitude(),gpsTracker.getLatitude()));
        }
        createCurrentPosMarker();




        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                try{
                    Log.e("latlung",latLng.toString());
                    manual =true ;
                    new markerAnimator().animateMarkerToICS(currentPosIcon, latLng, new LatLngInterpolator.Spherical(), 1200);
                    moveCamera(latLng);
//                    update_place_req(latLng) ;
                }catch (Exception e){}
            }
        });

    }
//
//    private void update_place_req(final LatLng latLng) {
//        address_text.setText(getString(R.string.wait));
//        getAddressDetails( latLng, new FetchLocation() {
//            @Override
//            public void onFinish(AddressDetailsResponse.AddressDetailsData data) {
//                update_place_bind(latLng , data);
//            }
//        });
//
//    }

//    protected void update_place_bind(LatLng postion, final AddressDetailsResponse.AddressDetailsData data) {
//      try {
//          address_text.setText(getString(R.string.wait));
//          favLocationModel.setDetails(data.getAddress());
//          favLocationModel.setDistrict(data.getCountry());
//          favLocationModel.setLatitude(String.valueOf(postion.latitude));
//          favLocationModel.setLongitude(String.valueOf(postion.longitude));
//          getFavPos(favLocationModel, new FavPosListener() {
//              @Override
//              public void onFetch(boolean exist) {
////                  update_add_fav_icon(exist);
////                  address_text.setText(data.getAddress());
//              }
//          });
//
//
//
//      }catch (Exception e){}
//
//
//
//    }
//
//    boolean fav_exist = true ;
//    private void update_add_fav_icon(boolean exist) {
//       try {
//           fav_exist  = exist;
//           Log.e("fav_exist" , fav_exist +"") ;
//           if (exist){
//               fav_icon.setColorFilter(ContextCompat.getColor(getContext() , R.color.app_yellow_1) , PorterDuff.Mode.SRC_ATOP);
//           }else{
//               fav_icon.clearColorFilter();
//           }
//       }catch (Exception e){}
//    }


    private void createCurrentPosMarker() {
        BitmapDrawable home =(BitmapDrawable) ContextCompat.getDrawable(getActivity(), R.drawable.location);
//        home.setColorFilter(ContextCompat.getColor(getActivity(),R.color.colorPrimaryDarkd), PorterDuff.Mode.SRC_ATOP);

        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(home.getBitmap());

        MarkerOptions markerOptions = null;
        markerOptions = new MarkerOptions().position(new LatLng(gpsTracker.getLatitude(),gpsTracker.getLongitude())).icon(icon);


        currentPosIcon = mMap.addMarker(markerOptions);
    }





    private void setupCurrentPostion() {
        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manual = false;
                if (gpsTracker.getLatitude() != 0) {
                    LatLng currentLoc = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
                    new markerAnimator().animateMarkerToICS(currentPosIcon, currentLoc, new LatLngInterpolator.Spherical(), 1200);

                    drawable.setColorFilter(ContextCompat.getColor(getActivity(), R.color.blue), PorterDuff.Mode.SRC_ATOP);
                    current.setImageDrawable(drawable);
                    moveCamera(currentLoc);
                } else {
                    drawable.clearColorFilter();
                    current.setImageDrawable(drawable);
                }
            }
        });
    }


    @OnClick(R.id.next)
    void next_click(){
    Log.e(TAG , "loading");
        showLoading(true);

        final  LatLng latLng = currentPosIcon.getPosition() ;
        getAddressDetails(currentPosIcon.getPosition(), new FetchLocation() {
            @Override
            public void onFinish(AddressDetailsResponse.AddressDetailsData data) {

                if (data!=null){
                    onFetchLocationFinish(data , latLng);
                }
            }
        });

//
    }

   protected void onFetchLocationFinish(AddressDetailsResponse.AddressDetailsData data , LatLng latLng){

    }




    @Override
    public void onStop() {
        super.onStop();
        try {
            getActivity().unregisterReceiver(gpsProviderReciver);
//            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver   );
        }catch (Exception e){}
    }

    @Override
    public void onResume() {
        Log.e(TAG  , "on rsume");
        getActivity().registerReceiver(gpsProviderReciver, new IntentFilter("android.location.PROVIDERS_CHANGED"));
        super.onResume();
    }


    @Override
    public void onPause() {
        Log.e(TAG  , "on pause");
        super.onPause();
    }
//
    @OnClick(R.id.search)
    void search_locations (){
        setupPlaceBar();
    }


    @OnClick(R.id.address_text)
    void show_locations(){
        setupPlaceBar();
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            gpsTracker.disconnect();
        }catch (Exception e){}
    }


    @BindView(R.id.next_text)
    TextView next_text ;

    protected  void setFinishTitle(String title){
        next_text.setText(title);
    }

//    FavLocationModel  favLocationModel = new FavLocationModel();

    public void onFavClicked(Place place) {
        manual =true ;
//        this.favLocationModel = favLocationModel ;
//        address_text.setText(favLocationModel.getDetails());
//        LatLng currentLoc = new LatLng(Double.parseDouble(favLocationModel.getLatitude() ), Double.parseDouble(favLocationModel.getLongitude())) ;
        Log.e("atlungplace" , place.getLatLng()+"");
        address_text.setText(place.getAddress());
        new markerAnimator().animateMarkerToICS(currentPosIcon, place.getLatLng(), new LatLngInterpolator.Spherical(), 1200);
        moveCamera(place.getLatLng());
//        update_add_fav_icon(true);
    }


    void getFavPos(final FavLocationModel favLocationModel , final FavPosListener favPosListener  ){

        get_favs(new FavFetch() {
            @Override
            public void onFavFetch(ArrayList<FavLocationModel> favs) {
                if (favs == null){
                    Log.e("fav_loc_comp" , "null") ;
                    favPosListener.onFetch(false);
                    return;
                }
                if (favs.isEmpty()){
                    Log.e("fav_loc_comp" , "isEmpty") ;
                    favPosListener.onFetch(false);
                    return;
                }
                for (FavLocationModel child : favs){
                    Log.e("fav_loc_comp" , child.getLongitude()+"-"+favLocationModel.getLongitude() +"-"+ child.getLatitude()+"-"+favLocationModel.getLatitude());
                    Log.e("fav_loc_comp" , child.getDetails() +"\n" +favLocationModel.getDetails());
                    if (compareAr(child.getDetails() , favLocationModel.getDetails()) ==0){
                        Log.e("fav_loc_comp" , "exist") ;
                        favPosListener.onFetch(true);
                        return;
                    }
                }
                Log.e("fav_loc_comp" , "404") ;
                favPosListener.onFetch(false);
                return;
            }
        } , false);

    }


    public  interface FavPosListener{
        void onFetch(boolean pos);
    }


//    @OnClick(R.id.fav)
//    void addd_fav_address(){
//        if (fav_exist)
//            return;
//        if (favLocationModel == null)
//            return;
//        if (favLocationModel.getDetails() == null)
//            return;
//
//        favLocationModel.setSecure(MadarApplication.getInstance().getPrefManager().getUser().getSecret());
//
//        if (TextUtils.isEmpty(favLocationModel.getDetails())) return;
//        showLoading(true);
//        Call<FavLocationResponse>  call = Injector.Api().AddUserFavsLocation(favLocationModel);
//        call.enqueue(new CallbackWithRetry<FavLocationResponse>(5, 3000, call, new onRequestFailure() {
//            @Override
//            public void onFailure() {
//                showNoConn(new NoConn() {
//                    @Override
//                    public void onRetry() {
//                        addd_fav_address();
//
//                    }
//                });
//                showLoading(false);
//            }
//        }) {
//            @Override
//            public void onResponse(Call<FavLocationResponse> call, final Response<FavLocationResponse> response) {
//                get_favs(new FavFetch() {
//                    @Override
//                    public void onFavFetch(ArrayList<FavLocationModel> favs) {
//
//                        if (response.body().getResponseItem().isSuccessful()){
//                            update_add_fav_icon(true);
//                        }
//
//                        showLoading(false);
//                    }
//                }, true);
//            }
//        });
//    }

//    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            FavLocationModel favLocationModel = (FavLocationModel) intent.getExtras().get("fav");
//            if (favLocationModel != null)
//                if (!TextUtils.isEmpty(favLocationModel.getLatitude()))
//                if (!TextUtils.isEmpty(favLocationModel.getLatitude()))
//                onFavClicked(favLocationModel);
//        }
//    };
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver , new IntentFilter(Config.FAV_UPDATE_ACTION));
//    }



    protected abstract  void mapDefaultMoveCameraAction(FavLocationModel favLocationModel , LatLng currentGpsLocation) ;

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;


    private void setupPlaceBar() {
//       PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
//                getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment1);
//            autocompleteFragment.setHint(getString(R.string.search_place));
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(final Place place) {
//                // TODO: Get info about the selected place.
//                Log.i("pacebar", "Place: " + place.getName());
//
//                onFavClicked(place);
//            }
//
//
//            @Override
//            public void onError(Status status) {
//                // TODO: Handle the error.
//                Log.i("placebar", "An error occurred: " + status);
//            }
//        });


        TelephonyManager tm = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        String locale = tm.getNetworkCountryIso();
        Log.e(TAG, "getCountry: " + locale);

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .setCountry(locale)
                .build();

        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .setFilter(typeFilter)
                            .build(getActivity());
            getActivity().startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
//
//        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
//                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
//                .setCountry("EG")
//                .setCountry("SA")
//                .build();

//        autocompleteFragment.setFilter(typeFilter);

    }



}
