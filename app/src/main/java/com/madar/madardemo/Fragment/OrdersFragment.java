package com.madar.madardemo.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.madar.madardemo.Adapter.OrderFragmentsAdapter;
import com.madar.madardemo.AppManger.MadarApplication;
import com.madar.madardemo.Fragment.Abstract.TitledFragment;
import com.madar.madardemo.Interface.NoConn;
import com.madar.madardemo.Model.OrderItem;
import com.madar.madardemo.Model.ServerResponse.UserOrdersResponse;
import com.madar.madardemo.R;
import com.madar.madardemo.Service.CallbackWithRetry;
import com.madar.madardemo.Service.Injector;
import com.madar.madardemo.Service.onRequestFailure;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyUtils;


/**
 * Created by Ahmed on 8/23/2017.
 */

public class OrdersFragment extends TitledFragment {

    @BindView(R.id.progressView)
    View progrssView ;
    @BindView(R.id.container)
    View containerHolder ;

    public static OrdersFragment getInstance() {
        return new OrdersFragment();
    }

    private ArrayList<OrderItem> orderItems;

    private OrderFragmentsAdapter fragmentsAdapter;

    private TabLayout tabLayout;
    private  ViewPager viewPager;

    private ArrayList<BaseOrderListFragment> fragments;

    private View v;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*if(v == null){

        }*/

        v = inflater.inflate(R.layout.fragment_orders, container, false);

        ButterKnife.bind(this, v) ;
        initLoading(this.progrssView, this.containerHolder) ;

        viewPager = (ViewPager)
                v.findViewById(R.id.fragment_orders_view_pager);

        fragments = new ArrayList<>();
        fragments.add(BaseOrderListFragment.getInstance(null));
        fragments.add(BaseOrderListFragment.getInstance(null));
        fragments.add(BaseOrderListFragment.getInstance(null));

        fragmentsAdapter = new OrderFragmentsAdapter(getChildFragmentManager(), fragments, getContext());

        viewPager.setAdapter(fragmentsAdapter);
        viewPager.setOffscreenPageLimit(3);

        tabLayout = (TabLayout) v.findViewById(R.id.fragment_orders_tab_layout);

        tabLayout.setupWithViewPager(viewPager);

        initTabLayout(tabLayout);

        return v;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void initTabLayout(final TabLayout tabLayout){
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if(tab != null){
                View v = getTabView(i);
                if(v != null){
                    tab.setCustomView(getTabView(i));
                }
            }
        }

