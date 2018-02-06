package com.madar.madardemo.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.madar.madardemo.R;

/**
 * Created by Ahmed on 8/20/2017.
 */

public class ReceiptSummaryItemViewHolder extends RecyclerView.ViewHolder {

    public TextView pendingAmountsTextView;
    public TextView unpaidGoodsPricesTextView;
    public TextView lastWireTransferTextViewDate;
    public TextView lastWireTransferTextViewAmount;
    public TextView beforeLastWireTransferTextViewDate;
    public TextView beforeLastWireTransferTextViewAmount;
    public TextView deliveryFeesTextView;


    public ReceiptSummaryItemViewHolder(View itemView) {
        super(itemView);
        this.pendingAmountsTextView = (TextView)
                itemView.findViewById(R.id.receipt_summary_item_amounts_to_be_transferred_text_view);

        this.unpaidGoodsPricesTextView = (TextView)
                itemView.findViewById(R.id.receipt_summary_item_unreceived_goods_prices_text_view);

        this.deliveryFeesTextView = (TextView)
                itemView.findViewById(R.id.receipt_summary_item_delivery_unpaid_fees_text_view);

        this.lastWireTransferTextViewDate = (TextView)
                itemView.findViewById(R.id.receipt_summary_item_last_two_wire_transfers_text_view_1_date);

        this.beforeLastWireTransferTextViewAmount = (TextView)
                itemView.findViewById(R.id.receipt_summary_item_last_two_wire_transfers_text_view_2_amount);

        this.lastWireTransferTextViewAmount = (TextView)
                itemView.findViewById(R.id.receipt_summary_item_last_two_wire_transfers_text_view_1_amount);

        this.beforeLastWireTransferTextViewDate = (TextView)
                itemView.findViewById(R.id.receipt_summary_item_last_two_wire_transfers_text_view_2_date);
    }
}
