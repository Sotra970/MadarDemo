package com.madar.madardemo.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.messaging.FirebaseMessaging;
import com.madar.madardemo.Activity.NavDrawerActivity;
import com.madar.madardemo.Activity.SocialActivity;
import com.madar.madardemo.AppManger.MadarApplication;
import com.madar.madardemo.Fragment.Abstract.BaseFragment;
import com.madar.madardemo.Interface.NoConn;
import com.madar.madardemo.Model.ProfileItem;
import com.madar.madardemo.Model.ServerResponse.ForgetResponse;
import com.madar.madardemo.Model.ServerResponse.UserLoginResponse;
import com.madar.madardemo.Model.SocialUser;
import com.madar.madardemo.R;
import com.madar.madardemo.Service.CallbackWithRetry;
import com.madar.madardemo.Service.Injector;
import com.madar.madardemo.Service.onRequestFailure;
import com.madar.madardemo.Util.Validation;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Ahmed on 8/15/2017.
 */

public class LoginFragment extends BaseFragment implements View.OnClickListener, SocialActivity.socialLoginInterface {

    private EditText userNameEditText;
    private EditText passwordEditText;

    @BindView(R.id.progressView)
    View progrssView ;
    @BindView(R.id.container)
    View containerHolder ;


    // ButterKnife
//
//    @BindView(R.id.google_sing_in_img)
//    ImageView googleImg;
//
//    @BindView(R.id.twitter_sing_in_img)
//    ImageView twitterImg;

    @BindView(R.id.twitter_login_button)
    TwitterLoginButton twitterLoginButton;

    @BindView(R.id.fb_login_button)
    LoginButton fbLoginButton;

    @BindView(R.id.google_sign_in)
    SignInButton google_sn  ;




