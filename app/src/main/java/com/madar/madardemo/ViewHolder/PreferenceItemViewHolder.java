package com.madar.madardemo.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.madar.madardemo.R;
import com.kyleduo.switchbutton.SwitchButton;

/**
 * Created by Ahmed on 8/21/2017.
 */

public class PreferenceItemViewHolder extends RecyclerView.ViewHolder{

    public TextView textView;
    public SwitchButton switchButton;
    public ImageView imageView;
    public ImageView rotatedLine;

    public PreferenceItemViewHolder(View itemView) {
        super(itemView);
        this.switchButton = (SwitchButton)
                itemView.findViewById(R.id.preference_item_toggle);
        this.textView = (TextView)
                itemView.findViewById(R.id.preference_item_text_view);
        this.imageView = (ImageView)
                itemView.findViewById(R.id.preference_item_image_view);
        this.rotatedLine = (ImageView)
                itemView.findViewById(R.id.preference_item_rotated_line_image_view);
    }
}
