package com.madar.madardemo.Model.ServerResponse;

import com.google.gson.annotations.SerializedName;
import com.madar.madardemo.Model.OrderItem;
import com.madar.madardemo.Model.ResponseItem;

import java.util.ArrayList;

/**
 * Created by Ahmed on 10/6/2017.
 */

public class UserOrdersResponse {

    @SerializedName("status")
    private ResponseItem status;

    @SerializedName("Data")
    private ArrayList<OrderItem> orders;

    public UserOrdersResponse(ResponseItem status, ArrayList<OrderItem> orders) {
        this.status = status;
        this.orders = orders;
    }

    public ResponseItem getStatus() {
        return status;
    }

    public ArrayList<OrderItem> getOrders() {
        return orders;
    }
}
