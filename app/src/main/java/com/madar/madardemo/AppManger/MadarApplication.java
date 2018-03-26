package com.madar.madardemo.AppManger;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.madar.madardemo.Model.ProfileItem;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;

import retrofit2.Retrofit;

/**
 * Created by Ahmed on 8/17/2017.
 */

public class MadarApplication extends MultiDexApplication {

    public final static int FONT_CHANGA_BOLD = 20002;
    public final static int FONT_CHANGA_REGULAR = 20003;
    public final static int FONT_CHANGA_SEMI_BOLD = 20008;
    public final static int FONT_CHANGA_EXTRA_BOLD = 20005;
    public final static int FONT_CHANGA_MEDIUM = 20007;
    public final static int FONT_CHANGA_LIGHT = 20004;
    public final static int FONT_CHANGA_EXTRA_LIGHT = 20006;


    private static final String CONSUMER_KEY = "4cSDVaD4dliG24bCAr2URCT7u";
    private static final String CONSUMER_SECRET = "haOK56SRCZofrRfZ8p0CCWKRd99IvVP6JzmmSF6Q7nXIF2xuPe";



    private static MadarApplication mInstance;
    private MyPreferenceManager pref;


    private static ExecutorService executorService;

    @Override
    public void onCreate() {
        super.onCreate();

        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(CONSUMER_KEY, CONSUMER_SECRET))
                .debug(true)
                .build();
        Twitter.initialize(config);

        mInstance = this;
        Log.e("App" , "oncreate");

    }


    public static ExecutorService getExecutorService(){
        if(executorService == null){
            int threadNum = Runtime.getRuntime().availableProcessors();
            executorService = Executors.newFixedThreadPool(threadNum);
        }
        return executorService;
    }

    public static synchronized MadarApplication getInstance() {
        return mInstance;
    }

    public MyPreferenceManager getPrefManager() {
        if (pref == null) {
            pref = new MyPreferenceManager(this);
        }

        return pref;
    }


    public static boolean hasNetwork ()
    {
        return mInstance.checkIfHasNetwork();
    }

    public boolean checkIfHasNetwork()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


    public static ProfileItem getUser() {
        return getInstance().getPrefManager().getUser() ;
    }

    public static String getCurrency() {
        return " ";
    }

    boolean isAlive = false ;
    public static boolean isAlive(){
        if (getInstance() != null && getInstance().getApplicationContext() !=null)
            return true ;
        else return false ;
    }


    public static boolean isForeground(Context context) {

         ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
         List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
         if (appProcesses == null) {
             Log.e("isForeground" , "false")       ;
             return false;
         }
         final String packageName = context.getPackageName();
         for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
             if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                Log.e("isForeground" , "true")       ;
                 return true;
             }
         }
         Log.e("isForeground" , "false")       ;
         return false;
    }
}
