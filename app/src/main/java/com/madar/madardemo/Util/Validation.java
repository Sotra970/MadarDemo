package com.madar.madardemo.Util;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.madar.madardemo.AppManger.MadarApplication;
import com.madar.madardemo.R;

/**
 * Created by Moza on 26-Jul-17.
 */

public class Validation {


    static  public boolean isEditTextEmpty(EditText editText){
        if (TextUtils.isEmpty(editText.getText().toString()))
        {
            editText.setError(MadarApplication.getInstance().getApplicationContext().getString(R.string.empty_error));
            editText.requestFocus();
            return true;
        }else
            return false;
    }


   static  public boolean validate_spinner_mu(Spinner spinner , String ID) {
        TextView textView = ((TextView) spinner.getChildAt(0));
        Log.e("txtview" , textView.getText().toString());
        if ( ID == null  || ID.equals("0") ){
            textView.setError(MadarApplication.getInstance().getApplicationContext().getString(R.string.empty_error));
            spinner.requestFocus();
            Log.e("err" , "true");
            return true;
        } else {
            textView.setError(null);
        }

        return false;
    }

    static public boolean validateName(String name){
        if (!name.matches("^[a-zA-Z]+$"))
            return false;
        else
            return true;
    }

    static public boolean validateEmail(EditText editText){
        if (!isEditTextEmpty(editText))
        {
            if (!editText.getText().toString().matches("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"))
            {
                editText.setError(MadarApplication.getInstance().getApplicationContext().getString(R.string.email_error));
                editText.requestFocus();
                return true;
            }
            else
                return false;
        }else return true ;
    }


    static public boolean validateLink(EditText editText){
        if (!isEditTextEmpty(editText))
        {
            if (!Patterns.WEB_URL.matcher(editText.getText()).matches())
            {
                editText.setError(MadarApplication.getInstance().getApplicationContext().getString(R.string.link_error));
                editText.requestFocus();
                return true;
            }
            else
                return false;
        }else return true ;
    }

    static public boolean validatePhone(EditText editText){
        if (!isEditTextEmpty(editText))
        {
            if (!editText.getText().toString().matches("^[+\\d{3}]?\\d{11,20}$"))
            {
                editText.setError(MadarApplication.getInstance().getApplicationContext().getString(R.string.phone_error));
                editText.requestFocus();
                return true;
            }
            else
                return false;
        }else return true ;
    }



    public static class PackageCountTextWatcher implements TextWatcher {
        EditText pack_count_txt ;
         public  PackageCountTextWatcher(EditText pack_count_txt) {
            this.pack_count_txt = pack_count_txt;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
                    int count  = Integer.parseInt(pack_count_txt.getText().toString()) ;
                    if (count  <1) pack_count_txt.setText("1");

        }
    }


}
