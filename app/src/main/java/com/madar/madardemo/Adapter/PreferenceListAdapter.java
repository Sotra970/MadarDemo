package com.madar.madardemo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.madar.madardemo.Model.PreferenceItem;
import com.madar.madardemo.R;
import com.madar.madardemo.Util.PreferenceManager;
import com.madar.madardemo.ViewHolder.PreferenceItemViewHolder;

import java.util.ArrayList;

/**
 * Created by Ahmed on 8/21/2017.
 */

public class PreferenceListAdapter extends RecyclerView.Adapter<PreferenceItemViewHolder> {

    private ArrayList<PreferenceItem> preferenceItems;
    private Context context;

    public PreferenceListAdapter(ArrayList<PreferenceItem> preferenceItems, Context context) {
        this.preferenceItems = preferenceItems;
        this.context = context;
    }

    @Override
    public PreferenceItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.preference_item, parent, false);
        return new PreferenceItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PreferenceItemViewHolder holder, int position) {
        final PreferenceItem preferenceItem = preferenceItems.get(position);

        holder.textView.setText(preferenceItem.getTitleResId());
        holder.switchButton.setChecked(preferenceItem.isActive());
        holder.imageView.setImageResource(preferenceItem.getImageResId());
        if(!preferenceItem.isActive()){
            holder.rotatedLine.setVisibility(View.VISIBLE);
        }
        else{
            holder.rotatedLine.setVisibility(View.GONE);
        }

        holder.switchButton.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        PreferenceManager.savePreference(
                                preferenceItem.getTitleResId(),
                                isChecked,
                                context
                        );
                        if(!isChecked){
                            holder.rotatedLine.setVisibility(View.VISIBLE);
                        }
                        else{
                            holder.rotatedLine.setVisibility(View.GONE);
                        }
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        if(preferenceItems != null){
            return preferenceItems.size();
        }
        return 0;
    }
}
