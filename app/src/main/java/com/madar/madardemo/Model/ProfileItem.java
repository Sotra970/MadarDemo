package com.madar.madardemo.Model;

import android.text.TextUtils;

import com.madar.madardemo.AppManger.Config;

import java.io.Serializable;

/**
 * Created by Ahmed on 8/18/2017.
 */

public class ProfileItem implements Serializable {




    private String ID;
    private String Name;
    private String FirstName;
    private String LastName;
    private String Phone;
    private String EMail;
    private String CityID;
    private String CityName;
    private String CountryID;
    private String CountryName;
    private String Currency_Code;
    private String Currency;
    private String CommercialRecord;
    private String Image;
    private String State;
    private String Link;
    private String storeTitle;
    private String District;
    private String commercialId;
    private String Pass;
    private String Secret;
    private String Activated;
    private String Request;

    private String FB_UID ;
    private String Twitter_UID ;
    private String Google_UID ;




    public String getRequest() {
        return Request;
    }

    public void setRequest(String request) {
        Request = request;
    }

    public static ProfileItem getDefault(){
        ProfileItem profileItem = new ProfileItem();
        profileItem.FirstName = "الاسم الاول";
        profileItem.LastName = "اسم العائلة";
        profileItem.storeTitle = "اسم المتجر";
        profileItem.Phone = "0123456789";
        profileItem.CityName = "المدينة";
        profileItem.District = "الحي";
        profileItem.commercialId = "0123456789";
        profileItem.Pass = "كلمة المرور";
        return profileItem;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public ProfileItem() {
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public String getCityID() {
        return CityID;
    }

    public void setCityID(String cityID) {
        CityID = cityID;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getCommercialRecord() {
        return CommercialRecord;
    }

    public void setCommercialRecord(String commercialRecord) {
        CommercialRecord = commercialRecord;
    }

    public String getImage() {
        return TextUtils.isEmpty(Image) || Image==null ? Config.temp_url :  Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getStoreTitle() {
        return storeTitle;
    }

    public void setStoreTitle(String storeTitle) {
        this.storeTitle = storeTitle;
    }

    public String getCommercialId() {
        return commercialId;
    }

    public void setCommercialId(String commercialId) {
        this.commercialId = commercialId;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        this.Pass = pass;
    }

    public String getSecret() {
        return Secret;
    }

    public void setSecret(String secret) {
        Secret = secret;
    }

    public String getActivated() {
        return Activated;
    }

    public void setActivated(String activated) {
        Activated = activated;
    }


    public String getFB_UID() {
        return FB_UID;
    }

    public void setFB_UID(String FB_UID) {
        this.FB_UID = FB_UID;
    }

    public String getTwitter_UID() {
        return Twitter_UID;
    }

    public void setTwitter_UID(String twitter_UID) {
        Twitter_UID = twitter_UID;
    }

    public String getGoogle_UID() {
        return Google_UID;
    }

    public void setGoogle_UID(String google_UID) {
        Google_UID = google_UID;
    }


    public String getCountryID() {
        return CountryID;
    }

    public void setCountryID(String countryID) {
        CountryID = countryID;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }


    public String getCurrency_Code() {
        return Currency_Code;
    }

    public void setCurrency_Code(String currency_Code) {
        Currency_Code = currency_Code;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }
}

