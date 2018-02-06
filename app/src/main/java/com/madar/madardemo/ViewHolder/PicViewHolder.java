package com.madar.madardemo.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.madar.madardemo.R;

/**
 * Created by Ahmed on 8/23/2017.
 */

public class PicViewHolder extends RecyclerView.ViewHolder{

    public ImageView img;

    public PicViewHolder(View itemView) {
        super(itemView);

        this.img = (ImageView)
                itemView.findViewById(R.id.pic_holder_img);
    }
}
