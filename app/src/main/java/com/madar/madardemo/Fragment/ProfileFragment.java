package com.madar.madardemo.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.madar.madardemo.Adapter.GenericSpinnerArrayAdapter;
import com.madar.madardemo.AppManger.Config;
import com.madar.madardemo.AppManger.MadarApplication;
import com.madar.madardemo.Fragment.Abstract.TitledFragment;
import com.madar.madardemo.Interface.NoConn;
import com.madar.madardemo.Model.CityModel;
import com.madar.madardemo.Model.CountryModel;
import com.madar.madardemo.Model.ProfileItem;
import com.madar.madardemo.Model.ServerResponse.ProfileImgModel;
import com.madar.madardemo.Model.ServerResponse.UpdateProfileResponse;
import com.madar.madardemo.R;
import com.madar.madardemo.Service.CallbackWithRetry;
import com.madar.madardemo.Service.Injector;
import com.madar.madardemo.Service.onRequestFailure;
import com.madar.madardemo.Util.Validation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by Ahmed on 8/17/2017.
 */

public class ProfileFragment extends TitledFragment{

    @BindView(R.id.container)
    View container;

    @BindView(R.id.progressView)
    View progress;


    @BindView(R.id.profile_bg)
    ImageView profile_bg;
    @BindView(R.id.profile_img)
    ImageView profile_img;

    private ProfileItem profileItem;


    @BindView(R.id.country_spiner)
    Spinner country_spiner ;

    private EditText storeTitleEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText phoneNumberEditText;
    private Spinner citySpinner;
    private EditText emailEditText;
    private EditText passwordEditText;

    private ProfileImgModel profile_img_model;

    public static ProfileFragment getInstance() {
        ProfileFragment fragment = new ProfileFragment();
        fragment.profileItem = null;
        try {
            fragment.profileItem =
                    MadarApplication.getInstance().getPrefManager().getUser();
        }
        catch (Exception e){}

        return fragment;
    }

