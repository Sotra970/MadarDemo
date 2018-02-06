package com.madar.madardemo.Model.ServerResponse;

import com.google.gson.annotations.SerializedName;
import com.madar.madardemo.Model.ResponseItem;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sotra on 10/3/2017.
 */

public class ProfileImgsResponse implements Serializable {

    @SerializedName("Data")
    private ArrayList<ProfileImgModel> data;

    @SerializedName("status")
    private ResponseItem responseItem;


    public ResponseItem getResponseItem() {
        return responseItem;
    }

    public void setResponseItem(ResponseItem responseItem) {
        this.responseItem = responseItem;
    }

    public ArrayList<ProfileImgModel> getData() {
        return data;
    }

    public void setData(ArrayList<ProfileImgModel> data) {
        this.data = data;
    }
}
