package com.madar.madardemo.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.madar.madardemo.R;
import com.madar.madardemo.Util.Spec;


/**
 * Created by Sotraa on 6/13/2016.
 */
public class TabTitleHolder {
    private final View view;
    LayoutInflater inflater ;
    public TabTitleHolder(String text , Context context){
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.tab_title_holder, null);
//        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setLayoutParams(new ViewGroup.LayoutParams(new Spec(context).WindowRec().x, ViewGroup.LayoutParams.MATCH_PARENT));
         TextView title = (TextView) view.findViewById(R.id.tab_title);
        title.setText(text);
    }
    public View getView (){
        return view;
    }
}
