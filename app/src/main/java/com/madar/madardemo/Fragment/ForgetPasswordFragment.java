package com.madar.madardemo.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
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
import com.madar.madardemo.Model.ServerResponse.ForgetResponse;
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

public class ForgetPasswordFragment extends BaseFragment implements View.OnClickListener {



    @BindView(R.id.progressView)
    View progrssView ;

    @BindView(R.id.container)
    View containerHolder ;

    @BindView(R.id.code_input)
    EditText code_input ;

    public static ForgetPasswordFragment getInstance() {
       ForgetPasswordFragment confirmationMessgaeSentFragment = new ForgetPasswordFragment() ;
        return confirmationMessgaeSentFragment ;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView= inflater.inflate(R.layout.fragment_forget_pass, container, false);
        ButterKnife.bind(this , fragmentView) ;
        initLoading(this.progrssView , this.containerHolder) ;
        View confirmButton = fragmentView.findViewById(R.id.fragment_message_sent_confirm);
        if(confirmButton != null){
            confirmButton.setOnClickListener(this);
        }

        View cancelButton = fragmentView.findViewById(R.id.fragment_message_sent_cancel);
        if(cancelButton != null){
            cancelButton.setOnClickListener(this);
        }


        return fragmentView;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fragment_message_sent_confirm:
                forget_pass();
                break;

            case R.id.fragment_message_sent_cancel:
                cancel();
                break;


        }
    }





    private void cancel(){
        getActivity().onBackPressed();
    }




    void forget_pass(){
        Log.e("froget_pass" , "forget");
        String username ;
        if ( Validation.isEditTextEmpty(code_input) )
            return;
        else {
            username = code_input.getText().toString() ;
        }

        showLoading(true);
        Call<ForgetResponse> call  = Injector.Api().forgetPass(username);
        call.enqueue(new CallbackWithRetry<ForgetResponse>(3, 3000, call, new onRequestFailure() {
            @Override
            public void onFailure() {
                showNoConn(new NoConn() {
                    @Override
                    public void onRetry() {
                        forget_pass();
                    }
                });
            }
        }) {
            @Override
            public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                if (response.body().getResponseItem() != null)
                    if (response.body().getResponseItem().isSuccessful()){
                        showLoading(false);
                        new AlertDialog.Builder(getActivity()).setMessage(getString(R.string.forget_message))
                                .setNeutralButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        cancel();
                                    }
                                })
                                .create().show();
                    }
            }
        });
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
