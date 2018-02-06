package com.madar.madardemo.Fragment.AddOrder.ReciverInfo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.madar.madardemo.Adapter.GenericSpinnerArrayAdapter;
import com.madar.madardemo.Adapter.No_Title_Pager_Adapter;
import com.madar.madardemo.AppManger.MadarApplication;
import com.madar.madardemo.Fragment.Abstract.AddOrderBaseFragment;
import com.madar.madardemo.Model.CityModel;
import com.madar.madardemo.R;
import com.madar.madardemo.Util.Validation;
import com.madar.madardemo.ViewHolder.CustomTabTitleHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ahmed on 8/15/2017.
 */

public class PackageReciverInfoFragment extends AddOrderBaseFragment implements AdapterView.OnItemSelectedListener  {

    @BindView(R.id.progressView)
    View progrssView ;
    @BindView(R.id.container)
    View containerHolder ;

    @BindView(R.id.receiver_name_input)
    EditText receiver_name_input ;



    @BindView(R.id.phone_number_input)
    EditText phone_number_input ;



    @BindView(R.id.district_input)
    EditText district_input ;



    @BindView(R.id.street_input)
    EditText street_input ;




    @BindView(R.id.building_number)
    EditText building_number_input ;








    public static PackageReciverInfoFragment getInstance() {
        return new PackageReciverInfoFragment();
    }
    View v ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (v == null) {
            v = inflater.inflate(R.layout.fragment_reciver_info, container, false);
            ButterKnife.bind(this, v);
            initLoading(this.progrssView, this.containerHolder);
        }

        setup_city_spinner(savedInstanceState);


