package com.madar.madardemo.ViewHolder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.madar.madardemo.Model.ServerResponse.AvDatesResponse;
import com.madar.madardemo.R;


/**
 * Created by Sotraa on 6/13/2016.
 */
public class DayTabTitleHolder {
    private final View view;
    LayoutInflater inflater ;
    TextView tab_day;
    TextView tab_date;
    Context context ;
    public DayTabTitleHolder(AvDatesResponse.AvDatesResponseDataModel child , Context context){
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.day_tab_title_holder, null);
        this.context = context ;
        tab_day = (TextView) view.findViewById(R.id.tab_day);
        tab_date = (TextView) view.findViewById(R.id.tab_date);
        tab_day.setText(child.data.get(0).Day);
        tab_date.setText(child.data.get(0).Day_Date_DB);
    }
    public View getView (){
        return view;
    }

    public  void select(){
        tab_day.setTextColor(ContextCompat.getColor(context , R.color.black));
        tab_date.setTextColor(ContextCompat.getColor(context , R.color.grey_800));
    }


    public  void unSelect(){
        tab_day.setTextColor(ContextCompat.getColor(context , R.color.grey_700));
        tab_date.setTextColor(ContextCompat.getColor(context , R.color.grey_500));
    }
}
