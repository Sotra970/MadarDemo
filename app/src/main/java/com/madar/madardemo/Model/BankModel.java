package com.madar.madardemo.Model;

import java.io.Serializable;

/**
 * Created by sotra on 10/20/2017.
 */

public class BankModel  implements Serializable{

    String ID  , Name  , account_number , account_holder_s_name ;

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

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getAccount_holder_s_name() {
        return account_holder_s_name;
    }

    public void setAccount_holder_s_name(String account_holder_s_name) {
        this.account_holder_s_name = account_holder_s_name;
    }
}
