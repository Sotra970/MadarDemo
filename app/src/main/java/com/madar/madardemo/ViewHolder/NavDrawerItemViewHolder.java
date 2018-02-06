package com.madar.madardemo.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.madar.madardemo.R;

/**
 * Created by Ahmed on 8/17/2017.
 */

public class NavDrawerItemViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView textView;
    public View marker;

    public NavDrawerItemViewHolder(View itemView) {
        super(itemView);
        this.textView = (TextView) itemView.findViewById(R.id.nav_drawer_page_item_text_view);
        this.imageView = (ImageView) itemView.findViewById(R.id.nav_drawer_page_item_image_view);
        this.marker = itemView.findViewById(R.id.nav_drawer_page_item_select_marker);
    }
}
