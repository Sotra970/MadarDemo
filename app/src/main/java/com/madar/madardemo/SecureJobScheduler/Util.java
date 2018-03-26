package com.madar.madardemo.SecureJobScheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.util.Log;

/**
 * Created by Sotra on 3/22/2018.
 */

public class Util {

    public static void scheduleJob(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ComponentName serviceComponent = new ComponentName(context, SecureJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(1*60*1000);
//            builder.setOverrideDeadline(1*60*1000);
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int b = jobScheduler.schedule(builder.build());
            Log.e("b" , b+"") ;
        }
    }
}
