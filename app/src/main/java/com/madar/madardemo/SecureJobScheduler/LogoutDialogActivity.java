package com.madar.madardemo.SecureJobScheduler;

import android.content.DialogInterface;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.madar.madardemo.AppManger.MadarApplication;
import com.madar.madardemo.R;

public class LogoutDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_layout);
        overridePendingTransition(R.anim.fade_in , R.anim.fade_out);
        AlertDialog  alertDialog =  new AlertDialog.Builder(this)
                .setMessage(R.string.please_relogin)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MadarApplication.getInstance().getPrefManager().clear();

                    }
                }).create();
        alertDialog.show();
    }
}
