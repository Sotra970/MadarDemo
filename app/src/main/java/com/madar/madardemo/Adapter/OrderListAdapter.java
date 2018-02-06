package com.madar.madardemo.Adapter;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.madar.madardemo.Interface.AdapterItemClickListener;
import com.madar.madardemo.Model.OrderItem;
import com.madar.madardemo.R;
import com.madar.madardemo.ViewHolder.OrderItemViewHolder;

import java.util.ArrayList;

/**
 * Created by Ahmed on 8/23/2017.
 */

public class OrderListAdapter extends GenericAdapter<OrderItem> {


    public OrderListAdapter(ArrayList<OrderItem> items, Context context, AdapterItemClickListener adapterItemClickListener) {
        super(items, context, adapterItemClickListener);
    }

    @Override
    public OrderItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(getContext())
                .inflate(R.layout.order_list_item, parent, false);
        return new OrderItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, int position) {
        OrderItem orderItem = getItem(position);
        OrderItemViewHolder holder = (OrderItemViewHolder) h;
        if(orderItem != null){

            holder.orderIdTextView.setText(orderItem.getID());

            String date = orderItem.getParsedDate();
            if(date != null){
                holder.dateTextView.setText(
                        orderItem.getParsedDate()
                );
            }

            String orderStatue = orderItem.getCase_Name();
            String image = orderItem.getCase_Img();
            //int colorResId = getColor(orderItem.getShip_Type());

            /*int color =  ResourcesCompat.getColor(
                    context.getResources(),
                    R.color.black,
                    null
            );

            holder.imageView.setColorFilter(color, PorterDuff.Mode.SRC_IN);

            holder.orderStatueTextView.setTextColor(color);*/

            Glide.with(getContext())
                    .load(image)
                    .into(holder.imageView);

            holder.orderStatueTextView.setText(orderStatue);

            if(orderItem.isReceiving()){
                holder.addressTextView.setText(
                        orderItem.getFrom_City_Name() + " - " + orderItem.getFrom_District()
                );

                holder.clientTitleTextView.setText(R.string.text_from);
                holder.clientTitleTextView.append(" : ");
                holder.clientTitleTextView.append(orderItem.getCustomer_Name());
            }

            else{
                holder.addressTextView.setText(
                        orderItem.getTo_City_Name() + " - " + orderItem.getTo_District()
                );

                holder.clientTitleTextView.setText(R.string.text_to);
                holder.clientTitleTextView.append(" : ");
                holder.clientTitleTextView.append(orderItem.getReceiver_Name());
            }

            if(position%2 == 0){
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

        super.onBindViewHolder(h, position);
    }

    /*public void setOrderCases(ArrayList<OrderCase> orderCases){
        this.orderCases = orderCases;
    }*/

    /*private String getState(String id){
        if(orderCases != null && !orderCases.isEmpty() && id != null){
            for(OrderCase orderCase : orderCases){
                try {
                    if(id.equalsIgnoreCase(orderCase.getId())){
                        return orderCase.getTitle();
                    }
                }
                catch (Exception e){}
            }
        }
        return "";
    }*/

    /*private int getColor(String caseId){
        if(caseId != null){
            switch (caseId){
                case "0":
                    return R.color.red_500;
                case "1":
                    return R.color.green_500;
                case "2":
                    return R.color.blue_500;
                case "3":
                    return R.color.yellow_800;
                case "4":
                    return R.color.teal_600;
                case "5":
                    return R.color.indigo_500;
            }
        }

        return R.color.black;
    }*/
}
