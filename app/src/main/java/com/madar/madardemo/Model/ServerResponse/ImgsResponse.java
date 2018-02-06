package com.madar.madardemo.Model.ServerResponse;

import com.google.gson.annotations.SerializedName;
import com.madar.madardemo.Model.ResponseItem;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sotra on 10/3/2017.
 */

public class ImgsResponse implements Serializable {

    @SerializedName("Data")
    private ArrayList<String> data;

    @SerializedName("status")
    private ResponseItem responseItem;


    public ResponseItem getResponseItem() {
        return responseItem;
    }

    public void setResponseItem(ResponseItem responseItem) {
        this.responseItem = responseItem;
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }
}
