package com.madar.madardemo.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.madar.madardemo.R;

/**
 * Created by Ahmed on 8/23/2017.
 */

public class OrderItemViewHolder extends RecyclerView.ViewHolder{

    public TextView clientTitleTextView;
    public TextView orderIdTextView;
    public TextView addressTextView;
    public ImageView imageView;
    public TextView orderStatueTextView;
    public TextView dateTextView;
    public View background;

    public OrderItemViewHolder(View itemView) {
        super(itemView);

        this.background = itemView.findViewById(R.id.order_list_item_background);

        this.addressTextView = (TextView)
                itemView.findViewById(R.id.order_list_item_client_address_text_view);

        this.clientTitleTextView = (TextView)
                itemView.findViewById(R.id.order_list_item_client_title_text_view);

        this.orderIdTextView = (TextView)
                itemView.findViewById(R.id.order_list_item_order_id_text_view);

        this.orderStatueTextView = (TextView)
                itemView.findViewById(R.id.order_list_item_statue_text_view);

        this.dateTextView = (TextView)
                itemView.findViewById(R.id.order_list_item_date_text_view);

        this.imageView = (ImageView)
                itemView.findViewById(R.id.order_list_item_image_view);
    }
}
