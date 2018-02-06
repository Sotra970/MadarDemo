package com.madar.madardemo.Adapter;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madar.madardemo.Interface.AdapterItemClickListener;
import com.madar.madardemo.Model.WireTransferSummaryItem;
import com.madar.madardemo.R;
import com.madar.madardemo.Util.TimeUtils;
import com.madar.madardemo.ViewHolder.WireTransferSummaryViewHolder;

import java.util.ArrayList;

/**
 * Created by Ahmed on 9/15/2017.
 */

public class WireTransferSummaryAdapter extends GenericAdapter<WireTransferSummaryItem>{


    public WireTransferSummaryAdapter(ArrayList<WireTransferSummaryItem> items, Context context, AdapterItemClickListener adapterItemClickListener) {
        super(items, context, adapterItemClickListener);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transfer_summary_item, parent, false);
        return new WireTransferSummaryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WireTransferSummaryItem summaryItem = getItem(position);

        WireTransferSummaryViewHolder viewHolder = (WireTransferSummaryViewHolder) holder;

        if(summaryItem != null){
            viewHolder.transferId.setText(summaryItem.getId());
            viewHolder.transferAmount.setText(summaryItem.getAmount());
            viewHolder.monthTextView.setText(TimeUtils.getMonthOfYear(summaryItem.getDate(),
                    TimeUtils.FORMAT_DATE_WITH_TIME, TimeUtils.LANGUAGE_EN, TimeUtils.LENGTH_SHORT));
            viewHolder.dayTextView.setText(TimeUtils.getDayOfMonth(summaryItem.getDate(), TimeUtils.FORMAT_DATE_WITH_TIME,
                    TimeUtils.LANGUAGE_EN));

            if(position%2 == 0){
                viewHolder.background.setBackgroundColor(
                        ResourcesCompat.getColor(
                                getContext().getResources(),
                                R.color.app_yellow_4,
                                null
                        )
                );
            }
            else{
                viewHolder.background.setBackgroundColor(
                        ResourcesCompat.getColor(
                                getContext().getResources(),
                                R.color.transparent,
                                null
                        )
                );
            }

            super.onBindViewHolder(holder, position);
        }
    }
}
