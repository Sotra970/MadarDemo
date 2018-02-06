package com.madar.madardemo.Model;

import java.io.Serializable;

/**
 * Created by sotra on 10/3/2017.
 */

public class FavLocationModel implements Serializable {

    String Longitude , Latitude ,CityID , District ,Details  , Secure   , ID ;
    int Default ;
    final String Request="AddUAddress";

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getCityID() {
        return CityID;
    }

    public void setCityID(String cityID) {
        CityID = cityID;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getSecure() {
        return Secure;
    }

    public void setSecure(String secure) {
        Secure = secure;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    public int getDefault() {
        return Default;
    }

    public void setDefault(int aDefault) {
        Default = aDefault;
    }
}
