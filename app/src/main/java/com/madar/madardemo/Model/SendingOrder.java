package com.madar.madardemo.Model;

/**
 * Created by Ahmed on 8/23/2017.
 */

public class SendingOrder extends BaseOrderItem {

    private String senderTitle;

    public SendingOrder(String address, int statue, String orderId, String receiver, String senderTitle) {
        super(address, statue, orderId, receiver);
        this.senderTitle = senderTitle;
    }

    public String getSenderTitle() {
        return senderTitle;
    }

    public void setSenderTitle(String senderTitle) {
        this.senderTitle = senderTitle;
    }
}
