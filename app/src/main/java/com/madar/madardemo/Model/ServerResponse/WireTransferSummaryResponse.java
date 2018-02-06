package com.madar.madardemo.Model.ServerResponse;

import com.google.gson.annotations.SerializedName;
import com.madar.madardemo.Model.ResponseItem;
import com.madar.madardemo.Model.WireTransferSummaryItem;

import java.util.ArrayList;

/**
 * Created by Ahmed on 10/4/2017.
 */

public class WireTransferSummaryResponse {

    @SerializedName("status")
    private ResponseItem status;

    @SerializedName("Data")
    private ArrayList<WireTransferSummaryItem> wireTransferSummaryItems;

    public WireTransferSummaryResponse(ResponseItem status, ArrayList<WireTransferSummaryItem> wireTransferSummaryItem) {
        this.status = status;
        this.wireTransferSummaryItems = wireTransferSummaryItem;
    }

    public ResponseItem getStatus() {
        return status;
    }

    public ArrayList<WireTransferSummaryItem> getWireTransferSummaryItems() {
        return wireTransferSummaryItems;
    }
}
