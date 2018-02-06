package com.madar.madardemo.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.madar.madardemo.Fragment.AddOrder.ShowLocationFragment;
import com.madar.madardemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ShowLocationActivity extends FragmentSwitchActivity {

    @BindView(R.id.activity_toolbar_title)
    TextView title ;



    @OnClick(R.id.back_btt)
    void back_btt(){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in ,  R.anim.fade_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_location);
        overridePendingTransition(R.anim.fade_in ,  R.anim.fade_out);
        ButterKnife.bind(this) ;
        String latlung= getIntent().getExtras().getString("latlung");
        ShowLocationFragment showLocationFragment = new ShowLocationFragment();
        showLocationFragment.setExtra_lat_lung(getLatLngFromString(latlung));
        showFragment(showLocationFragment , false);
    }

    public  static String create_lat_lung_string(LatLng latLung) {
        return latLung.latitude+","+latLung.longitude;
    }




    LatLng getLatLngFromString(String place){
        try {
            Log.e("subs" , place) ;
            Log.e("subs" , place.indexOf(",") +"" ) ;
            String lat  =    place.substring(0 , place.indexOf(","));
            String lung  = place.substring(place.indexOf(",")+1);
            return  new LatLng(Double.parseDouble(lat) ,Double.parseDouble( lung)) ;

        }catch (Exception e){
            return  new LatLng(0,0) ;
        }
    }


}
