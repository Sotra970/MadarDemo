package com.madar.madardemo.ViewHolder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.madar.madardemo.R;


/**
 * Created by Sotraa on 6/13/2016.
 */
public class CustomTabTitleHolder {
    private final View view;
    LayoutInflater inflater ;
    TextView title ;
    Context context ;
    public CustomTabTitleHolder(String text , Context context){
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.custom_tab_title_holder, null);
        this.context = context ;
        title = (TextView) view.findViewById(R.id.tab_title);
        title.setText(text);
    }
    public View getView (){
        return view;
    }

    public  void select(){
        title.setTextColor(ContextCompat.getColor(context , R.color.black));
    }


    public  void unSelect(){
        title.setTextColor(ContextCompat.getColor(context , R.color.grey_700));
    }
}
