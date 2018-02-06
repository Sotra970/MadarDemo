package com.madar.madardemo.Model;

/**
 * Created by Ahmed on 8/17/2017.
 */

public class NavDrawerPageItem {

    private int titleResId;
    private int drawableResId;

    public NavDrawerPageItem(int titleResId, int drawableResId) {
        this.titleResId = titleResId;
        this.drawableResId = drawableResId;
    }

    public int getTitleResId() {
        return titleResId;
    }

    public void setTitleResId(int titleResId) {
        this.titleResId = titleResId;
    }

    public int getDrawableResId() {
        return drawableResId;
    }

    public void setDrawableResId(int drawableResId) {
        this.drawableResId = drawableResId;
    }
}
