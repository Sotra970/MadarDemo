package com.madar.madardemo.Adapter;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madar.madardemo.Model.ReceiptItem;
import com.madar.madardemo.Model.ReceiptListItem;
import com.madar.madardemo.Model.ReceiptSummaryItem;
import com.madar.madardemo.Model.ServerResponse.ReceiptItemResponse;
import com.madar.madardemo.R;
import com.madar.madardemo.Util.TimeUtils;
import com.madar.madardemo.ViewHolder.ReceiptItemViewHolder;
import com.madar.madardemo.ViewHolder.ReceiptSummaryItemViewHolder;

import java.util.ArrayList;

/**
 * Created by Ahmed on 8/19/2017.
 */

public class ReceiptListAdapter extends GenericAdapter<ReceiptListItem> {

    private final static int VIEW_TYPE_SUMMARY_ITEM = 0;
    private final static int VIEW_TYPE_RECEIPT_ITEM = 1;


    public ReceiptListAdapter(ArrayList<ReceiptListItem> receiptItems, Context context) {
        super(receiptItems, context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_RECEIPT_ITEM){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.receipt_list_item, parent, false);

            return new ReceiptItemViewHolder(v);
        }
        else{
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.receipt_summary_item, parent, false);

            return new ReceiptSummaryItemViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ReceiptListItem receiptListItem = getItem(position);

        if (receiptListItem instanceof ReceiptSummaryItem) {
            ReceiptSummaryItem receiptSummaryItem = (ReceiptSummaryItem) receiptListItem;

            ReceiptSummaryItemViewHolder holder = (ReceiptSummaryItemViewHolder) viewHolder;

            ReceiptItemResponse.ReceiptData.NotPaidData notPaidData =
                    receiptSummaryItem.getNotPaidData();

            ArrayList<ReceiptItemResponse.ReceiptData.LastTransactionsData> lastTransactionsDatas =
                    receiptSummaryItem.getLastTransactionsDatas();

           if(notPaidData != null){

               String unpaidGoodsFeesText = notPaidData.getTotalProductsPrice();
               String pendingFeesText = notPaidData.getTotalBalance();
               String deliveryFeesText = notPaidData.getTotalShipPrice();

               holder.unpaidGoodsPricesTextView.setText(unpaidGoodsFeesText == null || unpaidGoodsFeesText.isEmpty() ? "0.00" : unpaidGoodsFeesText );
               holder.pendingAmountsTextView.setText(pendingFeesText == null || pendingFeesText.isEmpty() ? "0.00" : pendingFeesText );
               holder.deliveryFeesTextView.setText(deliveryFeesText == null || deliveryFeesText.isEmpty() ? "0.00" : deliveryFeesText );

           }

           if(lastTransactionsDatas != null && !lastTransactionsDatas.isEmpty()){
               ReceiptItemResponse.ReceiptData.LastTransactionsData d1 = null;
               try {
                   d1 = lastTransactionsDatas.get(0);
               }
               catch (Exception ignored){}

               if(d1 != null){
                   holder.lastWireTransferTextViewAmount.setText(d1.getShowAmount() == null ? "0" : d1.getShowAmount());
                   holder.lastWireTransferTextViewDate.setText(d1.getShowDate() == null ? "--" : d1.getShowDate());
               }

               ReceiptItemResponse.ReceiptData.LastTransactionsData d2 = null;
               try {
                   d2 = lastTransactionsDatas.get(1);
               }
               catch (Exception ignored){}

               if(d2 != null){
                   holder.beforeLastWireTransferTextViewAmount.setText(d2.getShowAmount() == null ? "0" : d2.getShowAmount());
                   holder.beforeLastWireTransferTextViewDate.setText(d2.getShowDate() == null ? "--" : d2.getShowDate());
               }
           }
        }
        else {
            ReceiptItem receiptItem = (ReceiptItem) receiptListItem;

            if(receiptItem != null){
                ReceiptItemViewHolder holder = (ReceiptItemViewHolder) viewHolder;

                String time = receiptItem.getTime();

                if(time != null){

                    holder.dayOfMonthTextView.setText(
                            TimeUtils.getDayOfMonth(time, TimeUtils.FORMAT_DATE_WITH_TIME, TimeUtils.LANGUAGE_EN)
                    );

                    holder.monthTextView.setText(
                            TimeUtils.getMonthOfYear(time, TimeUtils.FORMAT_DATE_WITH_TIME, TimeUtils.LANGUAGE_EN, TimeUtils.LENGTH_SHORT)
                    );
                }

                holder.goodsPriceTextView.setText(receiptItem.getGoodsPrice());
                holder.deliveryFeesTextView.setText(receiptItem.getDeliveryFees());
                holder.creditTextView.setText(receiptItem.getCredit());

                if(position%2 != 0){
                    holder.background.setBackgroundColor(
                            ResourcesCompat.getColor(
                                    getContext().getResources(),
                                    R.color.app_yellow_4,
                                    null
                            )
                    );
                }
                else{
                    holder.background.setBackgroundColor(
                            ResourcesCompat.getColor(
                                    getContext().getResources(),
                                    R.color.white,
                                    null
                            )
                    );
                }

            }

            super.onBindViewHolder(viewHolder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        ReceiptListItem listItem = getItems().get(position);
        if(listItem instanceof ReceiptSummaryItem){
            return VIEW_TYPE_SUMMARY_ITEM;
        }
        else{
            return VIEW_TYPE_RECEIPT_ITEM;
        }
    }
}
