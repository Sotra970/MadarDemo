package com.madar.madardemo.Util;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.madar.madardemo.R;


/**
 * Created by Mohamed Salama on 7/12/2016.
 */
public class CustomTextView extends android.support.v7.widget.AppCompatTextView {

    private static final String TAG = "TextView";


    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode()) {
            return;
        }

        setCustomFont(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        String font_name = a.getString(R.styleable.CustomTextView_font_name);
        setTypeface(Typefaces.get(ctx, font_name));
        a.recycle();
    }

}
