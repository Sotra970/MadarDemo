package com.madar.madardemo.AppManger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.IntentCompat;
import android.util.Log;

import com.madar.madardemo.Activity.SplashActivity;
import com.madar.madardemo.Model.ProfileItem;


public class MyPreferenceManager {

    private String TAG = MyPreferenceManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "trainning";




    private String ID;
    private String Name;
    private String FirstName;
    private String LastName;
    private String Phone;
    private String EMail;
    private String CityID;
    private String CityName;
    private String District;
    private String CommercialRecord;
    private String Image;
    private String State;
    private String Link;
    private String storeTitle;
    private String commercialId;
    private String password;
    private String Secret;



    // All Shared Preferences Keys
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_FIRST= "user_first";
    private static final String KEY_USER_LAST= "user_last";
    private static final String KEY_USER_PHONE = "user_phone";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_CityID = "user_cityid";
    private static final String KEY_USER_CityName = "user_city";
    private static final String KEY_USER_COUNTRY_ID = "country_id";
    private static final String KEY_USER_COUNTRY_NAME = "country_name";
    private static final String KEY_USER_District = "user_dis";
    private static final String KEY_USER_IMG = "user_img";
    private static final String KEY_USER_Secret = "user_secret";
    private static final String KEY_USER_PASS = "user_pass";
    private static final String KEY_CURRENCY_CODE= "curency";
    private static final String KEY_CURRENCY = "curency_code";

    public static final String KEY_INCREMENT_NOTFICATiON = "KEY_INCREMENT_NOTFICATiON";




    // Constructor
    public MyPreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void storeUser(ProfileItem user) {
        editor.clear();
        editor.commit();

        editor.putString(KEY_USER_ID, user.getID());
        editor.putString(KEY_USER_Secret, user.getSecret());
        editor.putString(KEY_USER_NAME, user.getName());
        editor.putString(KEY_USER_FIRST, user.getFirstName());
        editor.putString(KEY_USER_LAST, user.getLastName());
        editor.putString(KEY_USER_EMAIL , user.getEMail());
        editor.putString(KEY_USER_PHONE , user.getPhone());
        editor.putString(KEY_USER_IMG , user.getImage());
        editor.putString(KEY_USER_CityID , user.getCityID());
        editor.putString(KEY_USER_CityName, user.getCityName());
        editor.putString(KEY_USER_COUNTRY_ID ,  user.getCountryID());
        editor.putString(KEY_USER_COUNTRY_NAME, user.getCountryName());
        editor.putString(KEY_CURRENCY ,  user.getCurrency());
        editor.putString(KEY_CURRENCY_CODE, user.getCurrency_Code());
        editor.putString(KEY_USER_District , user.getDistrict());
        editor.putString(KEY_USER_PASS , user.getPass());
        editor.commit();
        Log.e(TAG, "User is stored in shared preferences. " + user.getSecret() +"--" + user.getID() + "  " +user.getCountryID() );
    }

    public ProfileItem getUser() {
        if (pref.getString(KEY_USER_Secret, null) != null) {

             String ID;
             String Name;
             String FirstName;
             String LastName;
             String Phone;
             String EMail;
             String CityID;
             String CityName;
            String CountryID;
            String CountryName;
             String District;
             String Image;
             String pass;
             String Secret;
             String curency;
             String currency_code;



            Secret = pref.getString(KEY_USER_Secret, null);
            ID = pref.getString(KEY_USER_ID, null);
            Name = pref.getString(KEY_USER_NAME, " ");
            FirstName = pref.getString(KEY_USER_FIRST, " ");
            LastName = pref.getString(KEY_USER_LAST, " ");
            Phone  = pref.getString(KEY_USER_PHONE, " ");
            EMail  = pref.getString(KEY_USER_EMAIL, " ");
            CityID  = pref.getString(KEY_USER_CityID, " ");
            CityName  = pref.getString(KEY_USER_CityName, " ");
            CountryID  = pref.getString(KEY_USER_COUNTRY_ID, " ");
            CountryName  = pref.getString(KEY_USER_COUNTRY_NAME, " ");
            currency_code  = pref.getString(KEY_CURRENCY_CODE  , " ");
            curency  = pref.getString(KEY_USER_COUNTRY_NAME, " ");
            District  = pref.getString(KEY_USER_District, "");
            Image  = pref.getString(KEY_USER_IMG,Config.temp_url);
            pass  = pref.getString(KEY_USER_PASS,"");


            ProfileItem userModel = new ProfileItem();
            userModel.setSecret(Secret);
            userModel.setID(ID);
            userModel.setName(Name);
            userModel.setFirstName(FirstName);
            userModel.setLastName(LastName);
            userModel.setPhone(Phone);
            userModel.setEMail(EMail);
            userModel.setCityID(CityID);
            userModel.setCityName(CityName);
            userModel.setCountryID(CountryID);
            userModel.setCountryName(CountryName);
            userModel.setCurrency(curency);
            userModel.setCurrency_Code(currency_code);
            userModel.setDistrict(District);
            userModel.setImage(Image);
            userModel.setPass(pass);

            return userModel;
        }
        return null;
    }


    public void clear() {
        editor.clear();
        editor.commit();

        Intent intent = new Intent(_context, SplashActivity.class);
        ComponentName cn = intent.getComponent();
        Intent mainIntent = Intent  .makeRestartActivityTask(cn);
        _context.startActivity(mainIntent);
    }



    public int  get_notfication() {
        int prev = pref.getInt(KEY_INCREMENT_NOTFICATiON, 0);
        return prev;
    }



    public void INCREMENT_NOTFICATiON() {
        int prev = pref.getInt(KEY_INCREMENT_NOTFICATiON, 0);
        prev++ ;
        editor.putInt(KEY_INCREMENT_NOTFICATiON , prev) ;
        editor.commit();
    }

    public void CLEAR_NOTFICATiON() {
        editor.putInt(KEY_INCREMENT_NOTFICATiON , 0) ;
        editor.commit();
    }

}
