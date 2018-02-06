package com.madar.madardemo.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.madar.madardemo.Listener.NavDrawerItemClickListener;
import com.madar.madardemo.Model.NavDrawerPageItem;
import com.madar.madardemo.R;
import com.madar.madardemo.ViewHolder.NavDrawerItemViewHolder;

import java.util.ArrayList;

/**
 * Created by Ahmed on 8/17/2017.
 */

public class NavDrawerItemAdapter extends RecyclerView.Adapter<NavDrawerItemViewHolder>{

    private ArrayList<NavDrawerPageItem> navDrawerItems;
    private Context context;
    private NavDrawerItemClickListener clickListener;

    private NavDrawerPageItem selectedItem;


    public NavDrawerItemAdapter(ArrayList<NavDrawerPageItem> navDrawerItems,
                                NavDrawerItemClickListener clickListener,
                                Context context)
    {
        this.navDrawerItems = navDrawerItems;
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override
    public NavDrawerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nav_drawer_page_item, parent, false);
        return new NavDrawerItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NavDrawerItemViewHolder holder, final int position) {
        final NavDrawerPageItem navDrawerItem = navDrawerItems.get(position);
        if(navDrawerItem != null){
            if(selectedItem != null && navDrawerItem.equals(selectedItem)){
                holder.itemView.setBackgroundColor(
                        ResourcesCompat.getColor(
                                context.getResources(),
                                R.color.app_blue_grey,
                                null
                        )
                );
                holder.imageView.setColorFilter(
                        ResourcesCompat.getColor(
                                context.getResources(),
                                R.color.app_yellow_1,
                                null),
                        PorterDuff.Mode.SRC_IN
                );

                holder.textView.setTextColor(
                        ResourcesCompat.getColor(
                                context.getResources(),
                                R.color.app_yellow_1,
                                null
                        )
                );

                holder.marker.setVisibility(View.VISIBLE);
            }

            else{
                holder.itemView.setBackgroundColor(
                        ResourcesCompat.getColor(
                                context.getResources(),
                                R.color.transparent,
                                null
                        )
                );

                holder.imageView.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);

                holder.textView.setTextColor(
                        ResourcesCompat.getColor(
                                context.getResources(),
                                R.color.black,
                                null
                        )
                );

                holder.marker.setVisibility(View.GONE);
            }

            Glide.with(context)
                    .load(navDrawerItem.getDrawableResId())
                    .into(holder.imageView);

            holder.textView.setText(navDrawerItem.getTitleResId());

            if(clickListener != null)
            {
                holder.itemView.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                selectItem(navDrawerItem);
                                clickListener.onNavDrawerItemClicked(navDrawerItem);
                            }
                        }
                );
            }
        }
    }

    @Override
    public int getItemCount() {
        if(navDrawerItems != null){
            return navDrawerItems.size();
        }
        return 0;
    }

    public void selectItem(int position){
        try {
            if(selectedItem != null){
                int lastPos = navDrawerItems.indexOf(this.selectedItem);
                notifyItemChanged(lastPos);
            }
            selectedItem = getItem(position);
            notifyItemChanged(position);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void selectItem(NavDrawerPageItem navDrawerPageItem){
        try {
            int lastPos = -1;
            if(selectedItem != null){
                lastPos = navDrawerItems.indexOf(this.selectedItem);
            }
            this.selectedItem = navDrawerPageItem;
            int pos = navDrawerItems.indexOf(navDrawerPageItem);
            if(lastPos != -1){
                notifyItemChanged(lastPos);
            }
            notifyItemChanged(pos);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public NavDrawerPageItem getSelectedItem() {
        return selectedItem;
    }

    public void removeSelectedItem(){
        if(selectedItem != null){
            int pos = navDrawerItems.indexOf(selectedItem);
            selectedItem = null;
            notifyItemChanged(pos);
        }
    }

    public NavDrawerPageItem getItem(int pos){
        try {
            return navDrawerItems.get(pos);
        }
        catch (Exception e){
            return null;
        }
    }
}
