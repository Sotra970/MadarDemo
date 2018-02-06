package com.madar.madardemo.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ahmed on 8/29/2017.
 */

public class ResponseItem {

    @SerializedName("statusCode")
    private int statusCode;

    @SerializedName("message")
    private String message;


    public ResponseItem(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public  boolean isSuccessful(){
        return  statusCode == 200 ? true : false ;
    }
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
