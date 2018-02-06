package com.madar.madardemo.Fragment.AddOrder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madar.madardemo.Fragment.Abstract.AddOrderBaseFragment;
import com.madar.madardemo.Interface.NoConn;
import com.madar.madardemo.Model.ServerResponse.AvDatesResponse;
import com.madar.madardemo.R;
import com.madar.madardemo.Service.CallbackWithRetry;
import com.madar.madardemo.Service.Injector;
import com.madar.madardemo.Service.onRequestFailure;
import com.madar.madardemo.Util.TimeUtils;
import com.madar.madardemo.ViewHolder.DayTabTitleHolder;
import com.madar.madardemo.ViewHolder.TimesTabTitleHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Ahmed on 8/15/2017.
 */

public class ReceivingDateTimeFragment extends AddOrderBaseFragment {

    @BindView(R.id.progressView)
    View progrssView ;
    @BindView(R.id.container)
    View containerHolder ;




    public static ReceivingDateTimeFragment getInstance() {
        return new ReceivingDateTimeFragment();
    }
    View v ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (v == null) {
            v = inflater.inflate(R.layout.fragment_reciving_date_time, container, false);
            ButterKnife.bind(this, v);
            initLoading(this.progrssView, this.containerHolder);
            showLoading(true);
        }


        return v;
    }


    @BindView(R.id.reciving_date_tab_layout)
    TabLayout daysTab ;
    @BindView(R.id.reciving_time_tab_layout)
    TabLayout TimesTab ;

    ArrayList<DayTabTitleHolder> dayHolders = new ArrayList<>() ;
    ArrayList<TimesTabTitleHolder> timeHolders = new ArrayList<>() ;
    ArrayList<AvDatesResponse.AvDatesResponseDataModel> dayModels = new ArrayList<>() ;
    ArrayList<AvDatesResponse.AvDatesDayModel> timesModels = new ArrayList<>() ;

    int first = 0 ;
    @Override
    public void onPageSelected(int pos) {
        setupPagerPullet(true , pos);
        initLoading(this.progrssView , this.containerHolder);
        if (dayModels.isEmpty()){
             first = 0 ;
        onDaySelectedSetup();
        onTimeSelectedSetup();
        getAddressDetails(new FetchDates() {
            @Override
            public void onFinish(ArrayList<AvDatesResponse.AvDatesResponseDataModel> data) {
                dayModels = data ;
                dayHolders = new ArrayList<>() ;
                daysTab.removeAllTabs();
                TimesTab.removeAllTabs();
                for (AvDatesResponse.AvDatesResponseDataModel child  :  data){
                    DayTabTitleHolder dayTabTitleHolder = new DayTabTitleHolder(child , getContext());
                    dayHolders.add(dayTabTitleHolder) ;
                    daysTab.addTab(daysTab.newTab().setCustomView(dayTabTitleHolder.getView()));
                    showLoading(false);

                }
            }
        });
        }

    }


    void onDaySelectedSetup(){
        daysTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition() ;
                first=0;

                DayTabTitleHolder dayTabTitleHolder = dayHolders.get(pos) ;
                dayTabTitleHolder.select();
                update_times(dayModels.get(pos).data);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                DayTabTitleHolder dayTabTitleHolder = dayHolders.get(tab.getPosition()) ;
                dayTabTitleHolder.unSelect();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    AvDatesResponse.AvDatesDayModel choosesDayModel ;
    void onTimeSelectedSetup(){
        TimesTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                showLoading(true);
                int pos = tab.getPosition() ;

                TimesTabTitleHolder  timesTabTitleHolder = timeHolders.get(pos) ;
               try {
                   String date_txt = timesTabTitleHolder.dayModel.Day_Date_DB+" "+timesTabTitleHolder.dayModel.To ;
                   Date datte  = TimeUtils.getDate(date_txt , TimeUtils.FORMAT_DATE_WITH_TIME_FULL);
                   if (datte==null){
                       showLoading(false);
                       return;
                   }

                   Calendar calendar = Calendar.getInstance() ;

                   SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TimeUtils.FORMAT_DATE_WITH_TIME_FULL , Locale.ENGLISH) ;


                   Log.e("current_date" ,                   simpleDateFormat.format( calendar.getTime()) ) ;
                   showLoading(false);
                   if ( calendar.getTimeInMillis() > datte.getTime() ){
                       choosesDayModel = null ;

                     if (first ==0){
                         first++ ;
                         new Handler().postDelayed(new Runnable() {
                             @Override
                             public void run() {
                                 getAvailableNextDate(true);

                             }
                         } , 1000);
                     }else{
                         getAvailableNextDate(false);
                         showDialog();
                     }

                   }else{
                       timesTabTitleHolder.select();
                       choosesDayModel = timesTabTitleHolder.dayModel ;
                   }
               }catch (Exception e){
                   showLoading(false);
               }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int pos = tab.getPosition() ;
                TimesTabTitleHolder  timesTabTitleHolder = timeHolders.get(pos) ;
                timesTabTitleHolder.unSelect();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void getAvailableNextDate(boolean showDialog) {
        showLoading(true);
        int i= -1 ;
        for (TimesTabTitleHolder timesTabTitleHolder : timeHolders){
            i++ ;
            try {
                String date_txt = timesTabTitleHolder.dayModel.Day_Date_DB+" "+timesTabTitleHolder.dayModel.To ;
                Date datte  = TimeUtils.getDate(date_txt , TimeUtils.FORMAT_DATE_WITH_TIME_FULL);
                Calendar calendar = Calendar.getInstance() ;

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TimeUtils.FORMAT_DATE_WITH_TIME_FULL , Locale.ENGLISH) ;


                Log.e("current_date" ,                   simpleDateFormat.format( calendar.getTime()) ) ;
                if ( calendar.getTimeInMillis() < datte.getTime() ){
                    timesTabTitleHolder.select();
                    TimesTab.getTabAt(i).select();

                    choosesDayModel = timesTabTitleHolder.dayModel ;
                    showLoading(false);
                    return;
                }
            }catch (Exception e){

            }
        }
        if (showDialog)
        showDialog();
        showLoading(false);
    }

    private void showDialog() {
        new AlertDialog.Builder(getActivity()).setMessage(getString(R.string.time_exceeded))
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
    }

    private void update_times(ArrayList<AvDatesResponse.AvDatesDayModel> data) {
        timesModels = data ;
        TimesTab.removeAllTabs();
        timeHolders.clear();
//        data.add(0,new AvDatesResponse.AvDatesDayModel () );
        for (AvDatesResponse.AvDatesDayModel child : data){
            TimesTabTitleHolder timesTabTitleHolder = new TimesTabTitleHolder(child , getContext());
            timeHolders.add(timesTabTitleHolder) ;
            TimesTab.addTab(TimesTab.newTab().setCustomView(timesTabTitleHolder.getView()));
        }   
    }


    void getAddressDetails(final FetchDates fetchDates ){

        Call<AvDatesResponse> call = Injector.Api().getAvailableDates();
        call.enqueue(new CallbackWithRetry<AvDatesResponse>(3, 30000, call, new onRequestFailure() {
            @Override
            public void onFailure() {
                showNoConn(new NoConn() {
                    @Override
                    public void onRetry() {
                        getAddressDetails(fetchDates);
                    }
                });
            }
        }) {
            @Override
            public void onResponse(Call<AvDatesResponse> call, Response<AvDatesResponse> response) {

                if (response.body().responseItem.getStatusCode() == 200){
                    fetchDates.onFinish(response.body().data);
                }else showLoading(false);
            }
        });

    }

    interface  FetchDates{
        void onFinish(ArrayList<AvDatesResponse.AvDatesResponseDataModel> data);
    }




    @OnClick(R.id.next)
    void next(){

        if (choosesDayModel == null){
            new AlertDialog.Builder(getActivity()).setMessage(getString(R.string.plschoose_date))
            .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).create().show();
            return;
        }
        getOrderModel().setDate(choosesDayModel.Day_Date_DB);
        getOrderModel().setTime_From(choosesDayModel.From);
        getOrderModel().setTime_To(choosesDayModel.To);
//        showLoading(true);



        getOnNextClick().next(getOrderModel());
    }


    @OnClick(R.id.cancel)
    void cancel_order(){
        getAddOrderActivity().backClick();
    }


    public void showLoading(final boolean show) {
            containerHolder.setVisibility(show ? View.GONE : View.VISIBLE);
            progrssView.setVisibility(show ? View.VISIBLE : View.GONE);
    }


}
