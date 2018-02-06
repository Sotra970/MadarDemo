package com.madar.madardemo.Model;

/**
 * Created by Ahmed on 8/18/2017.
 */

public class NotificationItem {

    private String message;
    private long linuxTime;
    private boolean isRead;

    public NotificationItem(String message, long linuxTime, boolean isRead) {
        this.message = message;
        this.linuxTime = linuxTime;
        this.isRead = isRead;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getLinuxTime() {
        return linuxTime;
    }

    public void setLinuxTime(long linuxTime) {
        this.linuxTime = linuxTime;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
