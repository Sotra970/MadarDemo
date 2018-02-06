package com.madar.madardemo.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.madar.madardemo.R;

/**
 * Created by Ahmed on 10/5/2017.
 */

public class TransferInfoItemViewHolder extends RecyclerView.ViewHolder{

    public TextView day;
    public TextView month;
    public TextView balance;
    public TextView productPrice;
    public TextView deliveryFees;
    public View background;

    public TransferInfoItemViewHolder(View itemView) {
        super(itemView);
        this.background = itemView.findViewById(R.id.transfer_info_item_background);
        this.balance = (TextView) itemView.findViewById(R.id.transfer_info_item_balance_text_view);
        this.day = (TextView) itemView.findViewById(R.id.transfer_info_item_day_text_view);
        this.month = (TextView) itemView.findViewById(R.id.transfer_info_item_month_text_view);
        this.deliveryFees = (TextView) itemView.findViewById(R.id.transfer_info_item_delivery_fees_text_view);
        this.productPrice = (TextView) itemView.findViewById(R.id.transfer_info_item_product_price_text_view);
    }
}
