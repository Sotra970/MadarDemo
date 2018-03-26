package com.madar.madardemo.Model;

import java.io.Serializable;

/**
 * Created by Sotra on 2/3/2018.
 */

public class CountryModel  implements Serializable{

    String ID , Name ,Code , Currency_Code , Currency ;
    String  Phone_Code , Phone_Numbers_Length ;

    public String getPhone_Code() {
        return Phone_Code;
    }

    public void setPhone_Code(String phone_Code) {
        Phone_Code = phone_Code;
    }

    public String getPhone_Numbers_Length() {
        return Phone_Numbers_Length;
    }

    public void setPhone_Numbers_Length(String phone_Numbers_Length) {
        Phone_Numbers_Length = phone_Numbers_Length;
    }

    public CountryModel(String ID, String name) {
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

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
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