    View v ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       if (v == null){
            v = inflater.inflate(R.layout.fragment_profile, container, false);
           ButterKnife.bind(this, v);
           initLoading(progress, this.container);

           View submitButton = v.findViewById(R.id.fragment_profile_submit_button);
           if(submitButton != null){
               submitButton.setOnClickListener(
                       new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               updateUser();
                           }
                       }
               );
           }
           initFields(v);

           setup_city_spinner(savedInstanceState);
           setup_country_spinner(savedInstanceState);

           fillData();
       }

       if (savedInstanceState !=null){
           profile_img_model = (ProfileImgModel) savedInstanceState.get("profile_img_model");
       }

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle(R.string.fragment_title_profile);
    }

    private void initFields(View view)
    {
        storeTitleEditText = (EditText)
                view.findViewById(R.id.fragment_profile_store_title_edit_text);

        firstNameEditText = (EditText)
                view.findViewById(R.id.fragment_profile_first_name_edit_text);

        lastNameEditText = (EditText)
                view.findViewById(R.id.fragment_profile_last_name_edit_text);

        phoneNumberEditText = (EditText)
                view.findViewById(R.id.fragment_profile_phone_number_edit_text);

        citySpinner = (Spinner)
                view.findViewById(R.id.fragment_profile_city_edit_text);


        emailEditText = (EditText)
                view.findViewById(R.id.fragment_profile_email_edit_text);

        passwordEditText = (EditText)
                view.findViewById(R.id.fragment_profile_password_edit_text);

    }

    private void fillData(){
        if(profileItem != null){
            storeTitleEditText.setText(profileItem.getName());
            firstNameEditText.setText(profileItem.getFirstName());
            lastNameEditText.setText(profileItem.getLastName());
            phoneNumberEditText.setText(profileItem.getPhone());

            emailEditText.setText(profileItem.getEMail());
            passwordEditText.setText(profileItem.getPass());

            Glide.with(this)
                    .load(R.drawable.circular_profile_background)
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .transition(new DrawableTransitionOptions().crossFade())
                    .thumbnail(0.5f)
                    .into(profile_bg);

            Log.e("profileImage" , profileItem.getImage()) ;
            Glide.with(this)
                    .load(profileItem.getImage() )
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .apply(new RequestOptions().centerCrop())
                    .apply(new RequestOptions().circleCrop())
                    .transition(new DrawableTransitionOptions().crossFade())
                    .thumbnail(0.5f)
                    .into(profile_img) ;
        }
    }

    private void updateUser(){
        final String store_name, fits_name, family_name, phone, email,  password ;

        if ( Validation.isEditTextEmpty(storeTitleEditText) ) {
            return;
        }
        else {
            store_name = storeTitleEditText.getText().toString() ;
        }

        if ( Validation.isEditTextEmpty(firstNameEditText) ) {
            return;
        }
        else {
            fits_name = firstNameEditText.getText().toString() ;
        }

        if ( Validation.isEditTextEmpty(lastNameEditText) ) {
            return;
        }
        else {
            family_name = lastNameEditText.getText().toString() ;
        }

        if ( Validation.isEditTextEmpty(phoneNumberEditText) )
        {
            return;
        }
        else {
            phone = phoneNumberEditText.getText().toString() ;
        }

        if ( Validation.validateEmail(emailEditText) ) {
            return;
        }
        else {
            email = emailEditText.getText().toString();
        }

        if ( Validation.validate_spinner_mu(citySpinner , selectCityModel.getID()) )
        {
            return;
        }

        if ( Validation.validate_spinner_mu(country_spiner , selectCountryModel.getID()) )
        {
            return;
        }


        if ( Validation.isEditTextEmpty(passwordEditText) )
        {
            return;
        }
        else {
            password = passwordEditText.getText().toString() ;
        }


        showLoading(true);

        final ProfileItem tempprofileItem = new ProfileItem() ;
        tempprofileItem.setName(store_name);
        tempprofileItem.setFirstName(fits_name);
        tempprofileItem.setLastName(family_name);
        tempprofileItem.setPhone(phone);
        tempprofileItem.setEMail(email);
        tempprofileItem.setCityID(selectCityModel.getID());
        tempprofileItem.setCountryID(selectCountryModel.getID());
        tempprofileItem.setCountryCode("+"+selectCountryModel.getPhone_Code());
        tempprofileItem.setCurrency_Code(selectCountryModel.getCurrency_Code());
        tempprofileItem.setCurrency(selectCountryModel.getCurrency());
        tempprofileItem.setID(MadarApplication.getInstance().getPrefManager().getUser().getID());
        tempprofileItem.setPass(password);
        if (!activity().getProfile_upload_img_result().isEmpty()){
            profile_img_model = activity().getProfile_upload_img_result().get(0);
            tempprofileItem.setImage(profile_img_model.getId() );
        }else {
            String jpeg = this.profileItem.getImage().substring((this.profileItem.getImage().lastIndexOf("/")+1) , this.profileItem.getImage().length()) ;
            String id =  jpeg.substring(0 , jpeg.indexOf(".")) ;
            profile_img_model = new ProfileImgModel(id , "mod" );
            tempprofileItem.setImage(id) ;
        }

        tempprofileItem.setRequest("UpdateUserData");
        tempprofileItem.setSecret(this.profileItem.getSecret());


        if (!tempprofileItem.getPhone().trim().equals(MadarApplication.getInstance().getPrefManager().getUser().getPhone())){
            // verfy then update
            showLoading(false);
            showFragment(UpdateConfirmationFragment.getInstance(tempprofileItem,this) , true);
            return;
        }

        Call<UpdateProfileResponse> apiInterface = Injector.Api().updateProfile(tempprofileItem);
        apiInterface.enqueue(new CallbackWithRetry<UpdateProfileResponse>(5, 3000, apiInterface, new onRequestFailure() {
            @Override
            public void onFailure() {
                showNoConn(new NoConn() {
                    @Override
                    public void onRetry() {
                        updateUser();
                    }
                });

                showLoading(false);
            }
        })
        {
            @Override
            public void onResponse(@NonNull Call<UpdateProfileResponse> call, @NonNull Response<UpdateProfileResponse> response) {
                UpdateProfileResponse res = response.body();
                if(res != null){
                    int code  = res.getStatus().getStatusCode() ;
                    if( code== 200){
                       onUpdateSuccess(tempprofileItem);
                    }
                    else if (code == 401 ){
                        emailEditText.setError(getString(R.string.duplicated));
                        emailEditText.requestFocus();
                    }

                    else if (code == 402 ){
                        phoneNumberEditText.setError(getString(R.string.duplicated));
                        phoneNumberEditText.requestFocus();
                    }
                }

                showLoading(false);
            }
        });

    }

    public void onUpdateSuccess(ProfileItem profileItem) {
        if (profile_img_model !=null)
//            profileItem.setImage(Config.BASE_IMG_URL+ profile_img_model.getPath());
            profileItem.setImage(Config.BASE_IMG_URL+ "/upload/"+profile_img_model.getId()+".jpg");
        MadarApplication.getInstance().getPrefManager().storeUser(profileItem);
        Intent intent = new Intent(Config.UPDATE_USER_PROFILE);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
        showLongToast(container , R.string.profile_updated);
    }


    void clearCities(){
        cityModels.clear();
        cityAdapter.clear();
        CityModel cityModel = new CityModel("0", getString(R.string.hint_city));
        cityModels.add(0,cityModel);
        cityAdapter.notifyDataSetChanged();
    }

    void clearCountries(){
        countryModels.clear();
        countryAdapter.clear();
        final CountryModel countryModel = new CountryModel("0", getString(R.string.hint_country));
        countryModels.add(0,countryModel);
        countryAdapter.notifyDataSetChanged();
    }




    GenericSpinnerArrayAdapter<CountryModel> countryAdapter ;
    ArrayList<CountryModel> countryModels = new ArrayList<>();
    CountryModel selectCountryModel;
    int user_country_pos = -1 ;
    private void setup_country_spinner(Bundle savedInstanceState) {

        countryModels = new ArrayList<>() ;
        country_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                selectCountryModel = countryAdapter.getItem(pos);
                clearCities();
                if (!selectCountryModel.getID().equals("0"))


                    get_cities(new CityDataFetch() {
                        @Override
                        public void onDataFetch(ArrayList<CityModel> av_cities) {
                            try {
                                clearCities();
                                for (int i=0  ; i<av_cities.size() ;  i++){
                                    CityModel cityModel = av_cities.get(i);
                                    cityModels.add(cityModel);
                                    if (cityModel.getID().equals(MadarApplication.getInstance().getPrefManager().getUser().getCityID())){
                                        user_city_pos = i ;
                                    }
                                }

                                cityAdapter.notifyDataSetChanged();
                                if (user_city_pos!=-1)
                                    citySpinner.setSelection(++user_city_pos);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    },selectCountryModel.getID());



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        countryAdapter = new GenericSpinnerArrayAdapter<CountryModel>(getContext() , countryModels) {
            @Override
            public void drawText(TextView textView, CountryModel object) {
                textView.setText(object.getName());
            }

            @Override
            public void drawSubText(TextView textView, CountryModel object) {
                textView.setText(object.getName());
            }
        };

        countryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        country_spiner.setAdapter(countryAdapter);

        getCountries(new CountryDataFetch() {
            @Override
            public void onDataFetch(ArrayList<CountryModel> countryModelsex) {
                try {
                    clearCountries();
                    for (int i=0  ; i<countryModelsex.size() ;  i++){
                        CountryModel countryModel = countryModelsex.get(i);
                        countryModels.add(countryModel);
                        if (countryModel.getID().equals(MadarApplication.getInstance().getPrefManager().getUser().getCountryID())){
                            user_country_pos = i ;
                        }
                    }
                    countryAdapter.notifyDataSetChanged();
                    country_spiner.setSelection(++user_country_pos);
                }catch (Exception e){}
            }
        });

    }


    private GenericSpinnerArrayAdapter<CityModel> cityAdapter ;
    private ArrayList<CityModel> cityModels = new ArrayList<>();
    private CityModel selectCityModel;
    int user_city_pos  = 0 ;
    private void setup_city_spinner(Bundle savedInstanceState) {




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

        cityAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdapter);
        clearCities();
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectCityModel = cityAdapter.getItem(i); ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


    @OnClick(R.id.profile_img_upload)
    void  upload_profile_img(){
        activity().pick_profile_image_permission();
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("Profile" , "onReceive") ;
            update_profile_img_src((Uri) intent.getExtras().get("img"));
        }
    };
    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver , new IntentFilter(Config.UPDATE_USER_PROFILE_IMG));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
        }catch (Exception e){}
    }

    void  update_profile_img_src(Uri src){
       try {
           Glide.with(this)
                   .load(src)
                   .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                   .apply(new RequestOptions().centerCrop())
                   .apply(new RequestOptions().circleCrop())
                   .transition(new DrawableTransitionOptions().crossFade())
                   .thumbnail(0.5f)
                   .into(profile_img) ;
       }catch (Exception e){
           e.printStackTrace();
       }
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("cityModels" , cityModels);
        outState.putSerializable("profile_img_model" , profile_img_model);
    }

}
