package com.madar.madardemo.Model;

import com.madar.madardemo.Model.ServerResponse.ReceiptItemResponse;

import java.util.ArrayList;

/**
 * Created by Ahmed on 8/20/2017.
 */

public class ReceiptSummaryItem extends ReceiptListItem{

    private ReceiptItemResponse.ReceiptData.NotPaidData notPaidData;
    private ArrayList<ReceiptItemResponse.ReceiptData.LastTransactionsData> lastTransactionsDatas;

    public ReceiptSummaryItem(ReceiptItemResponse.ReceiptData.NotPaidData unpaidGoodsPrices,
                              ArrayList<ReceiptItemResponse.ReceiptData.LastTransactionsData> lastTransactionsDatas)
    {
        this.notPaidData = unpaidGoodsPrices;
        this.lastTransactionsDatas = lastTransactionsDatas;
    }

    public ReceiptItemResponse.ReceiptData.NotPaidData getNotPaidData() {
        return notPaidData;
    }

    public ArrayList<ReceiptItemResponse.ReceiptData.LastTransactionsData> getLastTransactionsDatas() {
        return lastTransactionsDatas;
    }
}
