package com.madar.madardemo.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.madar.madardemo.Adapter.BankAccountSpinnerAdapter;
import com.madar.madardemo.AppManger.MadarApplication;
import com.madar.madardemo.Fragment.Abstract.TitledFragment;
import com.madar.madardemo.Model.BankItem;
import com.madar.madardemo.Model.ResponseItem;
import com.madar.madardemo.Model.ServerResponse.BankListResponse;
import com.madar.madardemo.R;
import com.madar.madardemo.Model.ServerResponse.AddBankResponse;
import com.madar.madardemo.Service.CallbackWithRetry;
import com.madar.madardemo.Service.Injector;
import com.madar.madardemo.Service.onRequestFailure;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Ahmed on 8/22/2017.
 */

public class AddBankAccountFragment extends TitledFragment{

    @BindView(R.id.progressView)
    View progrssView ;
    @BindView(R.id.container)
    View containerHolder ;

    private EditText accountHolderNameEditText;
    private EditText accountNumberEditText;
    private Spinner bankSelectSpinner;

    private String selectedBankId = "";

    private View saveButton;

    private BankAccountSpinnerAdapter spinnerAdapter;

    public static AddBankAccountFragment getInstance() {
        return new AddBankAccountFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bank_account, container, false);

        ButterKnife.bind(this , v) ;
        initLoading(this.progrssView , this.containerHolder) ;

        accountHolderNameEditText = (EditText)
                v.findViewById(R.id.fragment_bank_account_account_holder_name_edit_text);
        accountNumberEditText = (EditText)
                v.findViewById(R.id.fragment_bank_account_account_number_edit_text);
        bankSelectSpinner = (Spinner)
                v.findViewById(R.id.fragment_bank_account_bank_select_spinner);

        bankSelectSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if(spinnerAdapter != null){
                            selectedBankId = spinnerAdapter.getBankId(i);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        selectedBankId = null;
                    }
                }
        );

        saveButton = v.findViewById(R.id.fragment_bank_account_save_button);
        if(saveButton != null){
            saveButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            saveBankAccount();
                        }
                    }
            );
        }

        initBankList();

        return v;
    }

    private void initBankList(){
        if(bankSelectSpinner != null){
            spinnerAdapter =
                    new BankAccountSpinnerAdapter(
                            new BankItem[0],
                            getContext()
                    );

            bankSelectSpinner.setAdapter(spinnerAdapter);

            loadBankList();
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle(R.string.fragment_title_bank_account);
    }

    private void loadBankList(){
        showLoading(true);

        Call<BankListResponse> call =
                Injector.Api().getAvailableBanks(MadarApplication.getUser().getCountryID());

        call.enqueue(
                new CallbackWithRetry<BankListResponse>(
                        5,
                        3000,
                        call,
                        new onRequestFailure() {
                            @Override
                            public void onFailure() {
                                Log.e("receipts Request", "failure");
                            }
                        }
                ) {
                    @Override
                    public void onResponse(Call<BankListResponse> call, Response<BankListResponse> response) {
                        BankListResponse r = response.body();
                        if(r != null){
                            BankItem[] bankItems = r.getBankItems();
                            if(spinnerAdapter != null){
                                spinnerAdapter.updateData(bankItems);
                            }
                        }

                        showLoading(false);
                    }
                }
        );
    }

    private void saveBankAccount(){
        if(accountHolderNameEditText != null &&
                accountNumberEditText != null &&
                spinnerAdapter != null &&
                bankSelectSpinner != null)
        {
            String accHolderName = null;
            String accNumber = null;

            try {
                accHolderName = accountHolderNameEditText.getText().toString();
                accNumber = accountNumberEditText.getText().toString();

                if(!accHolderName.isEmpty() && !accNumber.isEmpty()){
                    String secret = null;

                    try {
                        secret = MadarApplication.getInstance().getPrefManager().getUser().getSecret();
                    }
                    catch (Exception e){
                        // ignore
                    }

                    if(secret != null){
                        showLoading(true);

                        final Call<AddBankResponse> receiptItemCall = Injector.Api()
                                .saveBankAccount(
                                        "AddUBank",
                                        secret,
                                        accHolderName,
                                        selectedBankId,
                                        accNumber
                                );

                        receiptItemCall.enqueue(new CallbackWithRetry<AddBankResponse>(
                                5,
                                3000,
                                receiptItemCall,
                                new onRequestFailure() {
                                    @Override
                                    public void onFailure() {
                                        Log.e("add bank Request", "failure");
                                    }
                                })
                        {

                            @Override
                            public void onResponse(Call<AddBankResponse> call, Response<AddBankResponse> response) {
                                boolean success = false;
                                AddBankResponse addBankResponse = response.body();
                                if(addBankResponse != null) {
                                    ResponseItem responseItem = addBankResponse.getStatus();
                                    if(responseItem.getStatusCode() == 200){
                                         success = true;
                                    }
                                }

                                if(success){
                                    Toast.makeText(
                                            getContext(),
                                            R.string.bank_add_success,
                                            Toast.LENGTH_SHORT
                                    ).show();
                                    getActivity().onBackPressed();
                                }
                                else{
                                    Toast.makeText(
                                            getContext(),
                                            R.string.bank_add_failure,
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }

                                showLoading(false);
                            }
                        });
                    }
                }
            }
            catch (Exception e){
                // failure
                Toast.makeText(
                        getContext(),
                        R.string.bank_add_failure,
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }
}
