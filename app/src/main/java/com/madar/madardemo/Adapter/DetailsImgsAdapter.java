package com.madar.madardemo.Adapter;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.madar.madardemo.AppManger.Config;
import com.madar.madardemo.Model.PicModel;
import com.madar.madardemo.Model.ReceiptItem;
import com.madar.madardemo.Model.ReceiptListItem;
import com.madar.madardemo.Model.ReceiptSummaryItem;
import com.madar.madardemo.R;
import com.madar.madardemo.Util.TimeUtils;
import com.madar.madardemo.ViewHolder.PicViewHolder;
import com.madar.madardemo.ViewHolder.ReceiptItemViewHolder;
import com.madar.madardemo.ViewHolder.ReceiptSummaryItemViewHolder;

import java.util.ArrayList;

/**
 * Created by Ahmed on 8/19/2017.
 */

public class DetailsImgsAdapter extends RecyclerView.Adapter<PicViewHolder> {

    private ArrayList<PicModel> data;
    private Context context;

    public DetailsImgsAdapter(ArrayList<PicModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public PicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item_view = LayoutInflater.from(context).inflate(R.layout.pick_item , parent , false);
            return new PicViewHolder(item_view);
    }

    @Override
    public void onBindViewHolder(PicViewHolder viewHolder, int position) {
        try {
            PicModel current  = data.get(position) ;
            Glide.with(context)
                    .load(current.getWeb_path())
//                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .apply(new RequestOptions().centerCrop())
                    .transition(new DrawableTransitionOptions().crossFade())
                    .thumbnail(0.5f)
                    .into(viewHolder.img) ;
        }catch (Exception e){}
    }

    @Override
    public int getItemCount() {

        return  data.size();
    }

}
