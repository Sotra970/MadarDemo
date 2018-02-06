package com.madar.madardemo.Fragment.AddOrder.ReciverInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.madar.madardemo.Activity.ReciverInfoGetLocationActivity;
import com.madar.madardemo.AppManger.Config;
import com.madar.madardemo.Fragment.GetLocationFragment;
import com.madar.madardemo.Fragment.GetLocationWithPlaceSearchFragment;
import com.madar.madardemo.Model.FavLocationModel;
import com.madar.madardemo.Model.ServerResponse.AddressDetailsResponse;
import com.madar.madardemo.R;

/**
 * Created by sotra on 10/8/2017.
 */

public class PagerMapLocationFull  extends GetLocationWithPlaceSearchFragment implements ReciverInfoGetLocationActivity.PlaceFetch {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setFinishTitle(getString(R.string.finish));
    }

    boolean callbacks = true ;


    @Override
    protected void onFetchLocationFinish(AddressDetailsResponse.AddressDetailsData data , LatLng latLng) {
//        super.onFetchLocationFinish(data);
        if (!callbacks)
            return;
        getOrderModel().setReciverMapCity(data.getCountry());
//        getOrderModel().setTo_City(Integer.parseInt(get_city_id(data.getCountry())));
//        getOrderModel().setDistrict(data.getAddress());
        getOrderModel().setDeleviry_Place(latLng.latitude+","+latLng.longitude);
        getOrderModel().setDeleviry_LAT(latLng.latitude);
        getOrderModel().setDeleviry_LUNG(latLng.longitude);

        Intent intent = new Intent(Config.REFRESH_Receive_info_MAp);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);


        showLoading(false);
        getActivity().onBackPressed();
        callbacks = false ;
    }

    @Override
    protected void mapDefaultMoveCameraAction(FavLocationModel favLocationModel, final  LatLng currentLoc) {

        getAddressDetails(currentLoc ,  new FetchLocation() {
            @Override
            public void onFinish(AddressDetailsResponse.AddressDetailsData data) {

                if (data!=null){
//                    update_place_bind(currentLoc , data);
                    showLoading(false);
                    moveCamera(currentLoc);
                }
                else showLoading(false);

            }
        });
    }


    @Override
    public void onPageSelected(int pos) {

    }

    public ReciverInfoGetLocationActivity.PlaceFetch getPlaceFetch() {
        return this;
    }

    @Override
    public void onplaceFetch(Place place) {
        onFavClicked(place);
    }
}
