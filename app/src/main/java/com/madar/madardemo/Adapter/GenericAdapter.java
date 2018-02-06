package com.madar.madardemo.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.madar.madardemo.Interface.AdapterItemClickListener;

import java.util.ArrayList;

/**
 * Created by Ahmed on 9/8/2017.
 */

public abstract class GenericAdapter<T> extends RecyclerView.Adapter {

    private ArrayList<T> items;
    private Context context;

    private AdapterItemClickListener adapterItemClickListener;


    public GenericAdapter(ArrayList<T> items,
                          Context context)
    {
        this.items = items;
        this.context = context;
    }

    public GenericAdapter(ArrayList<T> items, Context context, AdapterItemClickListener adapterItemClickListener) {
        this.items = items;
        this.context = context;
        this.adapterItemClickListener = adapterItemClickListener;
    }

    public void setAdapterItemClickListener(AdapterItemClickListener adapterItemClickListener) {
        this.adapterItemClickListener = adapterItemClickListener;
    }

    @Override
    public int getItemCount() {
        if(items == null){
            return 0;
        }
        return items.size();
    }

    public void updateData(ArrayList<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }


    @Nullable
    public Integer getItemId(T item){
        if(items == null || items.isEmpty()){
            return null;
        }
        return items.indexOf(item);
    }

    public ArrayList<T> getItems() {
        return items;
    }

    public Context getContext() {
        return context;
    }


    @Nullable
    public T getItem(int position){
        if(items != null && position >= 0 && position < items.size()){
            return items.get(position);
        }
        return null;
    }

    public boolean isDataSetEmpty(){
        return items == null || items.isEmpty();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(adapterItemClickListener != null){
            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            adapterItemClickListener.onAdapterItemClicked(holder.itemView);
                        }
                    }
            );
        }
    }


}
