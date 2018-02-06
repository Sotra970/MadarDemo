package com.madar.madardemo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sotra on 9/30/2017.
 */

public class AddOrderResponseModel implements Serializable {


    @SerializedName("status")
    private ResponseItem responseItem;

    @SerializedName("Data")
    private ArrayList<OrderItem> orderItems ;


    public ResponseItem getResponseItem() {
        return responseItem;
    }

    public void setResponseItem(ResponseItem responseItem) {
        this.responseItem = responseItem;
    }

    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}