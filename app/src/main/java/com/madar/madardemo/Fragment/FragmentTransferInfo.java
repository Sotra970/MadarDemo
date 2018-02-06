package com.madar.madardemo.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madar.madardemo.Adapter.TransferInfoAdapter;
import com.madar.madardemo.AppManger.MadarApplication;
import com.madar.madardemo.Fragment.Abstract.TitledFragment;
import com.madar.madardemo.Interface.NoConn;
import com.madar.madardemo.Model.ServerResponse.TransactionInfoResponse;
import com.madar.madardemo.Model.TransactionInfoItem;
import com.madar.madardemo.R;
import com.madar.madardemo.Service.CallbackWithRetry;
import com.madar.madardemo.Service.Injector;
import com.madar.madardemo.Service.onRequestFailure;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Ahmed on 10/5/2017.
 */

public class FragmentTransferInfo extends TitledFragment{

    @BindView(R.id.progressView)
    View progrssView ;
    @BindView(R.id.container)
    View containerHolder ;


    private String transactionNumber;
    private TransferInfoAdapter transferInfoAdapter;

    public static FragmentTransferInfo getInstance(String transferNo) {
        FragmentTransferInfo f = new FragmentTransferInfo();
        f.transactionNumber = transferNo;
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_transfer_info, container, false);

        ButterKnife.bind(this , v) ;
        initLoading(this.progrssView , this.containerHolder) ;

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.fragment_transfer_info_recycler_view);
        initRecyclerView(recyclerView);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadTransactionInfo();
    }

    private void initRecyclerView(final RecyclerView recyclerView){
        if(recyclerView != null){
            LinearLayoutManager la = new LinearLayoutManager(getContext());
            transferInfoAdapter = new TransferInfoAdapter(
                    null,
                    getContext()
            );

            recyclerView.setLayoutManager(la);
            recyclerView.setAdapter(transferInfoAdapter);
        }
    }

    private void loadTransactionInfo(){
        String secret = null;

        try {
            secret = MadarApplication.getInstance().getPrefManager().getUser().getSecret();
        }
        catch (Exception e){
            // ignore
        }

        if(secret != null){
            showLoading(true);

            Call<TransactionInfoResponse> callable =
                    Injector.Api().getTransactionInfo(
                            "GetTransfareDetails",
                            secret,
                            transactionNumber
                    );

            callable.enqueue(
                    new CallbackWithRetry<TransactionInfoResponse>(
                            5,
                            3000,
                            callable,
                            new onRequestFailure() {
                                @Override
                                public void onFailure() {
                                    Log.e("transaction info Req", "failure");
                                    showNoConn(
                                            new NoConn() {
                                                @Override
                                                public void onRetry() {
                                                    loadTransactionInfo();
                                                }
                                            }
                                    );

                                    showLoading(false);
                                }
                            })
                    {
                        @Override
                        public void onResponse(Call<TransactionInfoResponse> call, Response<TransactionInfoResponse> response) {
                            boolean ok = false;

                            TransactionInfoResponse res = response.body();
                            if(res != null){
                                ArrayList<TransactionInfoItem> transactionInfoItems =
                                        res.getTransactionInfoItems();
                                if(transferInfoAdapter != null){
                                    transferInfoAdapter.updateData(transactionInfoItems);

                                    ok = !transferInfoAdapter.isDataSetEmpty();
                                }
                            }

                            showEmptyLayout(!ok);

                            showLoading(false);
                        }
                    }
            );
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle(R.string.fragment_title_transfer_info);
    }
}