        return v;
    }

    @Override
    public void onPageSelected(int pos) {
        setupPagerPullet(true , pos);
        map_tabs_setup() ;
    }


    @BindView(R.id.viewPager)
    ViewPager viewPager ;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout ;
    No_Title_Pager_Adapter adapter ;
    void map_tabs_setup(){
        adapter = new No_Title_Pager_Adapter(getChildFragmentManager()) ;

        MapLinkFragment mapLinkFragment = new MapLinkFragment();
        MapFragment mapFragment = new MapFragment() ;

        adapter.addFragment(mapFragment);
        adapter.addFragment(mapLinkFragment);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        final CustomTabTitleHolder tab2titleHolder = new CustomTabTitleHolder(getString(R.string.map_link)  , getContext()) ;
        final CustomTabTitleHolder tab1titleHolder = new CustomTabTitleHolder(getString(R.string.map_open)  , getContext()) ;

        tabLayout.getTabAt(0).setCustomView( tab1titleHolder.getView());
        tabLayout.getTabAt(1).setCustomView( tab2titleHolder.getView());
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0 : tab1titleHolder.select();
                        break;
                    case 1 : tab2titleHolder.select();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0 : tab1titleHolder.unSelect();
                        break;
                    case 1 : tab2titleHolder.unSelect();
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @BindView(R.id.city_spiner)
    Spinner citySpinner ;
    GenericSpinnerArrayAdapter<CityModel> cityAdapter ;
    ArrayList<CityModel> cityModels = new ArrayList<>();

    int selected_city = 0 ;

    private void setup_city_spinner(Bundle savedInstanceState) {

        citySpinner.setOnItemSelectedListener(this);


        final CityModel cityModel = new CityModel("0",getString(R.string.hint_city));
        cityModels.add(0,cityModel);

        cityAdapter = new GenericSpinnerArrayAdapter<CityModel>(getContext() , cityModels) {
            @Override
            public void drawText(TextView textView, CityModel object) {
                textView.setText(object.getName());
            }

            @Override
            public void drawSubText(TextView textView, CityModel object) {
                textView.setText(object.getName());
            }


        };

        cityAdapter.setDropDownViewResource(R.layout.simple_spinner_item_child);

        citySpinner.setAdapter(cityAdapter);
        if (savedInstanceState == null)
            get_cities(new CityDataFetch() {
                @Override
                public void onDataFetch(ArrayList<CityModel> av_cities) {
                    cityModels.addAll(av_cities);
                   for (int i=0 ; i<cityModels.size() ; i++)
                       if (cityModels.get(i).getID().equals(getOrderModel().getFrom_City()+""))
                           selected_city= i;
                    Log.e("get_city_id" ,cityModel.getName() + "-"+selected_city);

                    if (getAddOrderActivity().isSame()){
                        citySpinner.setClickable(false);
                        citySpinner.setFocusable(false);
                        citySpinner.setFocusableInTouchMode(false);
                        citySpinner.setSelection(selected_city);

                    }
                    cityAdapter.notifyDataSetChanged();
                }
            }, MadarApplication.getUser().getCountryID());
        else {
            cityModels = (ArrayList<CityModel>) savedInstanceState.getSerializable("cityModels");
            for (int i=0 ; i<cityModels.size() ; i++)
                if (cityModels.get(i).getID().equals(getOrderModel().getFrom_City()+""))
                    selected_city= i;
            Log.e("get_city_id" ,cityModel.getName() + "-"+selected_city);

            if (getAddOrderActivity().isSame()){
                citySpinner.setClickable(false);
                citySpinner.setFocusable(false);
                citySpinner.setFocusableInTouchMode(false);
                citySpinner.setSelection(selected_city);

            }
        }







    }




    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("cityModels" , cityModels);
    }


    CityModel selectCityModel;
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case  R.id.city_spiner :
                selectCityModel = cityAdapter.getItem(position); ;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }








    @OnClick(R.id.next)
    void next(){

        final String receiver_name  , phone ,   district ,   street , building_number  ;

        if ( Validation.isEditTextEmpty(receiver_name_input) )
            return;
        else receiver_name = receiver_name_input.getText().toString() ;


        if ( Validation.isEditTextEmpty(phone_number_input) )
            return;
        else phone = phone_number_input.getText().toString() ;


        if ( Validation.validate_spinner_mu(citySpinner , selectCityModel.getID()) )
            return;

        // same city check
        if (getAddOrderActivity().isSame()  &&  !selectCityModel.getID().equals(getOrderModel().getReceiving_City_ID())){

            new  AlertDialog.Builder(getActivity())
                    .setMessage(getString(R.string.city_not_the_same))
                    .setNeutralButton(getString(R.string.accept_message), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
            return;
        }

        // check selected city the same map city



        // city eligible to shipping to
        if (getAddOrderActivity().isOther() &&  !selectCityModel.shippingToEdibility() ){
            Log.e("shippingToEdibility" , selectCityModel.shippingToEdibility()+"-"+selectCityModel.eligible) ;
            showNotSupportedAreaDialog();
            return;
        }


        if ( Validation.isEditTextEmpty(district_input) )
            return;
        else district = district_input.getText().toString() ;



        if ( Validation.isEditTextEmpty(street_input) )
            return;
        else street = street_input.getText().toString() ;

        if ( Validation.isEditTextEmpty(building_number_input) )
            return;
        else building_number = building_number_input.getText().toString() ;

        getOrderModel().setDistrict(selectCityModel.getName()+" - "+district+" - "+building_number+" - "+street);
        getOrderModel().setRecipient_Name(receiver_name);
        getOrderModel().setCity_ID(selectCityModel.getID());
        getOrderModel().setTo_City(selectCityModel.getID());
//        getOrderModel().setImgs(getAddOrderActivity().getUpload_img_result_ids());

        getOrderModel().setPhone(phone);

        // check for location
        if (TextUtils.isEmpty(getOrderModel().getDeleviry_Place())){
            showLongToast(containerHolder , R.string.location_err);
            return;
        }


        get_city_id(getOrderModel().getReciverMapCity(), new Validate_city() {
            @Override
            public void onDataFetch(CityModel cityModel) {

                if (cityModel == null)
                {
                    showMapCityNotEqaletoSelectedCity();
                    return;
                }

                if (!cityModel.getID().equals(selectCityModel.getID())){
                    showMapCityNotEqaletoSelectedCity();
                    return;
                }

                if (getAddOrderActivity().isSame()){
                    getOnNextClick().next(getOrderModel());
                    showLoading(false);
                    return;
                }


                if (getAddOrderActivity().isOther() &&!cityModel.shippingToEdibility()){
                    Log.e("shippingToEdibility" , cityModel.shippingToEdibility()+"-"+cityModel.eligible) ;
                    showNotSupportedAreaDialog();
                    return;
                }






                getOnNextClick().next(getOrderModel());

                showLoading(false);

            }
        });

    }






    @OnClick(R.id.cancel)
    void cancel_order(){
        getAddOrderActivity().backClick();
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



    private void showMapCityNotEqaletoSelectedCity() {
        showLoading(false);
        new  AlertDialog.Builder(getActivity())
                .setMessage(getString(R.string.showMapCityNotEqaletoSelectedCity))
                .setNeutralButton(getString(R.string.accept_message), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }





}
