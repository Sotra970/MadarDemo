package com.madar.madardemo.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.madar.madardemo.R;

/**
 * Created by Ahmed on 9/15/2017.
 */

public class PagerViewAdapter extends PagerAdapter {

    private Context context;

    public PagerViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    /*@Override
    public Object instantiateItem(ViewGroup container, int position) {
        FrameLayout itemLayout = new FrameLayout(context);

        ImageView background =
                new ImageView(context);

        background.setLayoutParams(
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        );

        Glide.with(context)
                .load(R.drawable.bg)
                .apply(new RequestOptions().centerCrop())
                .into(background);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);

        TextView textView = new TextView(context);
        ImageView imageView = new ImageView(context);


        imageView.setColorFilter(
                ResourcesCompat.getColor(
                        context.getResources(),
                        R.color.app_blue_grey,
                        null
                ),
                PorterDuff.Mode.SRC_IN
        );

        textView.setTextColor(
                ResourcesCompat.getColor(
                        context.getResources(),
                        R.color.app_blue_grey,
                        null
                )
        );

        if(Utils.getSystemApiNumber() >= 23){
            textView.setTextAppearance(R.style.text_extra_bold);
        }
        else{
            textView.setTextAppearance(context, R.style.text_extra_bold);
        }

        int titleResId = -1;
        int drawableResId = -1;

        switch (position){
            case 0:
                titleResId = R.string.text_fast_delivery;
                drawableResId = R.drawable.logistics;
                break;

            case 1:
                titleResId = R.string.text_packaging;
                drawableResId = R.drawable.box;
                break;

            case 2:
                titleResId = R.string.text_shippment;
                drawableResId = R.drawable.ready_for_delivery;
                break;

            case 3:
                titleResId = R.string.cash_on_delivery;
                drawableResId = R.drawable.wallet;
                break;
        }

        if(drawableResId != -1){
            Glide.with(context)
                    .load("")
                    .apply(new RequestOptions().error(drawableResId))
                    .into(imageView);

        }

        if(titleResId != -1){
            textView.setText(titleResId);
        }

        linearLayout.addView(textView);
        linearLayout.addView(imageView);

        itemLayout.addView(background);
        itemLayout.addView(linearLayout);

        container.addView(itemLayout);

        return itemLayout;
    }*/

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemLayout = LayoutInflater.from(context)
                .inflate(R.layout.pager_view_item, container, false);

        ImageView background =
                (ImageView) itemLayout.findViewById(R.id.pager_view_item_background);

        TextView textView =
                (TextView) itemLayout.findViewById(R.id.pager_view_item_text_view);
        TextView sec_textView =
                (TextView) itemLayout.findViewById(R.id.pager_view_item_sec_text_view);


        ImageView imageView =
                (ImageView) itemLayout.findViewById(R.id.pager_view_item_image_view);


        Glide.with(context)
                .load(R.drawable.bg)
                .apply(new RequestOptions().centerCrop())
                .into(background);


        int titleResId = -1;
        int sec_titleResId = -1;
        int drawableResId = -1;

        switch (position){
            case 0:
                titleResId = R.string.text_customer_service_num;
                sec_titleResId = R.string.text_customer_service;
                drawableResId = R.drawable.customer_service;
                break;

            case 1:
                titleResId = R.string.text_packaging_2;
                drawableResId = R.drawable.box;
                break;

            case 2:
                titleResId = R.string.text_delivery_2_all;
                drawableResId = R.drawable.delivery_transportation_machine;
                break;

            case 3:
                titleResId = R.string.cash_on_delivery;
                drawableResId = R.drawable.wallet;
                break;
        }

        if(drawableResId != -1){
            Glide.with(context)
                    .load(drawableResId)
                    .into(imageView);
        }


        if(titleResId != -1){
            textView.setText(titleResId);
        }
        if(sec_titleResId != -1){
            sec_textView.setText(sec_titleResId);
        }else {
            sec_textView.setText("");
        }

        //container.removeAllViews();
        container.addView(itemLayout);

        return itemLayout;
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        try {
            container.removeView((View) object);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
