package com.madar.madardemo.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.madar.madardemo.Activity.OrderDetailsActivity;
import com.madar.madardemo.Adapter.OrderListAdapter;
import com.madar.madardemo.Fragment.Abstract.BaseFragment;
import com.madar.madardemo.Interface.AdapterItemClickListener;
import com.madar.madardemo.Model.OrderItem;
import com.madar.madardemo.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ahmed on 8/28/2017.
 */

public class BaseOrderListFragment extends BaseFragment
{
    @BindView(R.id.progressView)
    View progrssView ;
    @BindView(R.id.container)
    View containerHolder ;

    private OrderListAdapter orderListAdapter;
    private RecyclerView recyclerView;

    private ArrayList<OrderItem> orderItems;

    public static BaseOrderListFragment getInstance(ArrayList<OrderItem> orderItems) {
        BaseOrderListFragment f = new BaseOrderListFragment();
        f.orderItems = orderItems;
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_base_order_list, container, false);

        ButterKnife.bind(this, v) ;
        initLoading(this.progrssView, this.containerHolder) ;

        recyclerView = (RecyclerView)
                v.findViewById(R.id.fragment_orders_recycler_view);

        initRecyclerView(recyclerView);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        initShowOrderItems();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private void initRecyclerView(final RecyclerView recyclerView){
        if(recyclerView != null){
            orderListAdapter =
                    new OrderListAdapter(null, getContext(), new AdapterItemClickListener() {
                                @Override
                                public void onAdapterItemClicked(View itemView) {
                                    if(recyclerView != null && itemView != null){
                                        int index = recyclerView.getChildAdapterPosition(itemView);
                                        if(index != RecyclerView.NO_POSITION){
                                            OrderItem orderItem =
                                                    orderListAdapter.getItem(index);

                                            if(orderItem != null){
                                                onOrderItemClicked(orderItem);
                                            }
                                        }
                                    }
                                }
                            }
                    );

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(orderListAdapter);
        }
    }

    protected void initShowOrderItems()
    {
        boolean dataShown = false;

        if(orderListAdapter != null){
            orderListAdapter.updateData(orderItems);

            dataShown = !orderListAdapter.isDataSetEmpty();
        }

        showEmptyLayout(!dataShown);
    }

    public void setOrderItems(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
        initShowOrderItems();
    }

    protected ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    private void onOrderItemClicked(OrderItem orderItem){
        if (orderItem!=null) ;
        Log.e("BaseOrderListFragment" , "onOrderItemClicked" + orderItem.toString()) ;
        Intent intent = new Intent(getContext(), OrderDetailsActivity.class);
        intent.putExtra("extra_order" , orderItem) ;
        startActivity(intent);
    }
}
