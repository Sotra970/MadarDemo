package com.madar.madardemo.Model.ServerResponse;

import com.google.gson.annotations.SerializedName;
import com.madar.madardemo.Model.ResponseItem;
import com.madar.madardemo.Model.TransactionInfoItem;

import java.util.ArrayList;

/**
 * Created by Ahmed on 10/5/2017.
 */

public class TransactionInfoResponse {

    @SerializedName("status")
    private ResponseItem status;

    @SerializedName("Data")
    private ArrayList<TransactionInfoItem> transactionInfoItems;

    public TransactionInfoResponse(ResponseItem status, ArrayList<TransactionInfoItem> transactionInfoItems) {
        this.status = status;
        this.transactionInfoItems = transactionInfoItems;
    }

    public ResponseItem getStatus() {
        return status;
    }

    public ArrayList<TransactionInfoItem> getTransactionInfoItems() {
        return transactionInfoItems;
    }
}
