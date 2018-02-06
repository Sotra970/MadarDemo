package com.madar.madardemo.Model;

/**
 * Created by Ahmed on 8/23/2017.
 */

public abstract class BaseOrderItem {

    public final static int STATUE_NEW_ORDER = 0;
    public final static int STATUE_PROBLEM_IN_RECEIVING = 1;
    public final static int STATUE_ORDER_RECEIVED = 2;
    public final static int STATUE_READY_FOR_SHIPMENT = 3;
    public final static int STATUE_RECEIVING_IS_BEING_AGREED_ON = 4;
    public final static int STATUE_IS_DELIVERING = 5;

    private String address;
    private int statue;
    private String orderId;
    private String date;

    public BaseOrderItem(String address, int statue, String orderId, String date)
    {
        this.address = address;
        this.statue = statue;
        this.orderId = orderId;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatue() {
        return statue;
    }

    public void setStatue(int statue) {
        this.statue = statue;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
