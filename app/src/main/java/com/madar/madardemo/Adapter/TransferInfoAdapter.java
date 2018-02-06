package com.madar.madardemo.Adapter;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madar.madardemo.Interface.AdapterItemClickListener;
import com.madar.madardemo.Model.TransactionInfoItem;
import com.madar.madardemo.R;
import com.madar.madardemo.Util.TimeUtils;
import com.madar.madardemo.ViewHolder.TransferInfoItemViewHolder;

import java.util.ArrayList;

/**
 * Created by Ahmed on 10/5/2017.
 */

public class TransferInfoAdapter extends GenericAdapter<TransactionInfoItem>{

    public TransferInfoAdapter(ArrayList<TransactionInfoItem> items, Context context) {
        super(items, context);
    }

    public TransferInfoAdapter(ArrayList<TransactionInfoItem> items, Context context, AdapterItemClickListener adapterItemClickListener) {
        super(items, context, adapterItemClickListener);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(getContext())
                .inflate(R.layout.transfer_info_item, parent, false);
        return new TransferInfoItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TransactionInfoItem transactionInfoItem = getItem(position);
        if(transactionInfoItem != null){
            TransferInfoItemViewHolder h = (TransferInfoItemViewHolder) holder;

            h.deliveryFees.setText(transactionInfoItem.getShipPrice());
            h.productPrice.setText(transactionInfoItem.getProductPrice());
            h.balance.setText(transactionInfoItem.getBalance());

            String date = transactionInfoItem.getDate();
            String day = TimeUtils.getDayOfMonth(date, TimeUtils.FORMAT_DATE_WITH_TIME, TimeUtils.LANGUAGE_EN);
            String month = TimeUtils.getMonthOfYear(date, TimeUtils.FORMAT_DATE_WITH_TIME, TimeUtils.LANGUAGE_EN, TimeUtils.LENGTH_SHORT);

            h.day.setText(day);
            h.month.setText(month);

            if(position%2 == 0){
                h.background.setBackgroundColor(
                        ResourcesCompat.getColor(
                                getContext().getResources(),
                                R.color.app_yellow_4,
                                null
                        )
                );
            }
            else{
                h.background.setBackgroundColor(
                        ResourcesCompat.getColor(
                                getContext().getResources(),
                                R.color.white,
                                null
                        )
                );
            }
        }
    }
}
