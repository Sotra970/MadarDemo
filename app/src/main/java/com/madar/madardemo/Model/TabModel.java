package com.madar.madardemo.Model;


import android.support.v4.app.Fragment;

import com.madar.madardemo.Fragment.Abstract.AddOrderBaseFragment;


/**
 * Created by sotra on 9/27/2017.
 */

public class TabModel {

    AddOrderBaseFragment fragment ;
    String title ;

    public TabModel(AddOrderBaseFragment fragment, String title) {
        this.fragment = fragment;
        this.title = title;
    }

    public AddOrderBaseFragment getFragment() {
        return fragment;
    }

    public void setFragment(AddOrderBaseFragment fragment) {
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
