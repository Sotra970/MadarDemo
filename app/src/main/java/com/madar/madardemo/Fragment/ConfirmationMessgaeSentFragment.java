package com.madar.madardemo.Fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.madar.madardemo.Activity.NavDrawerActivity;
import com.madar.madardemo.AppManger.MadarApplication;
import com.madar.madardemo.Fragment.Abstract.BaseFragment;
import com.madar.madardemo.Interface.NoConn;
import com.madar.madardemo.Model.ProfileItem;
import com.madar.madardemo.Model.ServerResponse.ConfirmationResponse;
import com.madar.madardemo.R;
import com.madar.madardemo.Service.CallbackWithRetry;
import com.madar.madardemo.Service.Injector;
import com.madar.madardemo.Service.onRequestFailure;
import com.madar.madardemo.Util.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Ahmed on 8/16/2017.
 */

public class ConfirmationMessgaeSentFragment extends BaseFragment implements View.OnClickListener {


    public ProfileItem profileItem ;

    @BindView(R.id.progressView)
    View progrssView ;

    @BindView(R.id.container)
    View containerHolder ;

    @BindView(R.id.code_input)
    EditText code_input ;

    public static ConfirmationMessgaeSentFragment getInstance(ProfileItem profileItem) {
       ConfirmationMessgaeSentFragment confirmationMessgaeSentFragment = new ConfirmationMessgaeSentFragment() ;
        confirmationMessgaeSentFragment.profileItem = profileItem ;
        return confirmationMessgaeSentFragment ;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_message_sent, container, false);
        ButterKnife.bind(this , v) ;
        initLoading(this.progrssView , this.containerHolder) ;
        if (savedInstanceState!=null)
            profileItem = (ProfileItem) savedInstanceState.getSerializable("profileItem") ;

        initView(v);

        return v;
    }

    private void initView(View fragmentView){
        TextView secondaryTextView = (TextView)
                fragmentView.findViewById(R.id.fragment_message_sent_secondary_text_view);

        Spannable w1 = new SpannableString("تم ارسال رسالة ");
        w1.setSpan(
                new ForegroundColorSpan(
                        ResourcesCompat.getColor(
                                getResources(),
                                R.color.grey_500,
                                null)
                ),
                0,
                w1.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        secondaryTextView.setText(w1);

        Spannable n1 = new SpannableString("ٍSMS");
        n1.setSpan(
                new ForegroundColorSpan(
                        ResourcesCompat.getColor(
                                getResources(),
                                R.color.red_500,
                                null)
                ),
                0,
                n1.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        secondaryTextView.append(n1);

        Spannable w2 = new SpannableString(" الى رقمك ");
        w2.setSpan(
                new ForegroundColorSpan(
                        ResourcesCompat.getColor(
                                getResources(),
                                R.color.grey_500,
                                null)
                ),
                0,
                w2.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        secondaryTextView.append(w2);

        Spannable n2 = new SpannableString(profileItem.getPhone());
        n2.setSpan(
                new ForegroundColorSpan(
                        ResourcesCompat.getColor(
                                getResources(),
                                R.color.red_500,
                                null)
                ),
                0,
                n2.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        secondaryTextView.append(n2);

        Spannable w3 = new SpannableString(" تحتوي على رمز التأكيد ");
        w3.setSpan(
                new ForegroundColorSpan(
                        ResourcesCompat.getColor(
                                getResources(),
                                R.color.grey_500,
                                null)
                ),
                0,
                w3.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        secondaryTextView.append(w3);

        View confirmButton = fragmentView.findViewById(R.id.fragment_message_sent_confirm);
        if(confirmButton != null){
            confirmButton.setOnClickListener(this);
        }

        View cancelButton = fragmentView.findViewById(R.id.fragment_message_sent_cancel);
        if(cancelButton != null){
            cancelButton.setOnClickListener(this);
        }

        View resendButton = fragmentView.findViewById(R.id.fragment_message_sent_resend_code);
        if(resendButton != null){
            resendButton.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fragment_message_sent_confirm:
                confirmCode();
                break;

            case R.id.fragment_message_sent_cancel:
                cancel();
                break;

            case R.id.fragment_message_sent_resend_code:
                resendCode();
                break;
        }
    }

    private void confirmCode(){

        // confirm
        final String code ;
        if (!Validation.isEditTextEmpty(code_input))
            code = code_input.getText().toString();
        else  return;

        showLoading(true);
        Call<ConfirmationResponse> confirmationResponseCall = Injector.Api().check_code(
                "ValidCode",code , profileItem.getSecret()
        );
        confirmationResponseCall.enqueue(new CallbackWithRetry<ConfirmationResponse>(5, 3000, confirmationResponseCall, new onRequestFailure() {
            @Override
            public void onFailure() {
                showNoConn(new NoConn() {
                    @Override
                    public void onRetry() {
                        try{
                            confirmCode();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        }) {
            @Override
            public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {
                if (response.body().getResponseItem().getStatusCode() == 200){
                    FirebaseMessaging.getInstance().subscribeToTopic(profileItem.getSecret());
                    MadarApplication.getInstance().getPrefManager().storeUser(profileItem);
                    FirebaseMessaging.getInstance().subscribeToTopic(profileItem.getSecret());
                    FirebaseMessaging.getInstance().subscribeToTopic("notifyallstore");

                    startHome() ;
                }else {
                    code_input.setError(getString(R.string.wrong_code));
                    code_input.requestFocus();
                    showLoading(false);
                }
            }
        });
    }




    private void startHome() {
        Intent i = new Intent(getContext(), NavDrawerActivity.class);
        startActivity(i);
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        getActivity().finish();
    }


    private void cancel(){
        getActivity().onBackPressed();
    }

    private void resendCode(){
        //resend
        // confirm
           code_input.setError(null);
        code_input.setText("");

        showLoading(true);

        Call<ConfirmationResponse> confirmationResponseCall = Injector.Api().resend_code(
                "ResendVerif",
               profileItem.getSecret()
        );
        confirmationResponseCall.enqueue(new CallbackWithRetry<ConfirmationResponse>(5, 3000, confirmationResponseCall, new onRequestFailure() {
            @Override
            public void onFailure() {
                showNoConn(new NoConn() {
                    @Override
                    public void onRetry() {
                        try{
                            resendCode();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        }) {
            @Override
            public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {
                if (response.body().getResponseItem().getStatusCode() == 200){
                    showLoading(false);
                    Toast.makeText(getActivity() ,  getString(R.string.resend_scc), Toast.LENGTH_LONG).show(); ;
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("profileItem" ,profileItem);
    }

    @OnClick(R.id.do_u_have_account)
    void do_u_have_account(){
        cancel();
    }


    public void showLoading(final boolean show) {
        try {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            containerHolder.setVisibility(show ? View.GONE : View.VISIBLE);

            progrssView.setVisibility(show ? View.VISIBLE : View.GONE);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
