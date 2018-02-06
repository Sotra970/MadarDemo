package com.madar.madardemo.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madar.madardemo.Adapter.WireTransferSummaryAdapter;
import com.madar.madardemo.AppManger.MadarApplication;
import com.madar.madardemo.Fragment.Abstract.TitledFragment;
import com.madar.madardemo.Interface.AdapterItemClickListener;
import com.madar.madardemo.Interface.NoConn;
import com.madar.madardemo.Model.ServerResponse.WireTransferSummaryResponse;
import com.madar.madardemo.Model.WireTransferSummaryItem;
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
 * Created by Ahmed on 9/15/2017.
 */

public class TransferSummaryFragment extends TitledFragment {

    @BindView(R.id.progressView)
    View progrssView ;
    @BindView(R.id.container)
    View containerHolder ;

    private View v;

    private WireTransferSummaryAdapter wireTransferSummaryAdapter;

    public static TransferSummaryFragment getInstance() {
        return new TransferSummaryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(v == null){
            v = inflater.inflate(R.layout.fragment_bank_transfer_summary, container, false);

            ButterKnife.bind(this, v) ;
            initLoading(this.progrssView, this.containerHolder) ;

            RecyclerView recyclerView = (RecyclerView)
                    v.findViewById(R.id.fragment_bank_transfer_summary_recycler_view);
            initRecyclerView(recyclerView);

            super.onCreateView(inflater, container, savedInstanceState);
        }

        return v;
    }

    private void initRecyclerView(final RecyclerView recyclerView){
        if(recyclerView != null){
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            wireTransferSummaryAdapter =
                    new WireTransferSummaryAdapter(
                            null,
                            getContext(),
                            new AdapterItemClickListener() {
                                @Override
                                public void onAdapterItemClicked(View itemView) {
                                    if(itemView != null){
                                        int index = recyclerView.getChildAdapterPosition(itemView);
                                        if(index != RecyclerView.NO_POSITION){

                                            WireTransferSummaryItem summaryItem =
                                                    wireTransferSummaryAdapter.getItem(index);

                                            if(summaryItem != null){
                                                String no = summaryItem.getId();
                                                showInfo(no);
                                            }
                                        }
                                    }
                                }
                            }
                    );

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(wireTransferSummaryAdapter);

            loadData();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle(R.string.fragment_title_transfer_summary);
    }

    private void loadData(){
        String secret = null;

        try {
            secret = MadarApplication.getInstance().getPrefManager().getUser().getSecret();
        }
        catch (Exception e){
            // ignore
        }

        if(secret != null){
            showLoading(true);

            Call<WireTransferSummaryResponse> transferSummaryResponseCall =
                    Injector.Api().getWireTransfers("GetTransfares", secret);

            transferSummaryResponseCall.enqueue(
                    new CallbackWithRetry<WireTransferSummaryResponse>(
                            5,
                            3000,
                            transferSummaryResponseCall,
                            new onRequestFailure() {
                                @Override
                                public void onFailure() {
                                    Log.e("transfers Request", "failure");
                                    showNoConn(
                                            new NoConn() {
                                                @Override
                                                public void onRetry() {
                                                    loadData();
                                                }
                                            }
                                    );

                                    showLoading(false);
                                }
                            }
                    )
                    {
                        @Override
                        public void onResponse(Call<WireTransferSummaryResponse> call, Response<WireTransferSummaryResponse> response) {
                            boolean ok = false;
                            WireTransferSummaryResponse transferSummaryResponse = response.body();

                            if(transferSummaryResponse != null){
                                ArrayList<WireTransferSummaryItem> items =
                                        transferSummaryResponse.getWireTransferSummaryItems();

                                if(wireTransferSummaryAdapter != null){
                                    wireTransferSummaryAdapter.updateData(items);

                                    ok = !wireTransferSummaryAdapter.isDataSetEmpty();
                                }

                                showEmptyLayout(!ok);

                                showLoading(false);
                            }
                        }
                    }
            );
        }
    }

    private void showInfo(String transactionNo){
        if(transactionNo != null){
            Fragment fragment = FragmentTransferInfo.getInstance(transactionNo);
            showFragment(fragment, true);
        }
    }


}
