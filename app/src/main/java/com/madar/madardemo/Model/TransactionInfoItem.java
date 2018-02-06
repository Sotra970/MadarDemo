package com.madar.madardemo.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ahmed on 10/5/2017.
 */

public class TransactionInfoItem {

    @SerializedName("Date")
    private String date;

    @SerializedName("Ship_Price")
    private String shipPrice;

    @SerializedName("Products_Price")
    private String productPrice;

    @SerializedName("balance")
    private String balance;

    public TransactionInfoItem(String date, String shipPrice, String productPrice, String balance) {
        this.date = date;
        this.shipPrice = shipPrice;
        this.productPrice = productPrice;
        this.balance = balance;
    }

    public String getDate() {
        return date;
    }

    public String getShipPrice() {
        return shipPrice;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getBalance() {
        return balance;
    }
}
