package com.madar.madardemo.Model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.madar.madardemo.R;

import butterknife.ButterKnife;

/**
 * Created by Ahmed on 10/4/2017.
 */

public class DaySummaryItemViewHolder extends RecyclerView.ViewHolder {

    public TextView orderIdTextView;
    public TextView deliveryFeesTextView;
    public TextView goodsPriceTextView;
    public TextView orderStateTextView;
    public TextView orderTimeTextView;

    public LinearLayout field_1;
    public LinearLayout field_2;
    public LinearLayout field_3;
    public LinearLayout field_4;
    public LinearLayout field_5;


    public DaySummaryItemViewHolder(View itemView) {
        super(itemView);
        this.orderIdTextView = (TextView) itemView.findViewById(R.id.order_number_text_view);
        this.deliveryFeesTextView = (TextView) itemView.findViewById(R.id.order_delivery_text_view);
        this.goodsPriceTextView = (TextView) itemView.findViewById(R.id.order_goods_price_text_view);
        this.orderStateTextView = (TextView) itemView.findViewById(R.id.order_state_text_view);
        this.orderTimeTextView = (TextView) itemView.findViewById(R.id.order_time_text_view);
        this.field_1 = (LinearLayout) itemView.findViewById(R.id.day_summary_row_field_1);
        this.field_2 = (LinearLayout) itemView.findViewById(R.id.day_summary_row_field_2);
        this.field_3 = (LinearLayout) itemView.findViewById(R.id.day_summary_row_field_3);
        this.field_4 = (LinearLayout) itemView.findViewById(R.id.day_summary_row_field_4);
        this.field_5 = (LinearLayout) itemView.findViewById(R.id.day_summary_row_field_5);

    }
}
