package com.madar.madardemo.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ahmed on 10/4/2017.
 */

public class DaySummaryItem {

    @SerializedName("Invoice_ID")
    private String orderId;

    @SerializedName("Ship_Price")
    private String DeliveryFees;

    @SerializedName("Products_Price")
    private String goodsPrice;


    @SerializedName("Date")
    private String orderTime;

    //@SerializedName("")
    private String orderState;

    public DaySummaryItem(String orderId, String deliveryFees, String goodsPrice, String orderTime, String orderState) {
        this.orderId = orderId;
        DeliveryFees = deliveryFees;
        this.goodsPrice = goodsPrice;
        this.orderTime = orderTime;
        this.orderState = orderState;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getDeliveryFees() {
        return DeliveryFees;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getOrderState() {
        return orderState;
    }
}
