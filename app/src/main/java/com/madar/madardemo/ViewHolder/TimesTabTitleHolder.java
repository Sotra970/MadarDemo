package com.madar.madardemo.ViewHolder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.madar.madardemo.Model.ServerResponse.AvDatesResponse;
import com.madar.madardemo.R;
import com.madar.madardemo.Util.Spec;


/**
 * Created by Sotraa on 6/13/2016.
 */
public class TimesTabTitleHolder {
    private final View view;
    LayoutInflater inflater ;
    TextView time_from ;
    TextView time_to ;
    TextView to_text ;
    View tab_container ;
    Context context ;
    public  AvDatesResponse.AvDatesDayModel dayModel  ;
    public TimesTabTitleHolder(AvDatesResponse.AvDatesDayModel child, Context context){
        this.dayModel = child ;
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.time_tab_title_holder, null);
        this.context = context ;
        time_from = (TextView) view.findViewById(R.id.time_from);
        time_to = (TextView) view.findViewById(R.id.time_to);
        to_text = (TextView) view.findViewById(R.id.to_text);
        tab_container =  view.findViewById(R.id.tab_container);

        if (child !=null)
            to_text.setVisibility(View.VISIBLE);
        try {
           if (TextUtils.isEmpty( child.Day_Date_Ar)){
               to_text.setVisibility(View.GONE);

           }
        }catch (Exception e){
            to_text.setVisibility(View.GONE);
        }


        if (child !=null)
        time_from.setText(child.From);
        if (child !=null)

            time_to.setText(child.To);
    }
    public View getView (){
        return view;
    }

    public  void select(){
        if (dayModel ==null)
            return;

            time_from.setTextColor(ContextCompat.getColor(context , R.color.black));
        time_to.setTextColor(ContextCompat.getColor(context , R.color.black));
        to_text.setTextColor(ContextCompat.getColor(context , R.color.black));
//        tab_container.setBackgroundColor(ContextCompat.getColor(context ,R.color.app_yellow_1));
    }


    public  void unSelect(){
        if (dayModel ==null)
            return;
            time_from.setTextColor(ContextCompat.getColor(context , R.color.grey_700));
        time_to.setTextColor(ContextCompat.getColor(context , R.color.grey_700));
        to_text.setTextColor(ContextCompat.getColor(context , R.color.grey_700));
//        tab_container.setBackgroundColor(ContextCompat.getColor(context ,R.color.transparent));

    }
}
