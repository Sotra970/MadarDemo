package com.madar.madardemo.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ahmed on 10/10/2017.
 */

public class OrderCase {

    @SerializedName("id")
    private String id;

    @SerializedName("name_of_the_case")
    private String title;

    public OrderCase(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }
}
