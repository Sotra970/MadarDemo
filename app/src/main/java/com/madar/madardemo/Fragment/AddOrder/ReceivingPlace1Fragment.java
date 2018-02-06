package com.madar.madardemo.Fragment.AddOrder;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.madar.madardemo.AppManger.Config;
import com.madar.madardemo.Fragment.GetLocationFragment;
import com.madar.madardemo.Model.CityModel;
import com.madar.madardemo.Model.FavLocationModel;
import com.madar.madardemo.Model.ServerResponse.AddressDetailsResponse;
import com.madar.madardemo.R;
import com.madar.madardemo.Util.MapUtils.LatLngInterpolator;
import com.madar.madardemo.Util.MapUtils.markerAnimator;
import com.robohorse.pagerbullet.PagerBullet;

import butterknife.BindView;

/**
 * Created by Ahmed on 8/15/2017.
 */

public class ReceivingPlace1Fragment extends GetLocationFragment {

    String TAG  = "receiving_1" ;


    public static ReceivingPlace1Fragment getInstance() {
        return new ReceivingPlace1Fragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v = super.onCreateView(inflater, container, savedInstanceState);
        onPageSelected(0);
        return v ;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setFinishTitle(getString(R.string.next));
    }

    @Override
    public void onPageSelected(int pos) {
        callbacks = true;
//        setupPagerPullet(true ,pos);
        driver_extra_info_layout.setVisibility(View.VISIBLE);
    }

    boolean callbacks = true ;



    @BindView(R.id.driver_extra_info_input)
    EditText driver_extra_info_input  ;

    @BindView(R.id.driver_extra_info_layout)
    View driver_extra_info_layout  ;


    @Override
    protected void onFetchLocationFinish(final AddressDetailsResponse.AddressDetailsData data , final LatLng latLng) {
//        super.onFetchLocationFinish(data);
        if (!callbacks)
            return;

        Validate_city validate_city = new Validate_city() {
            @Override
            public void onDataFetch(CityModel cityModel) {
                if (cityModel == null)
                {
                    showNotSupportedAreaDialog();
                    return;
                }
                if (!cityModel.shippingFromEdibility()){
                    showNotSupportedAreaDialog();
                    return;
                }

                if (!TextUtils.isEmpty(driver_extra_info_input.getText().toString().trim())){
                    getOrderModel().setMore_Place_Details(driver_extra_info_input.getText().toString().trim());
                }


                getOrderModel().setReceiving_City_ID(cityModel.getID());
                getOrderModel().setFrom_City(Integer.parseInt(cityModel.getID()));
                getOrderModel().setReceiving_District(data.getAddress());
                getOrderModel().setRecipient_Place(latLng.latitude+","+latLng.longitude);
                getOrderModel().setRecipient_LAT(latLng.latitude);
                getOrderModel().setRecipient_LUNG(latLng.longitude);
                showLoading(false);
                getOnNextClick().next(getOrderModel());
                showLoading(false);
                callbacks = false ;
            }
        };
        get_city_id(data.getCountry() , validate_city);

    }

    @Override
    protected void mapDefaultMoveCameraAction(FavLocationModel favLocationModel , LatLng GpsLocationPos) {
        final LatLng currentLoc ;
        if (favLocationModel == null && GpsLocationPos!=null){
            currentLoc = GpsLocationPos ;
        }else if (favLocationModel != null){
              currentLoc = new LatLng(Double.parseDouble(favLocationModel.getLatitude()) ,Double.parseDouble(favLocationModel.getLongitude()));
        }else currentLoc=new LatLng(0d ,0d);
        new markerAnimator().animateMarkerToICS(currentPosIcon, currentLoc, new LatLngInterpolator.Spherical(), 1200);

        getAddressDetails(currentLoc ,  new FetchLocation() {
                @Override
                public void onFinish(AddressDetailsResponse.AddressDetailsData data) {

                    if (data!=null){
                        update_place_bind(currentLoc , data);
                        showLoading(false);
                        moveCamera(currentLoc);


                    }
                    else showLoading(false);

                }
            });
    }

    private void showNotSupportedAreaDialog() {
        showLoading(false);
        new  AlertDialog.Builder(getActivity())
                .setMessage(getString(R.string.city_out_of_range))
                .setNeutralButton(getString(R.string.accept_message), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }



}
