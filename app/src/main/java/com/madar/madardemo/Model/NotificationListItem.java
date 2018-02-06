package com.madar.madardemo.Model;

import com.google.gson.annotations.SerializedName;
import com.madar.madardemo.Util.TimeUtils;

/**
 * Created by Ahmed on 10/12/2017.
 */

public class NotificationListItem {

    @SerializedName("id")
    private String id;

    @SerializedName("date")
    private String date;

    @SerializedName("content")
    private String content;

    @SerializedName("seen")
    private String seen;

    @SerializedName("order_id")
    private String orderId;

    @SerializedName("action")
    private String action;
    @SerializedName("latlung")
    private String latlung;

    private static String[] seeConsts = new String[]{
        "0", //not seen
        "1" // seen
    };

    private String parsedTime;

    public NotificationListItem(String id, String date, String content, String seen, String orderId, String action) {
        this.id = id;
        this.date = date;
        this.content = content;
        this.seen = seen;
        this.orderId = orderId;
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void initDate(){
        if(date != null){
            this.parsedTime =
                    TimeUtils.removeDateFromDate(date);
        }
    }

    public String getContent() {
        return content;
    }

    public boolean isSeen() {
        return seen != null && seen.equals(seeConsts[1]);
    }

    public String getOrderId() {
        return orderId;
    }

    public String getAction() {
        return action;
    }

    public String getParsedTime() {
        return parsedTime;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getLatlung() {
        return latlung;
    }

    public void setLatlung(String latlung) {
        this.latlung = latlung;
    }
}
