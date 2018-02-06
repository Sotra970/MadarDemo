package com.madar.madardemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.madar.madardemo.R;

import java.util.ArrayList;

public abstract class FavsArrayAdapter<T> extends ArrayAdapter<T> {

  // Vars
  private LayoutInflater mInflater;

  public FavsArrayAdapter(Context context, ArrayList<T> objects) {
    super(context, 0, objects);
    init(context);
  }

  // Headers
  public abstract void drawText(TextView textView, T object);
  public abstract void delete( T object , int pos);

  private void init(Context context) {
    this.mInflater = LayoutInflater.from(context);
  }

  @Override public View getView(final int position, View convertView, ViewGroup parent) {
    final ViewHolder vh;
    if (convertView == null) {
      convertView = mInflater.inflate(R.layout.simple_spinner_item_card, parent, false);
      vh = new ViewHolder(convertView);
      convertView.setTag(vh);
    } else {
      vh = (ViewHolder) convertView.getTag();
    }

    drawText(vh.textView, getItem(position));
    vh.delete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        delete(getItem(position) , position);
      }
    });

    return convertView;
  }

  static class ViewHolder {

    TextView textView;
    View delete ;

    private ViewHolder(View rootView) {
      textView = (TextView) rootView.findViewById(R.id.row_text);
      delete = rootView.findViewById(R.id.delete);
    }
  }



}