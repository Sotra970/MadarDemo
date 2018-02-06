package com.madar.madardemo.Loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.madar.madardemo.Model.BaseOrderItem;
import com.madar.madardemo.Model.ReceivingOrder;

import java.util.ArrayList;

/**
 * Created by Ahmed on 8/28/2017.
 */

public class OrdersLoader extends AsyncTaskLoader<ArrayList<BaseOrderItem>>{

    public OrdersLoader(Context context) {
        super(context);
    }

    @Override
    public ArrayList<BaseOrderItem> loadInBackground() {
        ArrayList<BaseOrderItem> orderItems = new ArrayList<>();
        orderItems.add(
                new ReceivingOrder("Al Ahram", BaseOrderItem.STATUE_NEW_ORDER, "4353-45443", "12 Nov", "Mohamed mohamed")
        );
        orderItems.add(
                new ReceivingOrder("Al Ahram", BaseOrderItem.STATUE_IS_DELIVERING, "4353-45443", "12 Nov", "Mohamed mohamed")
        );
        orderItems.add(
                new ReceivingOrder("Al Ahram", BaseOrderItem.STATUE_ORDER_RECEIVED, "4353-45443", "12 Nov", "Mohamed mohamed")
        );
        orderItems.add(
                new ReceivingOrder("Al Ahram", BaseOrderItem.STATUE_PROBLEM_IN_RECEIVING, "4353-45443", "12 Nov", "Mohamed mohamed")
        );
        orderItems.add(
                new ReceivingOrder("Al Ahram", BaseOrderItem.STATUE_RECEIVING_IS_BEING_AGREED_ON, "4353-45443", "12 Nov", "Mohamed mohamed")
        );

        return orderItems;
    }
}
