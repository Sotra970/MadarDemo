package com.madar.madardemo.Model.ServerResponse;

import com.google.gson.annotations.SerializedName;
import com.madar.madardemo.Model.FavLocationModel;
import com.madar.madardemo.Model.ResponseItem;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sotra on 10/3/2017.
 */

public class AddressDetailsResponse implements Serializable {

    @SerializedName("Data")
    private AddressDetailsData data;

    @SerializedName("status")
    private ResponseItem responseItem;

    public AddressDetailsData getData() {
        return data;
    }

    public void setData(AddressDetailsData data) {
        this.data = data;
    }

    public ResponseItem getResponseItem() {
        return responseItem;
    }

    public void setResponseItem(ResponseItem responseItem) {
        this.responseItem = responseItem;
    }


    public  class  AddressDetailsData implements Serializable {
        String Address , Country ;

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getCountry() {
            return Country;
        }

        public void setCountry(String country) {
            Country = country;
        }
    }
}
