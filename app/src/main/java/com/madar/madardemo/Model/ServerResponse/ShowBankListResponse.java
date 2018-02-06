package com.madar.madardemo.Model.ServerResponse;

import com.google.gson.annotations.SerializedName;
import com.madar.madardemo.Model.BankItem;
import com.madar.madardemo.Model.BankModel;
import com.madar.madardemo.Model.ResponseItem;

import java.util.ArrayList;

/**
 * Created by Ahmed on 8/29/2017.
 */

public class ShowBankListResponse {

    @SerializedName("status")
    private ResponseItem responseItem;

    @SerializedName("Data")
    private ArrayList<BankModel> data;

    public ResponseItem getResponseItem() {
        return responseItem;
    }

    public void setResponseItem(ResponseItem responseItem) {
        this.responseItem = responseItem;
    }

    public ArrayList<BankModel> getData() {
        return data;
    }

    public void setData(ArrayList<BankModel> data) {
        this.data = data;
    }
}
