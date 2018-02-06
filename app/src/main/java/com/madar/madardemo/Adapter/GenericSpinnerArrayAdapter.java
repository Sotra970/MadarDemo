package com.madar.madardemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.madar.madardemo.R;

import java.util.ArrayList;

public abstract class GenericSpinnerArrayAdapter<T> extends ArrayAdapter<T> {

  // Vars
  private LayoutInflater mInflater;

  public GenericSpinnerArrayAdapter(Context context, ArrayList<T> objects) {
    super(context, 0, objects);
    init(context);
  }

  // Headers
  public abstract void drawText(TextView textView, T object);
  public abstract void drawSubText(TextView textView, T object);

  private void init(Context context) {
    this.mInflater = LayoutInflater.from(context);
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    final ViewHolder vh;
    if (convertView == null) {
      convertView = mInflater.inflate(R.layout.simple_spinner_item, parent, false);
      vh = new ViewHolder(convertView);
      convertView.setTag(vh);
    } else {
      vh = (ViewHolder) convertView.getTag();
    }

    drawText(vh.textView, getItem(position));

    return convertView;
  }

  static class ViewHolder {

    TextView textView;

    private ViewHolder(View rootView) {
      textView = (TextView) rootView.findViewById(R.id.row_text);
    }
  }

  static class ViewHolderSub {

    TextView textView;

    private ViewHolderSub(View rootView) {
      textView = (TextView) rootView.findViewById(R.id.sub_text);
    }
  }
  @Override
  public View getDropDownView(int position, View convertView, ViewGroup parent) {
    final ViewHolderSub vh;
    if (convertView == null) {
      convertView = mInflater.inflate(R.layout.simple_spinner_item_child, parent, false);
      vh = new ViewHolderSub(convertView);
      convertView.setTag(vh);
    } else {
      vh = (ViewHolderSub) convertView.getTag();
    }

    drawSubText(vh.textView, getItem(position));

    return convertView;
  }
}