package com.madar.madardemo.Model;

import java.io.Serializable;

/**
 * Created by sotra on 10/14/2017.
 */

public class SocialUser  implements Serializable{
    public static final int FACEBOOK_SOCIAL_TYPE = 1 ;
    public static final int TWITTER_SOCIAL_TYPE = 2 ;
    public static final int GOOGLE_SOCIAL_TYPE = 3 ;
    String name  , photo , email , uid  ;
    int type ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
