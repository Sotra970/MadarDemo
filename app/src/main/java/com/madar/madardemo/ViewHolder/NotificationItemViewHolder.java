package com.madar.madardemo.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.madar.madardemo.R;

/**
 * Created by Ahmed on 8/18/2017.
 */

public class NotificationItemViewHolder extends RecyclerView.ViewHolder {

    public ImageView deleteButton;
    public TextView msgTextView;
    public ImageView bulletImageView;
    public TextView timeTextView;
    public View background;

    public NotificationItemViewHolder(View itemView) {
        super(itemView);
        this.deleteButton = (ImageView)
                itemView.findViewById(R.id.notification_item_delete_button);
        this.msgTextView = (TextView)
                itemView.findViewById(R.id.notification_item_msg_text_view);
        this.bulletImageView = (ImageView)
                itemView.findViewById(R.id.notification_item_bullet_image_view);
        this.timeTextView = (TextView)
                itemView.findViewById(R.id.notification_item_time_text_view);
        this.background = itemView.findViewById(R.id.notification_item_background);
    }


}
