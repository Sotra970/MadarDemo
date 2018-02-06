package com.madar.madardemo.Model;

/**
 * Created by Ahmed on 8/21/2017.
 */

public class PreferenceItem {

    private int titleResId;
    private int imageResId;
    private boolean active;

    public PreferenceItem(int titleResId, int imageResId, boolean active) {
        this.titleResId = titleResId;
        this.active = active;
        this.imageResId = imageResId;
    }

    public int getTitleResId() {
        return titleResId;
    }

    public void setTitleResId(int titleResId) {
        this.titleResId = titleResId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
