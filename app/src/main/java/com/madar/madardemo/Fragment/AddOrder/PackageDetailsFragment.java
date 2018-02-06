package com.madar.madardemo.Fragment.AddOrder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.madar.madardemo.Adapter.DetailsImgsAdapter;
import com.madar.madardemo.AppManger.MadarApplication;
import com.madar.madardemo.Fragment.Abstract.AddOrderBaseFragment;
import com.madar.madardemo.Model.OrderItem;
import com.madar.madardemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ahmed on 8/15/2017.
 */

public class PackageDetailsFragment extends AddOrderBaseFragment {

    @BindView(R.id.progressView)
    View progrssView ;
    @BindView(R.id.container)
    ScrollView containerHolder ;

    @BindView(R.id.sender_name_text)
    TextView sender_name_text ;
    @BindView(R.id.sender_address_text)
    TextView sender_address_text ;
    @BindView(R.id.sender_address_note_text)
    TextView sender_address_note_text ;

    @BindView(R.id.receiver_name_text)
    TextView receiver_name_text ;
    @BindView(R.id.receiver_address_text)
    TextView receiver_address_text ;

    @BindView(R.id.package_details_date)
    TextView package_details_date ;
    @BindView(R.id.package_details_time)
    TextView package_details_time ;
    @BindView(R.id.package_details_title)
    TextView package_details_title ;
    @BindView(R.id.package_details_notes)
    TextView package_details_notes ;

    // imgs
    @BindView(R.id.package_details_imgs_list_view)
    RecyclerView imgs_list_view ;


    @BindView(R.id.receiving_price)
    TextView receiving_price ;
    @BindView(R.id.receiving_payment_method)
    TextView receiving_payment_method ;
    @BindView(R.id.receiving_value)
    TextView receiving_value ;
    @BindView(R.id.receiving_total_price)
    TextView receiving_total_price ;


    @BindView(R.id.next)
    View next ;

    @BindView(R.id.next_text)
    TextView next_text ;

    @BindView(R.id.currency_txt)
    TextView currency_txt ;

    @BindView(R.id.currency_txt2)
    TextView currency_txt2 ;

    @BindView(R.id.currency_txt3)
    TextView currency_txt3 ;




    OrderItem orderItem ;

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }



    public static PackageDetailsFragment getInstance() {
        return new PackageDetailsFragment();
    }
    View v ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (v == null) {
            v = inflater.inflate(R.layout.fragment_package_details, container, false);
            ButterKnife.bind(this, v);
            initLoading(this.progrssView, this.containerHolder);

            currency_txt.setText(MadarApplication.getCurrency());

            try {
                bind_data(orderItem);
            }catch (Exception e){

            }


        }
        return v;
    }


    @Override
    public void onPageSelected(int pos) {
        setupPagerPullet(true , pos);
        bind_data(getAddOrderActivity().orderItem);
        next_text.setText(getString(R.string.order_confirm));
        next.setVisibility(View.VISIBLE);
    }






    protected  void bind_data(OrderItem orderItem){

         sender_name_text.setText(orderItem.getCustomer_Name()); ;
         sender_address_text.setText(orderItem.getFrom_District()); ;
        sender_address_note_text.setText(orderItem.getPlace_Detail()); ;

        currency_txt.setText(MadarApplication.getUser().getCurrency_Code());
        currency_txt2.setText(MadarApplication.getUser().getCurrency_Code());
        currency_txt3.setText(MadarApplication.getUser().getCurrency_Code());

         receiver_name_text.setText(orderItem.getReceiver_Name()); ;
         receiver_address_text.setText(orderItem.getTo_District()); ;

        String time =   getString(R.string.text_from) + orderItem.getTime_From()
                +"  " +
                getString(R.string.text_to) + orderItem.getTime_To() ;

         package_details_time.setText(
               time
         );
        orderItem.initDate();
        package_details_date.setText(orderItem.getParsedDate()); ;
        package_details_title.setText(orderItem.getTitle()); ;
        package_details_notes.setText(orderItem.getNote()); ;



         receiving_price.setText(orderItem.getShip_Price()); ;
         receiving_payment_method.setText(orderItem.getPayment_Method_Name()); ;


        receiving_value.setText(orderItem.getGood_Price()); ;

        double p1 = Double.parseDouble(orderItem.getShip_Price());
        double p2 = Double.parseDouble(orderItem.getGood_Price());
        double total = p1+p2 ;

        receiving_total_price.setText(total+"");

        DetailsImgsAdapter detailsImgsAdapter = new DetailsImgsAdapter(orderItem.getPics() , getContext()) ;
        imgs_list_view.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.HORIZONTAL , false));
        imgs_list_view.setAdapter(detailsImgsAdapter);

        containerHolder.scrollTo(0,0);
    }


    @OnClick(R.id.next)
    void next(){
        getAddOrderActivity().next(getOrderModel());
    }



    @OnClick(R.id.receiver_direction)
    void reciver_arrow(){
        ShowLocationFragment showLocationFragment = new ShowLocationFragment();
        showLocationFragment.setExtra_lat_lung(getLatLngFromString(orderItem.getRecipient_Place()));
        showFragment(showLocationFragment , true);
    }


    @OnClick(R.id.sender_direction)
    void sender_direction(){
        ShowLocationFragment showLocationFragment = new ShowLocationFragment();
        showLocationFragment.setExtra_lat_lung(getLatLngFromString(orderItem.getDeliver_Place()));
        showFragment(showLocationFragment , true);
    }

    @OnClick(R.id.sender_call)
    void sender_call(){
       try {
           String phone  = orderItem.getSender_Num() ;
           if (phone != null){
               Uri call = Uri.parse("tel:" + phone);
               Intent surf = new Intent(Intent.ACTION_DIAL, call);
               startActivity(surf);
           }
       }catch (Exception e){}
    }



    @OnClick(R.id.receiver_call)
    void receiver_call(){
      try {
          String phone  = orderItem.getMobile_Num() ;
          if (phone != null){
              Uri call = Uri.parse("tel:" + phone);
              Intent surf = new Intent(Intent.ACTION_DIAL, call);
              startActivity(surf);
          }
      }catch (Exception e){}
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
