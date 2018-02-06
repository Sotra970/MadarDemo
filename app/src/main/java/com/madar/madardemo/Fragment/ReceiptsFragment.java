package com.madar.madardemo.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madar.madardemo.Adapter.ReceiptListAdapter;
import com.madar.madardemo.AppManger.MadarApplication;
import com.madar.madardemo.Fragment.Abstract.TitledFragment;
import com.madar.madardemo.Interface.AdapterItemClickListener;
import com.madar.madardemo.Model.ReceiptItem;
import com.madar.madardemo.Model.ReceiptListItem;
import com.madar.madardemo.Model.ReceiptSummaryItem;
import com.madar.madardemo.Model.ServerResponse.ReceiptItemResponse;
import com.madar.madardemo.R;
import com.madar.madardemo.Service.CallbackWithRetry;
import com.madar.madardemo.Service.Injector;
import com.madar.madardemo.Service.onRequestFailure;
import com.madar.madardemo.Util.TimeUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Ahmed on 8/19/2017.
 */

public class ReceiptsFragment extends TitledFragment {

    @BindView(R.id.progressView)
    View progrssView ;
    @BindView(R.id.container)
    View containerHolder ;


    private ReceiptListAdapter receiptListAdapter;

    public static ReceiptsFragment getInstance() {
        return new ReceiptsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_receipts, container, false);
        ButterKnife.bind(this , v) ;
        initLoading(this.progrssView , this.containerHolder) ;

        RecyclerView recyclerView = (RecyclerView)
                v.findViewById(R.id.receipts_fragment_recycler_view);

        initRecyclerView(recyclerView);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle(R.string.fragment_title_receipts);
    }

    private void loadReceipts(){
        String secret = null;

        try {
            secret = MadarApplication.getInstance().getPrefManager().getUser().getSecret();
        }
        catch (Exception e){
            // ignore
        }

        if(secret != null){
            showLoading(true);

            final Call<ReceiptItemResponse> receiptItemCall = Injector.Api()
                    .getInvoices("GetUserInvoices", secret);

            receiptItemCall.enqueue(new CallbackWithRetry<ReceiptItemResponse>(
                    5,
                    3000,
                    receiptItemCall,
                    new onRequestFailure() {
                        @Override
                        public void onFailure() {
                            Log.e("receipts Request", "failure");
                        }
                    })
            {

                @Override
                public void onResponse(Call<ReceiptItemResponse> call, Response<ReceiptItemResponse> response) {
                    ReceiptItemResponse receiptItemResponse = response.body();
                    if(receiptItemResponse != null) {
                        final ReceiptItemResponse.ReceiptData data = receiptItemResponse.getReceiptData();

                        if(data != null){

                            final ArrayList<ReceiptItem> receiptItems = data.getReceiptListItems();

                            if(MadarApplication.getExecutorService() != null){
                                MadarApplication.getExecutorService()
                                        .submit(
                                                new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        ReceiptItemResponse.ReceiptData.NotPaidData notPaidData =
                                                                data.getNotPaidData();

                                                        final ArrayList<ReceiptItemResponse.ReceiptData.LastTransactionsData> lastTransactionsDatas
                                                                = data.getLastTransactionsData();


                                                        if(lastTransactionsDatas != null && !lastTransactionsDatas.isEmpty()){
                                                            for(ReceiptItemResponse.ReceiptData.LastTransactionsData transactionsData : lastTransactionsDatas){
                                                                transactionsData.setShowDate();
                                                                transactionsData.setShowAmount();
                                                            }
                                                        }

                                                        ReceiptSummaryItem receiptSummaryItem =
                                                                new ReceiptSummaryItem(notPaidData, lastTransactionsDatas);

                                                        final ArrayList<ReceiptListItem> receipts = new ArrayList<>();


                                                        receipts.add(receiptSummaryItem);
                                                        if(receiptItems != null){
                                                            receipts.addAll(receiptItems);
                                                        }

                                                        Handler uiHandler = new Handler(Looper.getMainLooper());
                                                        uiHandler.post(
                                                                new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        receiptListAdapter.updateData(receipts);
                                                                        showLoading(false);
                                                                    }
                                                                }
                                                        );
                                                    }
                                                }
                                        );
                            }
                        }
                    }
                }
            });
        }
    }

    private void initRecyclerView(final RecyclerView recyclerView){
        if(recyclerView != null)
        {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

            receiptListAdapter =
                    new ReceiptListAdapter(null, getContext());

            receiptListAdapter.setAdapterItemClickListener(
                    new AdapterItemClickListener() {
                        @Override
                        public void onAdapterItemClicked(View itemView) {
                            if(itemView != null){
                                int index = recyclerView.getChildAdapterPosition(itemView);
                                if(index != RecyclerView.NO_POSITION){
                                    ReceiptItem receiptItem = null;
                                    try {
                                        receiptItem = (ReceiptItem) receiptListAdapter.getItem(index);
                                    }
                                    catch (Exception e){
                                        // ignore
                                    }

                                    if(receiptItem != null){
                                        String date = receiptItem.getTime();
                                        String ss = TimeUtils.removeTimeFromDate(date);
                                        if(ss != null && !ss.isEmpty()){
                                            showFragment(
                                                    DaySummaryFragment.getInstance(ss),
                                                    true
                                            );
                                        }
                                    }
                                }
                            }
                        }
                    }
            );

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(receiptListAdapter);

            loadReceipts();
        }
    }
}
