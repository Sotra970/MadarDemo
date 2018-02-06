package com.madar.madardemo.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.madar.madardemo.Adapter.DaySummaryListAdapter;
import com.madar.madardemo.AppManger.MadarApplication;
import com.madar.madardemo.Fragment.Abstract.TitledFragment;
import com.madar.madardemo.Interface.NoConn;
import com.madar.madardemo.Model.DaySummaryItem;
import com.madar.madardemo.Model.ServerResponse.DaySummaryResponse;
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
 * Created by Ahmed on 10/4/2017.
 */

public class DaySummaryFragment extends TitledFragment {

    private DaySummaryListAdapter daySummaryListAdapter;
    private String date;

    @BindView(R.id.progressView)
    View progrssView ;
    @BindView(R.id.container)
    View containerHolder ;

    public static DaySummaryFragment getInstance(String date) {
        DaySummaryFragment f = new DaySummaryFragment();
        f.date = date;
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_day_summary, container, false);
        ButterKnife.bind(this,v);
        initLoading(this.progrssView, this.containerHolder);

        String month = TimeUtils.getMonthOfYear(date, TimeUtils.FORMAT_DATE_NO_TIME, TimeUtils.LANGUAGE_AR, TimeUtils.LENGTH_LONG);
        String day = TimeUtils.getDayOfMonth(date, TimeUtils.FORMAT_DATE_NO_TIME, TimeUtils.LANGUAGE_EN);

        TextView textView = (TextView) v.findViewById(R.id.fragment_day_summary_date_text_view);
        textView.setText(day + " " + month);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.fragment_day_summary_recycler_view);
        initRecyclerView(recyclerView);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadDaySummaryData();
    }

    private void initRecyclerView(RecyclerView recyclerView){
        if(recyclerView != null){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

            daySummaryListAdapter = new DaySummaryListAdapter(
                    null,
                    getContext()
            );

            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(daySummaryListAdapter);
        }
    }

    private void loadDaySummaryData(){
        String secret = null;

        try {
            secret = MadarApplication.getInstance().getPrefManager().getUser().getSecret();
        }
        catch (Exception e){
            // ignore
        }

        if(secret != null){
            showLoading(true);

            final Call<DaySummaryResponse> receiptItemCall = Injector.Api()
                    .getDaySummary("GetUserInvoicesDate", secret, date);

            receiptItemCall.enqueue(new CallbackWithRetry<DaySummaryResponse>(
                    5,
                    3000,
                    receiptItemCall,
                    new onRequestFailure() {
                        @Override
                        public void onFailure() {
                            Log.e("receipts Request", "failure");
                            showNoConn(
                                    new NoConn() {
                                        @Override
                                        public void onRetry() {
                                            loadDaySummaryData();
                                        }
                                    }
                            );

                            showLoading(false);
                        }
                    })
            {

                @Override
                public void onResponse(Call<DaySummaryResponse> call, Response<DaySummaryResponse> response) {
                    boolean ok = false;

                    DaySummaryResponse daySummaryResponse = response.body();
                    if(daySummaryResponse != null){
                        ArrayList<DaySummaryItem> daySummaryItems =
                                daySummaryResponse.getSummaryItems();

                        if(daySummaryListAdapter != null){
                            if(daySummaryItems != null && !daySummaryItems.isEmpty()){
                                daySummaryItems.add(0, null);
                            }

                            daySummaryListAdapter.updateData(daySummaryItems);

                            ok = !daySummaryListAdapter.isDataSetEmpty();
                        }
                    }

                    showEmptyLayout(!ok);

                    showLoading(false);
                }
            });
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle(R.string.fragment_title_day_summary);
    }
}