    public static LoginFragment getInstance() {
        return new LoginFragment();
    }
    View v ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(v == null){
            v = inflater.inflate(R.layout.fragment_login, container, false);
            ButterKnife.bind(this , v) ;
            initLoading(this.progrssView , this.containerHolder) ;
            getSocialActivity().initLoading(this.progrssView,this.containerHolder);
            social_setup();
            userNameEditText = (EditText)
                    v.findViewById(R.id.fragment_login_username_edittext);

            passwordEditText = (EditText)
                    v.findViewById(R.id.fragment_login_password_edittext);
            final ImageView imageView = (ImageView)
                    v.findViewById(R.id.fragment_sign_up_password_visibility_button);

            if(passwordEditText != null && imageView != null){
                imageView.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TransformationMethod transformationMethod = passwordEditText.getTransformationMethod();
                                if(transformationMethod == null){
                                    imageView.setColorFilter(
                                            ResourcesCompat.getColor(
                                                    getResources(),
                                                    R.color.grey_500,
                                                    null
                                            )
                                    );
                                    passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
                                }
                                else{
                                    imageView.setColorFilter(
                                            ResourcesCompat.getColor(
                                                    getResources(),
                                                    R.color.app_yellow_1,
                                                    null
                                            )
                                    );
                                    passwordEditText
                                            .setTransformationMethod(null);
                                }
                            }
                        }
                );
            }



            View switchToSignUp =  v.findViewById(R.id.fragment_login_switch_to_sign_up);
            if(switchToSignUp != null){
                switchToSignUp.setOnClickListener(this);
            }

            View confirm =  v.findViewById(R.id.fragment_login_confirm_button);
            if(confirm != null){
                confirm.setOnClickListener(this);
            }

        }

        return v;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.fragment_login_switch_to_sign_up){
            Fragment fragment = SignUpFragment.getInstance();
            showFragment(fragment, true);
        }
        else if(id == R.id.fragment_login_confirm_button){
            loginUser();
        }
    }

    private void loginUser(){
        String username;
        String password;

       if ( Validation.isEditTextEmpty(userNameEditText) )
           return;
        else {
           username = userNameEditText.getText().toString() ;
       }

        if ( Validation.isEditTextEmpty(passwordEditText) )
            return;
        else password = passwordEditText.getText().toString() ;


            showLoading(true);

            Call<UserLoginResponse> apiInterface = Injector.Api().login("CompLogin",username, password);
            apiInterface.enqueue(new CallbackWithRetry<UserLoginResponse>(5, 3000, apiInterface, new onRequestFailure() {
                @Override
                public void onFailure() {
                    Toast.makeText(getContext(), getString(R.string.msg_no_internet), Toast.LENGTH_LONG).show();
                    showNoConn(new NoConn() {
                        @Override
                        public void onRetry() {
                            loginUser();
                        }
                    });
                    showLoading(false);
                }
            }) {
                @Override
                public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                    UserLoginResponse res = response.body();
                    if(res.getResponseItem() !=null && res.getResponseItem().getStatusCode() == 200){
                        String secret = res.getSecret();
                        ProfileItem profileItem = res.getUserInfo().get(0) ;
                        profileItem.setSecret(secret);
                       if (profileItem.getActivated().equals("1")){
                           MadarApplication.getInstance().getPrefManager().storeUser(profileItem);
                           FirebaseMessaging.getInstance().subscribeToTopic(profileItem.getSecret());
                           startHome();
                       }
                       else {
                           showFragment(ConfirmationMessgaeSentFragment.getInstance(profileItem) , true);
                           showLoading(false);
                       }
                    }
                    else {

                        Toast.makeText(getContext() , getString(R.string.invalid_username_pass) , Toast.LENGTH_LONG).show();
                        showLoading(false);
                    }


                }
            });

    }




    private void socialLoginReq(final SocialUser socialUser ){
        showLoading(true);

        Call<UserLoginResponse> apiInterface = Injector.Api().sociallogin(
                "CompLogin",
                socialUser.getType() == SocialUser.FACEBOOK_SOCIAL_TYPE ? socialUser.getUid() : null,
                socialUser.getType() == SocialUser.TWITTER_SOCIAL_TYPE ? socialUser.getUid() : null,
                socialUser.getType() == SocialUser.GOOGLE_SOCIAL_TYPE ? socialUser.getUid() : null
                )
                ;
        apiInterface.enqueue(new CallbackWithRetry<UserLoginResponse>(5, 3000, apiInterface, new onRequestFailure() {
            @Override
            public void onFailure() {
                Toast.makeText(getContext(), getString(R.string.msg_no_internet), Toast.LENGTH_LONG).show();
                showNoConn(new NoConn() {
                    @Override
                    public void onRetry() {
                        socialLoginReq(socialUser);
                    }
                });
                showLoading(false);
            }
        }) {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                UserLoginResponse res = response.body();
                if(res.getResponseItem().getStatusCode() == 200){
                    String secret = res.getSecret();
                    ProfileItem profileItem = res.getUserInfo().get(0) ;
                    profileItem.setSecret(secret);
                    if (profileItem.getActivated().equals("1")){
                        MadarApplication.getInstance().getPrefManager().storeUser(profileItem);
                        FirebaseMessaging.getInstance().subscribeToTopic(profileItem.getSecret());
                        startHome();
                    }
                    else {
                        showFragment(ConfirmationMessgaeSentFragment.getInstance(profileItem) , true);
                        showLoading(false);
                    }
                }
                else {

                    Toast.makeText(getContext() , getString(R.string.invalid_username_pass) , Toast.LENGTH_LONG).show();
                    showLoading(false);
                }


            }
        });

    }


    private void startHome() {
        //clearStack();
        Intent i = new Intent(getContext(), NavDrawerActivity.class);
        startActivity(i);
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        getActivity().finish();
    }



    SocialActivity getSocialActivity(){
        return (SocialActivity) getActivity();
    }

    private void social_setup() {
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
        socialLoginReq(user);
    }


    @OnClick(R.id.forget_pass_text)
    void forget_pass(){
        showFragment(ForgetPasswordFragment.getInstance() , true);
    }

}
