package com.madar.madardemo.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.madar.madardemo.R;

/**
 * Created by Ahmed on 8/19/2017.
 */

public class ReceiptItemViewHolder extends RecyclerView.ViewHolder {

    public View background;
    public TextView dayOfMonthTextView;
    public TextView monthTextView;
    public TextView deliveryFeesTextView;
    public TextView goodsPriceTextView;
    public TextView creditTextView;
    public TextView paidAmountTextView;

    public ReceiptItemViewHolder(View itemView) {
        super(itemView);

        this.background = itemView.findViewById(R.id.receipt_list_item_background);

        this.dayOfMonthTextView = (TextView)
                itemView.findViewById(R.id.receipt_list_item_day_text_view);

        this.monthTextView = (TextView)
                itemView.findViewById(R.id.receipt_list_item_month_text_view);

        this.deliveryFeesTextView = (TextView)
                itemView.findViewById(R.id.receipt_list_item_delivery_fees_text_view);

        this.goodsPriceTextView = (TextView)
                itemView.findViewById(R.id.receipt_list_item_goods_price_text_view);

        this.creditTextView = (TextView)
                itemView.findViewById(R.id.receipt_list_item_credit_text_view);

        this.paidAmountTextView = (TextView)
                itemView.findViewById(R.id.receipt_list_item_paid_amount_text_view);
    }
}
