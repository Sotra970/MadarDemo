package com.madar.madardemo.Model.ServerResponse;

import com.google.gson.annotations.SerializedName;
import com.madar.madardemo.Model.FavLocationModel;
import com.madar.madardemo.Model.ProfileItem;
import com.madar.madardemo.Model.ResponseItem;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sotra on 10/3/2017.
 */

public class FavLocationResponse implements Serializable {

    @SerializedName("Data")
    private ArrayList<FavLocationModel> data;

    @SerializedName("status")
    private ResponseItem responseItem;

    public ArrayList<FavLocationModel> getData() {
        return data;
    }

    public void setData(ArrayList<FavLocationModel> data) {
        this.data = data;
    }

    public ResponseItem getResponseItem() {
        return responseItem;
    }

    public void setResponseItem(ResponseItem responseItem) {
        this.responseItem = responseItem;
    }
}
