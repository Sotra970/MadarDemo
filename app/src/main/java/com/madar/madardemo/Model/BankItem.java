package com.madar.madardemo.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ahmed on 8/29/2017.
 */

public class BankItem {

    @SerializedName("ID")
    private String id;

    @SerializedName("Name")
    private String title;

    public BankItem(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