        tabLayout.addOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        if(tab.getCustomView() != null){
                            View v = tab.getCustomView();
                            v.setBackgroundColor(
                                    ResourcesCompat.getColor(
                                            getResources(),
                                            R.color.yellow_button_background_selected,
                                            null
                                    )
                            );
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        if(tab.getCustomView() != null) {
                            View v = tab.getCustomView();
                            v.setBackgroundColor(
                                    ResourcesCompat.getColor(
                                            getResources(),
                                            R.color.yellow_button_background_unselected,
                                            null
                                    )
                            );
                        }
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        if(tab.getCustomView() != null){
                            View v = tab.getCustomView();
                            v.setBackgroundColor(
                                    ResourcesCompat.getColor(
                                            getResources(),
                                            R.color.yellow_button_background_selected,
                                            null
                                    )
                            );
                        }
                    }
                }
        );

        TabLayout.Tab first = tabLayout.getTabAt(0);
        if(first != null){
            first.select();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadOrdersList();
    }

    private View getTabView(int index){
        int textResId = -1;
        switch (index){
            case 0:
                textResId = R.string.text_all_orders;
                break;

            case 1:
                textResId = R.string.text_i_deliver;
                break;

            case 2:
                textResId = R.string.text_i_receive;
                break;
        }

        if(textResId != - 1){
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setLayoutParams(
                     new FrameLayout.LayoutParams(
                             ViewGroup.LayoutParams.MATCH_PARENT,
                             ViewGroup.LayoutParams.MATCH_PARENT
                     )
            );
            linearLayout.setGravity(Gravity.CENTER);

            TextView textView = new TextView(getContext());
            textView.setTextColor(
                    ResourcesCompat.getColor(
                            getResources(),
                            R.color.grey_700,
                            null
                    )
            );
            CalligraphyUtils.applyFontToTextView(getContext(), textView, "fonts/Changa-Bold.ttf");
            textView.setText(textResId);
            textView.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
            );

            linearLayout.addView(textView);

            return linearLayout;
        }

        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle(R.string.fragment_title_orders);
    }



    protected void loadOrdersList(){
        if(orderItems != null){
            showLoading(true);

            if(MadarApplication.getExecutorService() != null){
                MadarApplication.getExecutorService()
                        .submit(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        showOrderItems(orderItems);
                                    }
                                }
                        );
            }
        }

        else{
            String secret = null;

            try {
                secret = MadarApplication.getInstance().getPrefManager().getUser().getSecret();
            }
            catch (Exception e){
                // ignore
            }

            if(secret != null){
                showLoading(true);

                final Call<UserOrdersResponse> receiptItemCall = Injector.Api()
                        .getUserOrderInfo("getUserAllOrders", secret);

                receiptItemCall.enqueue(new CallbackWithRetry<UserOrdersResponse>(
                        CallbackWithRetry.HTTP_REQUEST_RETRY_COUNT,
                        CallbackWithRetry.HTTP_REQUEST_RETRY_INTERVAl_MILISECONDS,
                        receiptItemCall,
                        new onRequestFailure() {
                            @Override
                            public void onFailure() {
                                if(tabLayout != null){
                                    tabLayout.setVisibility(View.GONE);
                                }
                                Log.e("orders Request", "failure");
                                showNoConn(
                                        new NoConn() {
                                            @Override
                                            public void onRetry() {
                                                loadOrdersList();
                                            }
                                        }
                                );

                                showLoading(false);
                            }
                        })
                {

                    @Override
                    public void onResponse(Call<UserOrdersResponse> call, Response<UserOrdersResponse> response) {
                        final UserOrdersResponse userOrdersResponse = response.body();
                        if(userOrdersResponse != null){
                            orderItems = userOrdersResponse.getOrders();

                            if(MadarApplication.getExecutorService() != null){
                                MadarApplication.getExecutorService()
                                        .submit(
                                                new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        initOrderDates(orderItems);
                                                        showOrderItems(orderItems);
                                                    }
                                                }
                                        );
                            }
                        }
                    }
                });
            }
        }
    }

    private void initOrderDates(final ArrayList<OrderItem> orderItems){
        // do not call on ui thread
        if(orderItems != null && !orderItems.isEmpty()){
            for(OrderItem orderItem : orderItems){
                orderItem.initDate();
            }
        }
    }

    private void showOrderItems(final ArrayList<OrderItem> orderItems){
        final ArrayList<OrderItem> receiving = new ArrayList<OrderItem>();
        final ArrayList<OrderItem> sending = new ArrayList<OrderItem>();

        if(orderItems != null && !orderItems.isEmpty()){
            for(OrderItem orderItem : orderItems){
                if(orderItem.isReceiving()){
                    receiving.add(orderItem);
                }
                else{
                    sending.add(orderItem);
                }
            }
        }

        Handler uiHandler =
                new Handler(Looper.getMainLooper());

        uiHandler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        if(fragmentsAdapter != null){
                            if(fragments != null){
                                BaseOrderListFragment f;
                                for(int i = 0; i < fragments.size(); i++){
                                    f = fragments.get(i);
                                    switch (i){
                                        case 0:
                                            f.setOrderItems(orderItems);
                                            break;

                                        case 1:
                                            f.setOrderItems(sending);
                                            break;

                                        case 2:
                                            f.setOrderItems(receiving);
                                            break;
                                    }
                                }
                            }


                        }
                        showLoading(false);
                    }
                }
        );
    }
}
