package com.madar.madardemo.Model.ServerResponse;

import com.google.gson.annotations.SerializedName;
import com.madar.madardemo.Model.FavLocationModel;
import com.madar.madardemo.Model.ResponseItem;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sotra on 10/3/2017.
 */

public class AvDatesResponse implements Serializable {

    @SerializedName("Data")
    public ArrayList<AvDatesResponseDataModel> data;

    @SerializedName("status")
    public ResponseItem responseItem;

    public  class  AvDatesResponseDataModel {

        public String day ;
        public ArrayList<AvDatesDayModel> data ;


    }


    public static class  AvDatesDayModel {

           public String Day_ID  ;
           public String Day_Num  ;
           public String Day;
           public String To ;
           public String From;
           public String Day_Date_DB;
           public String Day_Date;
           public String Day_Date_Ar;
           public String From_Ar;
           public String To_Ar;




    }


}
