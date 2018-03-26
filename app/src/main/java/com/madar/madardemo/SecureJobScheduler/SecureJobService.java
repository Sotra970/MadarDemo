package com.madar.madardemo.SecureJobScheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.madar.madardemo.AppManger.MadarApplication;
import com.madar.madardemo.Model.isFoundModel;
import com.madar.madardemo.R;
import com.madar.madardemo.Service.Injector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sotra on 3/22/2018.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class SecureJobService extends JobService {

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        Log.e("SecureJobService" ,"onStartJob");
        if (MadarApplication.getInstance()!=null && MadarApplication.getUser()!=null){
        Call<isFoundModel> call  = Injector.Api().iSFound(MadarApplication.getInstance().getPrefManager().getUser().getSecret()) ;
        call.enqueue(new Callback<isFoundModel>() {
            @Override
            public void onResponse(Call<isFoundModel> call, Response<isFoundModel> response) {
                if (response !=null && response.body()!=null){
                    if (response.body().getResponseItem().isSuccessful()){
                        jobFinished(jobParameters, true);
                    }else {
                        jobFinished(jobParameters, false);
                        MadarApplication.getInstance().getPrefManager().clear(false);
//                        if (MadarApplication.isAlive()){
//                            startActivity(new Intent(getApplicationContext() , LogoutDialogActivity.class));
//                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<isFoundModel> call, Throwable t) {
                jobFinished(jobParameters, true);
            }
        });
        }else {
            jobFinished(jobParameters, true);
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.e("SecureJobService" ,"onStopJob");
        return false;
    }
}
