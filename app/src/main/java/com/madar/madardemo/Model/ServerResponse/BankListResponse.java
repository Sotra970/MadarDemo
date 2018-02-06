package com.madar.madardemo.Model.ServerResponse;

import com.madar.madardemo.Model.BankItem;
import com.madar.madardemo.Model.ResponseItem;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ahmed on 8/29/2017.
 */

public class BankListResponse {

    @SerializedName("status")
    private ResponseItem responseItem;

    @SerializedName("Data")
    private BankItem[] bankItems;

    public BankListResponse(ResponseItem responseItem, BankItem[] bankItems) {
        this.responseItem = responseItem;
        this.bankItems = bankItems;
    }

    public ResponseItem getResponseItem() {
        return responseItem;
    }

    public void setResponseItem(ResponseItem responseItem) {
        this.responseItem = responseItem;
    }

    public BankItem[] getBankItems() {
        return bankItems;
    }

    public void setBankItems(BankItem[] bankItems) {
        this.bankItems = bankItems;
    }
}
