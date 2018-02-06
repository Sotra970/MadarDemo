package com.madar.madardemo.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.madar.madardemo.Adapter.BankArrayAdapter;
import com.madar.madardemo.Adapter.FavsArrayAdapter;
import com.madar.madardemo.AppManger.Config;
import com.madar.madardemo.AppManger.MadarApplication;
import com.madar.madardemo.Fragment.Abstract.AddOrderBaseFragment;
import com.madar.madardemo.Fragment.Abstract.BaseFragment;
import com.madar.madardemo.Interface.NoConn;
import com.madar.madardemo.Model.BankModel;
import com.madar.madardemo.Model.FavLocationModel;
import com.madar.madardemo.Model.ServerResponse.AddBankResponse;
import com.madar.madardemo.Model.ServerResponse.ConfirmationResponse;
import com.madar.madardemo.Model.ServerResponse.ShowBankListResponse;
import com.madar.madardemo.R;
import com.madar.madardemo.Service.CallbackWithRetry;
import com.madar.madardemo.Service.Injector;
import com.madar.madardemo.Service.onRequestFailure;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowUSerBanksFragment extends BaseFragment {


    public ShowUSerBanksFragment() {
        // Required empty public constructor
    }


    @BindView(R.id.progressView)
    View progrssView;
    @BindView(R.id.container)
    View containerHolder;

    View layout_res ;

    @BindView(R.id.list_view)
    ListView listView ;

    @BindView(R.id.no_items)
    TextView no_items ;

    public static Fragment getInstance() {
        return new ShowUSerBanksFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (layout_res == null){
            layout_res =  inflater.inflate(R.layout.fragment_show_bank, container, false);
            ButterKnife.bind(this, layout_res) ;
            initLoading(this.progrssView, this.containerHolder);
            showLoading(true);
        }

        return layout_res ;

    }

    @Override
    public void onResume() {
        super.onResume();
        get_user_banks();
        Log.e("ShowUSerBanksFragment" , "onresume") ;
    }

    private void get_user_banks() {
        Call<ShowBankListResponse> call  = Injector.Api().showBanks(MadarApplication.getInstance().getPrefManager().getUser().getSecret());
        call.enqueue(new CallbackWithRetry<ShowBankListResponse>(5, 3000, call, new onRequestFailure() {
            @Override
            public void onFailure() {
                showNoConn(new NoConn() {
                    @Override
                    public void onRetry() {
                        get_user_banks();
                    }
                });
            }
        }) {
            @Override
            public void onResponse(Call<ShowBankListResponse> call, Response<ShowBankListResponse> response) {
                if (response.body().getResponseItem() !=null && response.body().getResponseItem().isSuccessful()){
                    if (response.body().getData()!=null && !response.body().getData().isEmpty()){
                        update(response.body().getData());
                        no_items.setVisibility(View.GONE);
                        showLoading(false);
                    }else {
                        no_items.setVisibility(View.VISIBLE);
                        showLoading(false);
                    }
                }else{
                    no_items.setVisibility(View.VISIBLE);
                    showLoading(false);
                }

            }
        });
    }


    private void update(final ArrayList<BankModel> data) {
        showLoading(false);
        BankArrayAdapter<BankModel> arrayAdapter = new BankArrayAdapter<BankModel>(getContext() , data ) {


            @Override
            public void delete(final BankModel object , int pos) {
                showLoading(true);
                delete_bank(object);
            }


        };
        listView.setAdapter(arrayAdapter);

    }

    private void delete_bank(final BankModel bankModel ) {

            Call<ConfirmationResponse> call = Injector.Api().deleteBank(
                    MadarApplication.getInstance().getPrefManager().getUser().getSecret(),
                    bankModel.getID()
            );
            call.enqueue(new CallbackWithRetry<ConfirmationResponse>(5, 3000, call, new onRequestFailure() {
                @Override
                public void onFailure() {
                    showNoConn(new NoConn() {
                        @Override
                        public void onRetry() {
                            delete_bank(bankModel);
                        }
                    });
                    showLoading(false);
                }
            }) {
                @Override
                public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {
                    if (response.body().getResponseItem() !=null &&response.body().getResponseItem().isSuccessful()){
                        get_user_banks();
                        return;
                    }
                    showLoading(false);
                }
            });
    }



    @OnClick(R.id.add_bank)
    void add_bank(){
        showFragment(AddBankAccountFragment.getInstance() , true);
    }


}
