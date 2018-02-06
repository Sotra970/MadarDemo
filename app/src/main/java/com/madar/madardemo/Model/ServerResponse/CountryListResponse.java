package com.madar.madardemo.Model.ServerResponse;

import com.google.gson.annotations.SerializedName;
import com.madar.madardemo.Model.CityModel;
import com.madar.madardemo.Model.CountryModel;
import com.madar.madardemo.Model.ResponseItem;

import java.util.ArrayList;

/**
 * Created by Ahmed on 8/30/2017.
 */

public class CountryListResponse {

    @SerializedName("Data")
    private ArrayList<CountryModel> Data;

    @SerializedName("status")
    private ResponseItem status;


    public ArrayList<CountryModel> getData() {
        return Data;
    }

    public void setData(ArrayList<CountryModel> data) {
        Data = data;
    }

    public ResponseItem getStatus() {
        return status;
    }

    public void setStatus(ResponseItem status) {
        this.status = status;
    }
}
