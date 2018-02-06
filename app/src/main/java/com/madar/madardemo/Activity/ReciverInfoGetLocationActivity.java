package com.madar.madardemo.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.madar.madardemo.Fragment.AddOrder.ReciverInfo.PagerMapLocationFull;
import com.madar.madardemo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class ReciverInfoGetLocationActivity extends FragmentSwitchActivity {

    PlaceFetch placeFetch ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_info_get_location);
        overridePendingTransition(R.anim.activity_slide_up , R.anim.activity_slide_down);
        ButterKnife.bind(this);
        PagerMapLocationFull pagerMapLocationFull = new PagerMapLocationFull() ;
        placeFetch =  pagerMapLocationFull.getPlaceFetch();
        showFragment(pagerMapLocationFull, false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_slide_up , R.anim.activity_slide_down);
    }
    @OnClick(R.id.back_btt)
    void back(){
        onBackPressed();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                if (placeFetch!=null)
                placeFetch.onplaceFetch(place);
                Log.e(TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.e(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    public  interface PlaceFetch{
        void onplaceFetch (Place place);
    }
}
