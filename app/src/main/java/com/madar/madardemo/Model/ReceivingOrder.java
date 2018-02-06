package com.madar.madardemo.Model;

/**
 * Created by Ahmed on 8/23/2017.
 */

public class ReceivingOrder extends BaseOrderItem {

    private String receiverTitle;

    public ReceivingOrder(String address, int statue, String orderId, String date, String receiverTitle) {
        super(address, statue, orderId, date);
        this.receiverTitle = receiverTitle;
    }

    public String getReceiverTitle() {
        return receiverTitle;
    }

    public void setReceiverTitle(String receiverTitle) {
        this.receiverTitle = receiverTitle;
    }
}
