package com.madar.madardemo.Fragment.Abstract;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.madar.madardemo.Activity.AddOrderActivity;
import com.madar.madardemo.AppManger.Config;
import com.madar.madardemo.AppManger.MadarApplication;
import com.madar.madardemo.Interface.NoConn;
import com.madar.madardemo.Interface.OnNextClick;
import com.madar.madardemo.Model.CityModel;
import com.madar.madardemo.Model.FavLocationModel;
import com.madar.madardemo.Model.OrderModel;
import com.madar.madardemo.Model.ServerResponse.AddressDetailsResponse;
import com.madar.madardemo.Model.ServerResponse.ConfirmationResponse;
import com.madar.madardemo.Model.ServerResponse.FavLocationResponse;
import com.madar.madardemo.R;
import com.madar.madardemo.Service.CallbackWithRetry;
import com.madar.madardemo.Service.Injector;
import com.madar.madardemo.Service.onRequestFailure;
import com.madar.madardemo.Util.ArabicNormalizer;
import com.madar.madardemo.Util.MapUtils.LocationReq;
import com.robohorse.pagerbullet.PagerBullet;

import java.io.Serializable;
import java.text.Collator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by sotra on 9/29/2017.
 */

public abstract class AddOrderBaseFragment  extends BaseFragment implements Serializable {




    protected  LocationReq gpsTracker;
//    protected LatLng cuurent_location;
    private GoogleMap mMap;



    protected void inti_map(  GoogleMap mMap){
        this.mMap = mMap;
        gpsTracker = new LocationReq(getActivity(), new LocationReq.update_location() {
            @Override
            public void update_location(Location location) {
                onCurrentLocationUpdated(location);
            }
        }, 15000);
    }

    protected  void onCurrentLocationUpdated(Location location){

    }
    protected AddOrderActivity getAddOrderActivity(){
        return (AddOrderActivity) getActivity();
    }

    protected OnNextClick getOnNextClick(){
        return getAddOrderActivity();
    }

    protected OrderModel getOrderModel() {
        return AddOrderActivity.orderModel;
    }

    protected void setOrderModel(OrderModel orderModel) {
       getAddOrderActivity().orderModel = orderModel;
    }



    boolean camera_move = false ;

    protected void moveCamera(final LatLng current) {
        if (!camera_move){
            camera_move = true ;
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(current)      // Sets the center of the map to Mountain View
                    .zoom(16)                   // Sets the zoom
//                    .bearing(90)                // Sets the orientation of the camera to east
//                    .tilt(90)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 3000, new GoogleMap.CancelableCallback() {
                @Override
                public void onFinish() {
                    camera_move = false;
                }

                @Override
                public void onCancel() {
                    camera_move = false;
                }
            });
        }
    }

    protected void forcemoveCamera(final LatLng current , int zoom) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(current)      // Sets the center of the map to Mountain View
                    .zoom(zoom)                   // Sets the zoom
