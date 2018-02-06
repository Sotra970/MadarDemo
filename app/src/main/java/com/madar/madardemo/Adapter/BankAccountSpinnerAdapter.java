package com.madar.madardemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.madar.madardemo.Model.BankItem;
import com.madar.madardemo.R;

/**
 * Created by Ahmed on 8/24/2017.
 */

public class BankAccountSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private BankItem[] banks;
    private Context context;

    public BankAccountSpinnerAdapter(BankItem[] banks, Context context) {
        super();
        this.banks = banks;
        this.context = context;
    }

    @Override
    public int getCount() {
        if(banks != null){
            return banks.length;
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(banks != null && position < banks.length){
            return banks[position];
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if( convertView == null ){
            view = LayoutInflater.from(context)
                    .inflate(R.layout.spinner_bank_account_dropdown_item, parent, false);
        }
        else {
            view = convertView;
        }
        TextView title = (TextView) view.findViewById(R.id.spinner_bank_account_text_view);
        try {
            BankItem bank = banks[position];
            title.setText(bank.getTitle());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public boolean isEmpty() {
        return banks == null || banks.length == 0;
    }

    public void updateData(BankItem[] bankItems){
        this.banks = bankItems;
        notifyDataSetChanged();
    }

    public String getBankId(int position){
        try {
            BankItem bankItem = banks[position];
            return bankItem.getId();
        }
        catch (Exception e){
            return null;
        }
    }
}
