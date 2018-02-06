package com.madar.madardemo.Adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madar.madardemo.Activity.NotificationActivity;
import com.madar.madardemo.Model.NotificationListItem;
import com.madar.madardemo.R;
import com.madar.madardemo.ViewHolder.NotificationItemViewHolder;

import java.util.ArrayList;

/**
 * Created by Ahmed on 8/18/2017.
 */

public class NotificationListAdapter extends GenericAdapter<NotificationListItem>
{

    private NotificationActivity.NotificationItemCLickListener notificationItemCLickListener;

    public NotificationListAdapter(ArrayList<NotificationListItem> items,
                                   Context context,
                                   NotificationActivity.NotificationItemCLickListener cLickListener) {
        super(items, context);
        this.notificationItemCLickListener = cLickListener;
    }

    @Override
    public NotificationItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);
        return new NotificationItemViewHolder(v);
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, int position) {
        final NotificationListItem notificationItem =  getItem(position);
        if(notificationItem != null){
            
            NotificationItemViewHolder holder = (NotificationItemViewHolder) h;
            
            if(holder != null){

                if(notificationItemCLickListener != null){
                    holder.itemView.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    notificationItemCLickListener.onItemClicked(notificationItem);
                                }
                            }
                    );

                    holder.deleteButton.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    notificationItemCLickListener.onDeleteIconClicked(notificationItem);
                                }
                            }
                    );
                }

                holder.msgTextView.setText(notificationItem.getContent());
                holder.timeTextView.setText(notificationItem.getParsedTime());

                if(notificationItem.isSeen()){
                    holder.bulletImageView.setColorFilter(
                            ResourcesCompat.getColor(
                                    getContext().getResources(),
                                    R.color.grey_500,
                                    null
                            ),
                            PorterDuff.Mode.SRC_IN
                    );
                    holder.deleteButton.setColorFilter(
                            ResourcesCompat.getColor(
                                    getContext().getResources(),
                                    R.color.grey_500,
                                    null
                            ),
                            PorterDuff.Mode.SRC_IN
                    );
                    holder.msgTextView.setTextColor(
                            ResourcesCompat.getColor(
                                    getContext().getResources(),
                                    R.color.grey_600,
                                    null
                            )
                    );
                    if(position%2 == 0){
                        holder.background.setBackgroundColor(
                                ResourcesCompat.getColor(
                                        getContext().getResources(),
                                        R.color.grey_100,
                                        null
                                )
                        );
                    }
                    else{
                        holder.background.setBackgroundColor(
                                ResourcesCompat.getColor(
                                        getContext().getResources(),
                                        R.color.transparent,
                                        null
                                )
                        );
                    }
                }
                else{
                    holder.bulletImageView.setColorFilter(
                            ResourcesCompat.getColor(
                                    getContext().getResources(),
                                    R.color.app_yellow_1,
                                    null
                            ),
                            PorterDuff.Mode.SRC_IN
                    );
                    holder.deleteButton.setColorFilter(
                            ResourcesCompat.getColor(
                                    getContext().getResources(),
                                    R.color.black,
                                    null
                            ),
                            PorterDuff.Mode.SRC_IN
                    );
                    holder.msgTextView.setTextColor(
                            ResourcesCompat.getColor(
                                    getContext().getResources(),
                                    R.color.black,
                                    null
                            )
                    );
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
                                        R.color.transparent,
                                        null
                                )
                        );
                    }
                }
            }
        }
    }

    public void removeItem(NotificationListItem notificationListItem){
        if(notificationListItem != null){
            ArrayList<NotificationListItem> notificationListItems = getItems();
            try {
                int position = notificationListItems.indexOf(notificationListItem);
                notificationListItems.remove(notificationListItem);
                notifyItemRemoved(position);
            }
            catch (Exception e){

            }
        }
    }
}
