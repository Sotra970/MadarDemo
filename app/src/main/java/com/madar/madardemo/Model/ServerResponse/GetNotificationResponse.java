package com.madar.madardemo.Model.ServerResponse;

import com.google.gson.annotations.SerializedName;
import com.madar.madardemo.Model.NotificationListItem;
import com.madar.madardemo.Model.ResponseItem;

import java.util.ArrayList;

/**
 * Created by Ahmed on 10/12/2017.
 */

public class GetNotificationResponse {

    @SerializedName("status")
    private ResponseItem responseItem;

    @SerializedName("Data")
    private ArrayList<NotificationListItem> notificationListItems;

    public GetNotificationResponse(ResponseItem responseItem, ArrayList<NotificationListItem> notificationListItems) {
        this.responseItem = responseItem;
        this.notificationListItems = notificationListItems;
    }

    public ResponseItem getResponseItem() {
        return responseItem;
    }

    public void setResponseItem(ResponseItem responseItem) {
        this.responseItem = responseItem;
    }

    public ArrayList<NotificationListItem> getNotificationListItems() {
        return notificationListItems;
    }

    public void setNotificationListItems(ArrayList<NotificationListItem> notificationListItems) {
        this.notificationListItems = notificationListItems;
    }
}
