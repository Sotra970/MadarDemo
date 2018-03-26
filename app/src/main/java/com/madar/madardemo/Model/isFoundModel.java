package com.madar.madardemo.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sotra on 9/30/2017.
 */

public class isFoundModel implements Serializable {


    @SerializedName("status")
    private ResponseItem responseItem;

    public ResponseItem getResponseItem() {
        return responseItem;
    }
}