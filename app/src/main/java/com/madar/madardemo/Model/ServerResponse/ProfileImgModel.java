package com.madar.madardemo.Model.ServerResponse;

import java.io.Serializable;

/**
 * Created by sotra on 10/16/2017.
 */

 public  class ProfileImgModel implements Serializable {
    String id , path ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
