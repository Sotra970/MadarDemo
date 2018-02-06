package com.madar.madardemo.Model.ServerResponse;

import com.google.gson.annotations.SerializedName;
import com.madar.madardemo.Model.ResponseItem;

/**
 * Created by Ahmed on 10/9/2017.
 */

public class AddBankResponse {

    @SerializedName("status")
    private ResponseItem status;

    public AddBankResponse(ResponseItem status) {
        this.status = status;
    }

    public ResponseItem getStatus() {
        return status;
    }
}
