package com.madar.madardemo.Model;

import java.io.Serializable;

/**
 * Created by Sotra on 2/3/2018.
 */

public class CountryModel  implements Serializable{

    String ID , Name ,Code , Currency_Code , Currency ;

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
