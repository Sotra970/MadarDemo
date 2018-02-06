package com.madar.madardemo.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.madar.madardemo.Activity.FragmentSwitchActivity;
import com.madar.madardemo.Activity.SocialActivity;
import com.madar.madardemo.Adapter.GenericSpinnerArrayAdapter;
import com.madar.madardemo.Fragment.Abstract.BaseFragment;
import com.madar.madardemo.Interface.NoConn;
import com.madar.madardemo.Model.CityModel;
import com.madar.madardemo.Model.CountryModel;
import com.madar.madardemo.Model.ProfileItem;
import com.madar.madardemo.Model.ServerResponse.CityListResponse;
import com.madar.madardemo.Model.ServerResponse.UserRegResponse;
import com.madar.madardemo.Model.SocialUser;
import com.madar.madardemo.R;
import com.madar.madardemo.Service.CallbackWithRetry;
import com.madar.madardemo.Service.Injector;
import com.madar.madardemo.Service.onRequestFailure;
import com.madar.madardemo.Util.Validation;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Ahmed on 8/15/2017.
 */

public class SignUpFragment extends BaseFragment implements View.OnClickListener  ,
    SocialActivity.socialLoginInterface {

    public static SignUpFragment getInstance() {
        return new SignUpFragment();
    }


    @BindView(R.id.progressView)
    View progrssView ;
    @BindView(R.id.container)
    View containerHolder ;



    @BindView(R.id.store_name_input)
    EditText store_name_input ;

    @BindView(R.id.first_name_input)
    EditText first_name_input ;

    @BindView(R.id.family_name_input)
    EditText family_name_input ;

    @BindView(R.id.phone_number_input)
    EditText phone_number_input ;


    @BindView(R.id.email_input)
    EditText email_input ;


    @BindView(R.id.city_spiner)
    Spinner citySpinner ;

    @BindView(R.id.country_spiner)
    Spinner country_spiner ;

    @BindView(R.id.password_input)
    EditText password_input ;

    View res_layout ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (res_layout ==null){
            res_layout= LayoutInflater.from(getContext())
                    .inflate(R.layout.fragment_sign_up, container, false);
            ButterKnife.bind(this , res_layout) ;
            initLoading(this.progrssView , this.containerHolder);

            initView(res_layout);
            social_setup();

            setup_city_spinner(savedInstanceState);
            setup_country_spinner(savedInstanceState);


        }

        return  res_layout;
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
    private void setup_country_spinner(Bundle savedInstanceState) {

        countryModels = new ArrayList<>() ;
        country_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectCountryModel = countryAdapter.getItem(i);
                clearCities();
                if (!selectCountryModel.getID().equals("0"))
                get_cities(new CityDataFetch() {
                    @Override
                    public void onDataFetch(ArrayList<CityModel> av_cities) {
                        try {
                           clearCities();
                            cityModels.addAll(av_cities);
                            cityAdapter.notifyDataSetChanged();
                        }catch (Exception e){}
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
                    countryModels.addAll(countryModelsex);
                    countryAdapter.notifyDataSetChanged();
                }catch (Exception e){}
            }
        });

    }


    GenericSpinnerArrayAdapter<CityModel> cityAdapter ;
    ArrayList<CityModel> cityModels = new ArrayList<>();
    CityModel selectCityModel;
    private void setup_city_spinner(Bundle savedInstanceState) {

        cityModels = new ArrayList<>() ;
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectCityModel = cityAdapter.getItem(i); ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



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

    }



    public void initView(View fragmentView){
        if(fragmentView != null){
            View v = fragmentView.findViewById(R.id.fragment_sign_up_switch_to_sign_in);
            if(v != null){
                v.setOnClickListener(this);
            }



            final EditText passwordEdit = (EditText)
                    fragmentView.findViewById(R.id.password_input);
            final ImageView imageView = (ImageView)
                    fragmentView.findViewById(R.id.fragment_sign_up_password_visibility_button);
            if(passwordEdit != null && imageView != null){
                imageView.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TransformationMethod transformationMethod = passwordEdit.getTransformationMethod();
                                if(transformationMethod == null){
                                    imageView.setColorFilter(
                                            ResourcesCompat.getColor(
                                                    getResources(),
                                                    R.color.grey_500,
                                                    null
                                            )
                                    );
                                    passwordEdit.setTransformationMethod(new PasswordTransformationMethod());
                                }
                                else{
                                    imageView.setColorFilter(
                                            ResourcesCompat.getColor(
                                                    getResources(),
                                                    R.color.app_yellow_1,
                                                    null
                                            )
                                    );
                                    passwordEdit.setTransformationMethod(null);
                                }
                            }
                        }
                );
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.fragment_sign_up_switch_to_sign_in){
            Fragment fragment = LoginFragment.getInstance();
            showFragment(fragment , true);
        }

    }



    @OnClick(R.id.confirm_btt)
     void register_user(){
        final String store_name , fits_name  , family_name  , phone , email ,   district  ,  password ;

        if ( Validation.isEditTextEmpty(store_name_input) )
            return;
        else store_name = store_name_input.getText().toString() ;

        if ( Validation.isEditTextEmpty(first_name_input) )
            return;
        else fits_name = first_name_input.getText().toString() ;


        if ( Validation.isEditTextEmpty(family_name_input) )
            return;
        else family_name = family_name_input.getText().toString() ;


        if ( Validation.isEditTextEmpty(phone_number_input) )
            return;
        else phone = phone_number_input.getText().toString() ;


        if ( Validation.validateEmail(email_input) )
            return;
        else email = email_input.getText().toString() ;


        if ( Validation.validate_spinner_mu(country_spiner , selectCountryModel.getID()) )
            return;
        if ( Validation.validate_spinner_mu(citySpinner , selectCityModel.getID()) )
            return;




        if ( Validation.isEditTextEmpty(password_input) )
            return;
        else password = password_input.getText().toString() ;


        showLoading(true);

        ProfileItem profileItem = new ProfileItem() ;
        profileItem.setName(store_name);
        profileItem.setFirstName(fits_name);
        profileItem.setLastName(family_name);
        profileItem.setPhone(phone);
        profileItem.setEMail(email);
        profileItem.setCityID(selectCityModel.getID());
        profileItem.setCountryID(selectCountryModel.getID());

        profileItem.setPass(password);
        profileItem.setRequest("RegComp");

        if (socialUser != null)
        {
            if (socialUser.getPhoto() !=null)
                profileItem.setImage(socialUser.getPhoto());

            if (socialUser.getType() == SocialUser.FACEBOOK_SOCIAL_TYPE )  profileItem.setFB_UID(socialUser.getUid());
            if (socialUser.getType() == SocialUser.TWITTER_SOCIAL_TYPE  )  profileItem.setTwitter_UID(socialUser.getUid());
            if (socialUser.getType() == SocialUser.GOOGLE_SOCIAL_TYPE   )  profileItem.setGoogle_UID(socialUser.getUid());

        }

        Call<UserRegResponse> apiInterface = Injector.Api().createAccount(profileItem);
        apiInterface.enqueue(new CallbackWithRetry<UserRegResponse>(5, 3000, apiInterface, new onRequestFailure() {
            @Override
            public void onFailure() {
                Toast.makeText(getContext(), getString(R.string.msg_no_internet), Toast.LENGTH_LONG).show();
                showNoConn(new NoConn() {
                    @Override
                    public void onRetry() {
                        register_user();
                    }
                });
                showLoading(false);
            }
        }) {
            @Override
            public void onResponse(Call<UserRegResponse> call, Response<UserRegResponse> response) {
                UserRegResponse res = response.body();
                int code  = res.getResponseItem().getStatusCode() ;
                if( code== 200){
                    showLongToast(containerHolder , getString(R.string.regstraion_scuccess)  );
                    clearStack(new FragmentSwitchActivity.ClearStack() {
                        @Override
                        public void onClearFinish() {
                            showFragment(LoginFragment.getInstance() , false);
                        }
                    });
//                    showFragment(LoginFragment.getInstance() , true);

//                    showFragment(LoginFragment.getInstance(), true);

                }else if (code == 401 ){
                    email_input.setError(getString(R.string.duplicated));
                    email_input.requestFocus();
                }

                else if (code == 402 ){
                    phone_number_input.setError(getString(R.string.duplicated));
                    phone_number_input.requestFocus();
                }

                showLoading(false);
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("cityModels" , cityModels);
    }



    @BindView(R.id.twitter_login_button)
    TwitterLoginButton twitterLoginButton;

    @BindView(R.id.fb_login_button)
    LoginButton fbLoginButton;

    @BindView(R.id.google_sign_in)
    SignInButton google_sn  ;


    SocialActivity getSocialActivity(){
        return (SocialActivity) getActivity();
    }

    private void social_setup() {
        getSocialActivity().initLoading(this.progrssView,this.containerHolder);
        getSocialActivity().TwitterloginButton = this.twitterLoginButton;
        getSocialActivity().loginButton = this.fbLoginButton;
        getSocialActivity().google_sn = this.google_sn;
        getSocialActivity().setSocialLoginInterface(this);
    }
    @OnClick(R.id.twitter_sing_in_img)
    void twitter_on_click(){
        getSocialActivity().twitter_on_click();
    }

    @OnClick(R.id.fb_sing_in_img)
    void facebook_on_click()
    {

        getSocialActivity().facebook_on_click();

    }


    @OnClick(R.id.google_sing_in_img)
    void google_on_click(){
        getSocialActivity().google_on_click();
    }


    @Override
    public void social_login(SocialUser user) {
        showLoading(false);
        socialbind(user);
    }

    SocialUser socialUser ;
    private void socialbind(SocialUser user) {
            socialUser = user ;
        if (user.getName() != null){
            first_name_input.setText(user.getName());
        }

        if (user.getEmail() != null){
            email_input.setText(user.getEmail());
        }


    }

//    public void showLoading(final boolean show) {
//        try {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//            containerHolder.setVisibility(show ? View.GONE : View.VISIBLE);
//
//            progrssView.setVisibility(show ? View.VISIBLE : View.GONE);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
