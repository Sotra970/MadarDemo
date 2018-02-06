package com.madar.madardemo.Model.ServerResponse;

import com.google.gson.annotations.SerializedName;
import com.madar.madardemo.Model.FavLocationModel;
import com.madar.madardemo.Model.ResponseItem;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sotra on 10/3/2017.
 */

public class MapLinkFetchResponse implements Serializable {

    @SerializedName("Data")
    private LatLungModel data;

    @SerializedName("status")
    private ResponseItem responseItem;


    public LatLungModel getData() {
        return data;
    }

    public void setData(LatLungModel data) {
        this.data = data;
    }

    public ResponseItem getResponseItem() {
        return responseItem;
    }

    public void setResponseItem(ResponseItem responseItem) {
        this.responseItem = responseItem;
    }

    public class  LatLungModel {
        double Latitude , Longitude ;

        public double getLatitude() {
            return Latitude;
        }

        public void setLatitude(double latitude) {
            Latitude = latitude;
        }

        public double getLongitude() {
            return Longitude;
        }

        public void setLongitude(double longitude) {
            Longitude = longitude;
        }
    }
}
