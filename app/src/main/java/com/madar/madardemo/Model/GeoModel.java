package com.madar.madardemo.Model;

import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Created by sotra on 9/30/2017.
 */

public class GeoModel implements Serializable {
    Geocoder geocoder;
    List<Address> addresses;
    String address    = "not available";
    String city       = "not available";
    String state      = "not available";
    String country    = "not available";
    String postalCode = "not available";
    String knownName  = "not available";
    LatLng latLng ;

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public GeoModel(Geocoder geocoder  , LatLng latLng ) {
        this.geocoder = geocoder;
        this.latLng = latLng ;

        try {
            Log.e("address_model" ,"latlaung : " +latLng.latitude +"--" +  latLng.longitude) ;

            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

          address    = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
          city       = addresses.get(0).getLocality();
          state      = addresses.get(0).getAdminArea();
          country    = addresses.get(0).getCountryName();
          postalCode = addresses.get(0).getPostalCode();
          knownName  = addresses.get(0).getFeatureName();
            Log.e("address_model" ,"city : " + city) ;
            Log.e("address_model" ,"address : " + address) ;
            Log.e("address_model" ,"state : " + state) ;
            Log.e("address_model" ,"country : " + country) ;
            Log.e("address_model" ,"postalCode : " + postalCode) ;
            Log.e("address_model" ,"knownName : " + knownName) ;

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public Geocoder getGeocoder() {
        return geocoder;
    }

    public void setGeocoder(Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getKnownName() {
        return knownName;
    }

    public void setKnownName(String knownName) {
        this.knownName = knownName;
    }
}