//                    .bearing(90)                // Sets the orientation of the camera to east
//                    .tilt(90)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 3000, new GoogleMap.CancelableCallback() {
                @Override
                public void onFinish() {

                }

                @Override
                public void onCancel() {

                }
            });
    }


    protected void moveCamera(final LatLng current , int zoom) {
        if (!camera_move){
            camera_move = true ;
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(current)      // Sets the center of the map to Mountain View
                    .zoom(zoom)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(90)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 3000, new GoogleMap.CancelableCallback() {
                @Override
                public void onFinish() {
                    camera_move = false;
                }

                @Override
                public void onCancel() {
                    camera_move = false;
                }
            });
        }
    }





    protected final BroadcastReceiver gpsProviderReciver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().matches("android.location.PROVIDERS_CHANGED"))
            {
                Log.e("GpsProviderReciver", String.valueOf(intent.getAction()));
                checkGpsProvider();
            }
        }
    };
    protected   void  checkGpsProvider(){
        final LocationManager manager = (LocationManager) getActivity().getSystemService( Context.LOCATION_SERVICE );
        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getString(R.string.no_gps_per))
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();        }
    }




    abstract public   void onPageSelected(int pos);


   protected void delete_fav(final String fav_id , final DeleteFav deleteFav){
        Call<ConfirmationResponse> call = Injector.Api().deleteFav(
                MadarApplication.getInstance().getPrefManager().getUser().getSecret(),
                fav_id
        );
       call.enqueue(new CallbackWithRetry<ConfirmationResponse>(5, 3000, call, new onRequestFailure() {
           @Override
           public void onFailure() {
               showNoConn(new NoConn() {
                   @Override
                   public void onRetry() {
                       delete_fav(fav_id , deleteFav);
                   }
               });
               showLoading(false);
           }
       }) {
           @Override
           public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {
               if (response.body().getResponseItem().isSuccessful()){
                   deleteFav.onFavDelete();
               }
               showLoading(false);
           }
       });
    }


   public  void get_favs( FavFetch favFetch , boolean reload){
        if (!favs.isEmpty() && !reload){
            if (favFetch!=null)
            favFetch.onFavFetch(favs);
        }else{
            favs_req(favFetch) ;
        }
    }

    static  ArrayList<FavLocationModel> favs = new ArrayList() ;
    void favs_req(final FavFetch favFetch){


        Call<FavLocationResponse> call  = Injector.Api().getUserFavsLocation(
                MadarApplication.getInstance().getPrefManager().getUser().getSecret()
        );

        call.enqueue(new CallbackWithRetry<FavLocationResponse>(5, 3000, call, new onRequestFailure() {
            @Override
            public void onFailure() {
                showNoConn(new NoConn() {
                    @Override
                    public void onRetry() {
                        favs_req(favFetch);
                    }
                });
            }
        }) {
            @Override
            public void onResponse(Call<FavLocationResponse> call, Response<FavLocationResponse> response) {

                if (response.body().getResponseItem() !=null){
                    int code =response.body().getResponseItem().getStatusCode() ;
                    if (code == 200){
                        if (!response.body().getData().isEmpty()){
                            favs = response.body().getData() ;
                            if (favFetch!=null)
                                favFetch.onFavFetch(response.body().getData());
                            return;
                        }

                    }
                    favFetch.onFavFetch(null);
                }


            }
        });

    }



    public  interface  FavFetch{
        void onFavFetch(  ArrayList<FavLocationModel> favs);
    }

    public  interface  DeleteFav{
        void onFavDelete();
    }






    Validate_city validate_city ;
    String city_name ;

    protected    void   get_city_id(String city , final Validate_city validate_city){
        city_name = city ;
        this.validate_city = validate_city ;
        get_cities(new CityDataFetch() {
            @Override
            public void onDataFetch(ArrayList<CityModel> av_cities) {

                for (CityModel cityModel : av_cities){
                    if (compareAr(cityModel.getName() , city_name) == 0 ){
                        Log.e("get_city_id" , "match") ;

                        validate_city.onDataFetch(cityModel);
                        return;
                    }
                }
                validate_city.onDataFetch(null);
            }
        });
    }

    public interface Validate_city {
        void onDataFetch(CityModel cityModel);
    }


    public int  compareAr(String s1 , String s2){
        String new_s1 =   s1.replaceAll("\\s+","");
        String new_s2 =   s2.replaceAll("\\s+","");
//        String new_s2 = new ArabicNormalizer(s2).getOutput() ;
//        String new_s1 = new ArabicNormalizer(s1).getOutput() ;
        Log.e("get_city_id" ,new_s1 +"-" + new_s2) ;

        if (new_s2.contains(new_s1)  || new_s2.equals(new_s1)) return 0 ;


//        try {
//            Collator collator = Collator.getInstance(new Locale("ar"));
//            collator.setStrength(Collator.SECONDARY);
//            Log.e("get_city_id" ,new_s1 +"-" + new_s2) ;
//            return  collator.compare(new_s1, new_s2)  ;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return -1 ;
    }




    public void getAddressDetails(final LatLng latLng  , final FetchLocation fetchLocation){
        Call<AddressDetailsResponse> call = Injector.Api().getAddressDetails(
                latLng.latitude+","+latLng.longitude
        ) ;
        call.enqueue(new CallbackWithRetry<AddressDetailsResponse>(3, 3000, call, new onRequestFailure() {
            @Override
            public void onFailure() {
                showNoConn(new NoConn() {
                    @Override
                    public void onRetry() {
                        getAddressDetails(latLng , fetchLocation);
                    }
                });
            }
        }) {
            @Override
            public void onResponse(Call<AddressDetailsResponse> call, Response<AddressDetailsResponse> response) {

                if (response.body().getResponseItem() != null){
                    if (response.body().getResponseItem().getStatusCode() == 200){
                        fetchLocation.onFinish(response.body().getData());
                    }else showLoading(false);
                }
            }
        });

    }

    public interface  FetchLocation{
        void onFinish(AddressDetailsResponse.AddressDetailsData data);
    }



    protected void  setupPagerPullet( boolean visible  , int selection){
      try {
          PagerBullet pagerBullet = (PagerBullet) getView().findViewById(R.id.pagerBullet);
          pagerBullet.setIndicatorTintColorScheme(ContextCompat.getColor(getContext(),R.color.app_yellow_1) , ContextCompat.getColor(getContext(),R.color.grey_300));
          FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
              @Override
              public Fragment getItem(int position) {
                  return new Fragment();
              }

              @Override
              public int getCount() {
                  return Config.pages_count;
              }
          };

          pagerBullet.setAdapter(fragmentPagerAdapter);
          pagerBullet.setVisibility(visible?View.VISIBLE : View.GONE);
          pagerBullet.setCurrentItem(selection);
      }catch (Exception e){}
    }



}
