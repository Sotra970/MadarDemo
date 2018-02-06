package com.madar.madardemo.Model.ServerResponse;

import com.google.gson.annotations.SerializedName;
import com.madar.madardemo.Model.ResponseItem;

/**
 * Created by Ahmed on 8/31/2017.
 */

public class ForgetResponse {


    @SerializedName("status")
    private ResponseItem responseItem;


    public ResponseItem getResponseItem() {
        return responseItem;
    }

    public void setResponseItem(ResponseItem responseItem) {
        this.responseItem = responseItem;
    }


}
