package com.madar.madardemo.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ahmed on 8/19/2017.
 */

public class ReceiptItem extends ReceiptListItem{

    @SerializedName("Date")
    private String time;

    @SerializedName("Ship_Price")
    private String deliveryFees;

    @SerializedName("Products_Price")
    private String goodsPrice;

    @SerializedName("balance")
    private String credit;

    @SerializedName("Paid")
    private String paidAmount;



    public ReceiptItem(String time,
                       String deliveryFees,
                       String goodsPrice,
                       String credit,
                       String paidAmount)
    {
        this.time = time;
        this.deliveryFees = deliveryFees;
        this.goodsPrice = goodsPrice;
        this.credit = credit;
        this.paidAmount = paidAmount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDeliveryFees() {
        return deliveryFees;
    }

    public void setDeliveryFees(String deliveryFees) {
        this.deliveryFees = deliveryFees;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }


}
