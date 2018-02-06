package com.madar.madardemo.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.madar.madardemo.R;

/**
 * Created by Ahmed on 9/15/2017.
 */

public class WireTransferSummaryViewHolder extends RecyclerView.ViewHolder {

    public View background;
    public TextView dayTextView;
    public TextView monthTextView;
    public TextView transferId;
    public TextView transferAmount;

    public WireTransferSummaryViewHolder(View itemView) {
        super(itemView);

        this.background =
                itemView.findViewById(R.id.transfer_summary_item_background);

        this.dayTextView =
                (TextView) itemView.findViewById(R.id.transfer_summary_item_day_text_view);

        this.monthTextView =
                (TextView) itemView.findViewById(R.id.transfer_summary_item_month_text_view);

        this.transferId =
                (TextView) itemView.findViewById(R.id.tranfer_summary_item_id_text_view);

        this.transferAmount =
                (TextView) itemView.findViewById(R.id.tranfer_summary_item_amount_text_view);

    }
}
