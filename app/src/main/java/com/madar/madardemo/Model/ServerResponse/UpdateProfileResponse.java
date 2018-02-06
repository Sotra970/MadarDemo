package com.madar.madardemo.Model.ServerResponse;

import com.google.gson.annotations.SerializedName;
import com.madar.madardemo.Model.ResponseItem;

/**
 * Created by Ahmed on 10/15/2017.
 */

public class UpdateProfileResponse {

    @SerializedName("status")
    private ResponseItem status;

    public ResponseItem getStatus() {
        return status;
    }
}
