package com.madar.madardemo.FCM;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;

import com.bumptech.glide.request.target.NotificationTarget;
import com.google.firebase.messaging.RemoteMessage;
import com.madar.madardemo.Activity.NotificationActivity;
import com.madar.madardemo.Activity.OrderDetailsActivity;
import com.madar.madardemo.Activity.ShowLocationActivity;
import com.madar.madardemo.Activity.SplashActivity;
import com.madar.madardemo.AppManger.MadarApplication;
import com.madar.madardemo.AppManger.MyPreferenceManager;
import com.madar.madardemo.R;
import com.madar.madardemo.Util.NotificationCenter;

import java.util.Calendar;
import java.util.Map;

/**
 * Created by Sotraa on 6/15/2016.
 */
public class recivedMesseageHandel {
    Context context ;
    int ID = 0 ;
    private NotificationTarget notificationTarget;

    public recivedMesseageHandel(Context context, RemoteMessage remoteMessage){
        this.context = context.getApplicationContext() ;

    try {

        if (remoteMessage.getData().get("action").equals("notification")){

            sendNotification(remoteMessage.getData().get("message") , remoteMessage.getData().get("order_id"));
        }

        if (remoteMessage.getData().get("action").equals("notification_location")){

            sendNotification(remoteMessage.getData().get("message") , remoteMessage.getData().get("order_id") ,remoteMessage.getData().get("latlung") );
        }

        if (remoteMessage.getData().get("action").contains("NotifyAllStore")){

            if (remoteMessage.getData().get("City").equals("0") ){
                //show all users in this country
                if ( remoteMessage.getData().get("Country").equals("0")|| remoteMessage.getData().get("Country").equals(MadarApplication.getUser().getCountryID())){
                    sendNotification(remoteMessage.getData().get("message"));
                }
            }else if ( remoteMessage.getData().get("City").equals(MadarApplication.getUser().getCityID())) {
                //show all users in this city
                sendNotification(remoteMessage.getData().get("message"));

            }

        }
    }catch (Exception e){
        Log.e("receive fcm exception",e.toString());
    }

    }


//
//
    private void sendNotification(String mes) {
        inc_noti();
        Intent intent = new Intent(context, SplashActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bitmap = BitmapFactory.decodeResource( context.getResources(), R.mipmap.ic_launcher);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(mes)
                .setLargeIcon(bitmap)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setSound(defaultSoundUri)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority( NotificationCompat.PRIORITY_DEFAULT)
                ;


        final Notification notification = notificationBuilder.build();

        long id =  Calendar.getInstance().getTimeInMillis() ;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify((int) id,notification);
//
    }
    private void sendNotification(String mes ,String  order_id) {
        inc_noti();
        Intent intent = new Intent(context, OrderDetailsActivity.class);
        intent.putExtra("extra_order_id" , order_id) ;

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bitmap = BitmapFactory.decodeResource( context.getResources(), R.mipmap.ic_launcher);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(mes)
                .setLargeIcon(bitmap)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setSound(defaultSoundUri)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority( NotificationCompat.PRIORITY_DEFAULT)
                ;


        final Notification notification = notificationBuilder.build();

        long id =  Calendar.getInstance().getTimeInMillis() ;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify((int) id,notification);
//
    }
    private void sendNotification(String mes ,String  order_id , String latlung) {
        inc_noti();
        Intent intent = new Intent(context , ShowLocationActivity.class) ;
        intent.putExtra("latlung" , latlung) ;

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bitmap = BitmapFactory.decodeResource( context.getResources(), R.mipmap.ic_launcher);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(mes)
                .setLargeIcon(bitmap)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setSound(defaultSoundUri)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority( NotificationCompat.PRIORITY_DEFAULT)
                ;


        final Notification notification = notificationBuilder.build();

        long id =  Calendar.getInstance().getTimeInMillis() ;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify((int) id,notification);
//
    }


    void inc_noti(){
        MadarApplication.getInstance().getPrefManager().INCREMENT_NOTFICATiON();
        Intent intent = new Intent() ;
        intent.setAction(MyPreferenceManager.KEY_INCREMENT_NOTFICATiON);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}