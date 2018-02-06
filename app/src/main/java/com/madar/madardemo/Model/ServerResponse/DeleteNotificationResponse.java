package com.madar.madardemo.Model.ServerResponse;

import com.google.gson.annotations.SerializedName;
import com.madar.madardemo.Model.ResponseItem;

/**
 * Created by Ahmed on 10/16/2017.
 */

public class DeleteNotificationResponse {

    @SerializedName("status")
    private ResponseItem status;

    public ResponseItem getStatus() {
        return status;
    }
}
