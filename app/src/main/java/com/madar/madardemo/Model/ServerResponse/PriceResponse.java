package com.madar.madardemo.Model.ServerResponse;

import com.google.gson.annotations.SerializedName;
import com.madar.madardemo.Model.ResponseItem;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sotra on 10/3/2017.
 */

public class PriceResponse implements Serializable {

    @SerializedName("Data")
    public ArrayList<PriceModel> data;

    @SerializedName("status")
    public ResponseItem responseItem;





    public  class PriceModel  {
        public  String  Price ;
        public  String  Ship_Size ;
        public  String  Main_Price ;
        public  String  Discount_Percent ;


    }


}
