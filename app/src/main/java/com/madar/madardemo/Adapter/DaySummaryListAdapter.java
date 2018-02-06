package com.madar.madardemo.Adapter;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madar.madardemo.Model.DaySummaryItem;
import com.madar.madardemo.Model.DaySummaryItemViewHolder;
import com.madar.madardemo.R;
import com.madar.madardemo.ViewHolder.UselessViewHolder;

import java.util.ArrayList;

/**
 * Created by Ahmed on 10/4/2017.
 */

public class DaySummaryListAdapter extends GenericAdapter<DaySummaryItem> {

    private int VIEW_TYPE_HEADER = 0;
    private int VIEW_TYPE_ROW = 1;

    private final static int EVEN_FIELD_LIGHT_BACKGROUND = R.color.white;
    private final static int EVEN_FIELD_DARK_BACKGROUND = R.color.table_row_color_1;

    private final static int ODD_FIELD_LIGHT_BACKGROUND = R.color.table_row_color_1;
    private final static int ODD_FIELD_DARK_BACKGROUND = R.color.table_row_color_2;

    public DaySummaryListAdapter(ArrayList<DaySummaryItem> items, Context context) {
        super(items, context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_HEADER){
            View v = LayoutInflater.from(getContext())
                    .inflate(R.layout.day_summary_header_row, parent, false);
            return new UselessViewHolder(v);
        }
        View v = LayoutInflater.from(getContext())
                .inflate(R.layout.day_summary_table_row, parent, false);
        return new DaySummaryItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position != 0){
            DaySummaryItemViewHolder daySummaryItemViewHolder = null;
            try {
                daySummaryItemViewHolder = (DaySummaryItemViewHolder) holder;
            }
            catch (Exception e){
                // ignore
            }

            if(daySummaryItemViewHolder != null){
                DaySummaryItem daySummaryItem = getItem(position);

                if(daySummaryItem != null){
                    daySummaryItemViewHolder.orderIdTextView.setText(daySummaryItem.getOrderId());
                    daySummaryItemViewHolder.deliveryFeesTextView.setText(daySummaryItem.getDeliveryFees());
                    daySummaryItemViewHolder.goodsPriceTextView.setText(daySummaryItem.getGoodsPrice());
                    daySummaryItemViewHolder.orderTimeTextView.setText(daySummaryItem.getOrderTime());
                    daySummaryItemViewHolder.orderStateTextView.setText(
                            getContext().getText(R.string.delivered)
                    );

                    setColors(daySummaryItemViewHolder, position);
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return VIEW_TYPE_HEADER;
        }
        return VIEW_TYPE_ROW;
    }

    private void setColors(DaySummaryItemViewHolder daySummaryItemViewHolder, int position){
        if(position%2 != 0){
            daySummaryItemViewHolder.field_1.setBackgroundColor(
                    ResourcesCompat.getColor(
                            getContext().getResources(),
                            EVEN_FIELD_LIGHT_BACKGROUND,
                            null
                    )
            );

            daySummaryItemViewHolder.field_2.setBackgroundColor(
                    ResourcesCompat.getColor(
                            getContext().getResources(),
                            EVEN_FIELD_DARK_BACKGROUND,
                            null
                    )
            );

            daySummaryItemViewHolder.field_3.setBackgroundColor(
                    ResourcesCompat.getColor(
                            getContext().getResources(),
                            EVEN_FIELD_LIGHT_BACKGROUND,
                            null
                    )
            );

            daySummaryItemViewHolder.field_4.setBackgroundColor(
                    ResourcesCompat.getColor(
                            getContext().getResources(),
                            EVEN_FIELD_DARK_BACKGROUND,
                            null
                    )
            );

            daySummaryItemViewHolder.field_5.setBackgroundColor(
                    ResourcesCompat.getColor(
                            getContext().getResources(),
                            EVEN_FIELD_LIGHT_BACKGROUND,
                            null
                    )
            );
        }
        else{
            daySummaryItemViewHolder.field_1.setBackgroundColor(
                    ResourcesCompat.getColor(
                            getContext().getResources(),
                            ODD_FIELD_LIGHT_BACKGROUND,
                            null
                    )
            );

            daySummaryItemViewHolder.field_2.setBackgroundColor(
                    ResourcesCompat.getColor(
                            getContext().getResources(),
                            ODD_FIELD_DARK_BACKGROUND,
                            null
                    )
            );

            daySummaryItemViewHolder.field_3.setBackgroundColor(
                    ResourcesCompat.getColor(
                            getContext().getResources(),
                            ODD_FIELD_LIGHT_BACKGROUND,
                            null
                    )
            );

            daySummaryItemViewHolder.field_4.setBackgroundColor(
                    ResourcesCompat.getColor(
                            getContext().getResources(),
                            ODD_FIELD_DARK_BACKGROUND,
                            null
                    )
            );

            daySummaryItemViewHolder.field_5.setBackgroundColor(
                    ResourcesCompat.getColor(
                            getContext().getResources(),
                            ODD_FIELD_LIGHT_BACKGROUND,
                            null
                    )
            );
        }
    }
}
