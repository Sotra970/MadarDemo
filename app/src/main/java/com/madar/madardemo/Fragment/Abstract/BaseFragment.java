package com.madar.madardemo.Fragment.Abstract;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.madar.madardemo.Activity.FragmentSwitchActivity;
import com.madar.madardemo.Activity.MainActivity;
import com.madar.madardemo.Fragment.ConnectionFailedFragment;
import com.madar.madardemo.Interface.NoConn;
import com.madar.madardemo.Model.CityModel;
import com.madar.madardemo.Model.CountryModel;
import com.madar.madardemo.Model.ServerResponse.CityListResponse;
import com.madar.madardemo.Model.ServerResponse.CountryListResponse;
import com.madar.madardemo.R;
import com.madar.madardemo.Service.CallbackWithRetry;
import com.madar.madardemo.Service.Injector;
import com.madar.madardemo.Service.onRequestFailure;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Ahmed on 8/16/2017.
 */

public abstract class BaseFragment extends Fragment {



    protected void showFragment(Fragment fragment , boolean back){
        Activity activity = getActivity();
        if(activity != null){
            activity().showFragment(fragment , back);
        }
    }


    protected void showFragment(Fragment fragment , boolean back  , int enter_anim , int exit_anim ){
        Activity activity = getActivity();
        if(activity != null){
            activity().showFragment(fragment , back  , enter_anim , exit_anim);
        }
    }



    protected void showFragment(Fragment fragment , String tag){
        if(activity() != null){
            activity().showFragment(fragment , tag);
        }
    }


    protected void showNoConn(NoConn noConn) {
        try {
            activity().showNoConn(noConn);
        }catch (Exception e){}
    }





    protected void clearStack(){
        activity().clearStack();
    }

    protected void clearStack(FragmentSwitchActivity.ClearStack clearStack){
        activity().clearStack(clearStack);
    }

    protected void runOnUiThread(Runnable runnable){
        if(runnable != null){
            if(getActivity() != null){
                getActivity().runOnUiThread(runnable);
            }
        }
    }

    protected FragmentSwitchActivity activity (){
        return (FragmentSwitchActivity) getActivity() ;
    }



    static ArrayList<CityModel> av_cities ;
    protected  void get_cities(final CityDataFetch cityDataFetch ){
        if (av_cities !=null )
        {
            if (!av_cities.isEmpty()){
                cityDataFetch.onDataFetch(av_cities);
                return;
            }
        }
        Call<CityListResponse> api = Injector.Api().getAvailableCities() ;
        api.enqueue(new CallbackWithRetry<CityListResponse>(5, 3000, api, new onRequestFailure() {
            @Override
            public void onFailure() {
                showNoConn(new NoConn() {
                    @Override
                    public void onRetry() {
                        get_cities(cityDataFetch);
                    }
                });
            }
        }) {
            @Override
            public void onResponse(Call<CityListResponse> call, Response<CityListResponse> response) {
                if (response.body().getStatus().getStatusCode()==200){
                    av_cities= response.body().getData() ;
                    cityDataFetch.onDataFetch((av_cities));
                }
            }
        });

    }


    protected  void get_cities(final CityDataFetch cityDataFetch , String country ){
        Call<CityListResponse> api = Injector.Api().getAvailableCities(country) ;
        api.enqueue(new CallbackWithRetry<CityListResponse>(5, 3000, api, new onRequestFailure() {
            @Override
            public void onFailure() {
                showNoConn(new NoConn() {
                    @Override
                    public void onRetry() {
                        get_cities(cityDataFetch);
                    }
                });
            }
        }) {
            @Override
            public void onResponse(Call<CityListResponse> call, Response<CityListResponse> response) {
                if (response.body().getStatus().getStatusCode()==200){
                    cityDataFetch.onDataFetch(( response.body().getData()));
                }
            }
        });
    }

    public  interface  CityDataFetch{
        void onDataFetch(ArrayList<CityModel> av_cities);
    }



    protected  void getCountries(final CountryDataFetch countryDataFetch ){
        Call<CountryListResponse> api = Injector.Api().getCountries() ;
        api.enqueue(new CallbackWithRetry<CountryListResponse>(5, 3000, api, new onRequestFailure() {
            @Override
            public void onFailure() {
                showNoConn(new NoConn() {
                    @Override
                    public void onRetry() {
                        getCountries(countryDataFetch);
                    }
                });
            }
        }) {
            @Override
            public void onResponse(Call<CountryListResponse> call, Response<CountryListResponse> response) {
                if (response.body().getStatus().getStatusCode()==200){
                    countryDataFetch.onDataFetch(( response.body().getData()));
                }
            }
        });
    }


    public  interface  CountryDataFetch{
        void onDataFetch(ArrayList<CountryModel> countryModels);
    }






    View progrssView ;
    View container ;



    public void initLoading(View progrssView, View container){
        this.progrssView = progrssView ;
        this.container = container ;
        activity().initLoading(progrssView , container);
    }


    public void showLoading(final boolean show) {
        try {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            container.setVisibility(show ? View.GONE : View.VISIBLE);
            container.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    container.setVisibility(show ? View.GONE : View.VISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    container.setVisibility(show ? View.GONE : View.VISIBLE);

                }
            });

            progrssView.setVisibility(show ? View.VISIBLE : View.GONE);
            progrssView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progrssView.setVisibility(show ? View.VISIBLE : View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    progrssView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void showLongToast(View view, String stringId) {

        Snackbar.make(view, stringId, Snackbar.LENGTH_LONG).show();
    }

    protected void showLongToast(View view, int stringId) {

        Snackbar.make(view, stringId, Snackbar.LENGTH_LONG).show();

    }

    protected void showLongToast(View view, int stringId, int action, View.OnClickListener listener) {

        Snackbar snack = Snackbar.make(view, stringId, Snackbar.LENGTH_LONG)
                .setAction(action, listener)
                .setActionTextColor(getResources().getColor(R.color.colorPrimary));
        snack.show();

    }



    private View noDataLayout;

    protected void showEmptyLayout(boolean visible){
        if(noDataLayout != null){
            noDataLayout.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    private void initNoDataLayout(){
        if(getView() != null){
            noDataLayout = getView().findViewById(R.id.no_data_layout);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initNoDataLayout();
    }



}
