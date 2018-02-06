package com.madar.madardemo.Model.ServerResponse;

import com.google.gson.annotations.SerializedName;
import com.madar.madardemo.Model.ResponseItem;
import com.madar.madardemo.Model.OrderCase;

import java.util.ArrayList;

/**
 * Created by Ahmed on 10/10/2017.
 */

public class GetOrderCasesResponse {

    @SerializedName("status")
    private ResponseItem status;

    @SerializedName("Data")
    private ArrayList<OrderCase> orderCases;

    public GetOrderCasesResponse(ResponseItem status, ArrayList<OrderCase> orderCases) {
        this.status = status;
        this.orderCases = orderCases;
    }

    public ResponseItem getStatus() {
        return status;
    }

    public ArrayList<OrderCase> getOrderCases() {
        return orderCases;
    }
}
