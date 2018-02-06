package com.madar.madardemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.madar.madardemo.Model.BankModel;
import com.madar.madardemo.R;

import java.util.ArrayList;

public abstract class BankArrayAdapter<T> extends ArrayAdapter<T> {

  // Vars
  private LayoutInflater mInflater;

  public BankArrayAdapter(Context context, ArrayList<T> objects) {
    super(context, 0, objects);
    init(context);
  }

  // Headers
  public abstract void delete( T object , int pos);

  private void init(Context context) {
    this.mInflater = LayoutInflater.from(context);
  }

  @Override public View getView(final int position, View convertView, ViewGroup parent) {
    final ViewHolder vh;
    if (convertView == null) {
      convertView = mInflater.inflate(R.layout.bank_item_card, parent, false);
      vh = new ViewHolder(convertView);
      convertView.setTag(vh);
    } else {
      vh = (ViewHolder) convertView.getTag();
    }
    BankModel bankModel  = (BankModel) getItem(position);
    vh.bank_text.setText(bankModel.getName());
    vh.bank_number_text.setText(bankModel.getAccount_number());
    vh.bank_delete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        delete(getItem(position) , position);
      }
    });

    return convertView;
  }

  static class ViewHolder {

    TextView bank_text , bank_number_text;
    View bank_delete ;

    private ViewHolder(View rootView) {
      bank_text = (TextView) rootView.findViewById(R.id.bank_text);
      bank_number_text = (TextView) rootView.findViewById(R.id.bank_number_text);
      bank_delete = rootView.findViewById(R.id.bank_delete);
    }
  }



}