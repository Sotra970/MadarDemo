package com.madar.madardemo.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ahmed on 9/15/2017.
 */

public class WireTransferSummaryItem {

    @SerializedName("id")
    private String id;

    @SerializedName("Amount")
    private String amount;

    @SerializedName("From")
    private String date;

    public WireTransferSummaryItem(String id, String amount, String date) {
        this.id = id;
        this.amount = amount;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}
