package com.madar.madardemo.Model.ServerResponse;

import com.google.gson.annotations.SerializedName;
import com.madar.madardemo.Model.DaySummaryItem;
import com.madar.madardemo.Model.ResponseItem;

import java.util.ArrayList;

/**
 * Created by Ahmed on 10/6/2017.
 */

public class DaySummaryResponse {

    @SerializedName("status")
    private ResponseItem status;

    @SerializedName("Data")
    private ArrayList<DaySummaryItem> summaryItems;

    public DaySummaryResponse(ResponseItem status, ArrayList<DaySummaryItem> summaryItems) {
        this.status = status;
        this.summaryItems = summaryItems;
    }

    public ResponseItem getStatus() {
        return status;
    }

    public ArrayList<DaySummaryItem> getSummaryItems() {
        return summaryItems;
    }
}
