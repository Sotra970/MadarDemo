package com.madar.madardemo.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ahmed on 8/30/2017.
 */

public class CityModel  implements Serializable {

    String ID , Name ;
    @SerializedName("Type")
    public int eligible;

    public CityModel(String ID, String name) {
        this.ID = ID;
        Name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


    public  boolean shippingFromEdibility(){
        return  eligible == 1  ||  eligible == 3 ? true : false ;
    }


    public  boolean shippingToEdibility(){
        return  eligible == 2 || eligible == 3 ? true : false ;
    }
}
