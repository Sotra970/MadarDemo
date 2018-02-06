package com.madar.madardemo.Model.ServerResponse;

import com.google.gson.annotations.SerializedName;
import com.madar.madardemo.Model.ProfileItem;
import com.madar.madardemo.Model.ResponseItem;

import java.util.ArrayList;

/**
 * Created by Ahmed on 8/31/2017.
 */

public class UserLoginResponse {

    @SerializedName("Data")
    private ArrayList<ProfileItem> profileItem;

    @SerializedName("status")
    private ResponseItem responseItem;

    @SerializedName("Secret")
    private String secret;

    public UserLoginResponse(ArrayList<ProfileItem> userInfo, ResponseItem responseItem, String secret) {
        this.profileItem = userInfo;
        this.responseItem = responseItem;
        this.secret = secret;
    }

    public ArrayList<ProfileItem> getUserInfo() {
        return profileItem;
    }

    public void setUserInfo(ArrayList<ProfileItem> userInfo) {
        this.profileItem = userInfo;
    }

    public ResponseItem getResponseItem() {
        return responseItem;
    }

    public void setResponseItem(ResponseItem responseItem) {
        this.responseItem = responseItem;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
