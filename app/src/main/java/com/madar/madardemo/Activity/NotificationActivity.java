package com.madar.madardemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.madar.madardemo.Adapter.NotificationListAdapter;
import com.madar.madardemo.AppManger.MadarApplication;
import com.madar.madardemo.Interface.NoConn;
import com.madar.madardemo.Model.NotificationListItem;
import com.madar.madardemo.Model.ServerResponse.DeleteNotificationResponse;
import com.madar.madardemo.Model.ServerResponse.GetNotificationResponse;
import com.madar.madardemo.R;
import com.madar.madardemo.Service.CallbackWithRetry;
import com.madar.madardemo.Service.Injector;
import com.madar.madardemo.Service.onRequestFailure;
import com.madar.madardemo.Util.NotificationCenter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Ahmed on 8/18/2017.
 */

public class NotificationActivity extends FragmentSwitchActivity
{

    private NotificationListAdapter notificationAdapter;


    private View loadingLayout;
    private View emptyLayout;

    private RecyclerView recyclerView;

    public static NotificationActivity getInstance() {
        return new NotificationActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        recyclerView = (RecyclerView)
                findViewById(R.id.fragment_notifications_recycler_view);

        loadingLayout = findViewById(R.id.progress_layout);
        emptyLayout = findViewById(R.id.no_data_layout);


        initRecyclerView(recyclerView);

        initActionToolbar();

        TextView titleTextView = (TextView)
                findViewById(R.id.activity_toolbar_title);

        if(titleTextView != null){
            titleTextView.setText(R.string.fragment_title_notifications);
        }

        overridePendingTransition(R.anim.activity_slide_up, R.anim.fade_out);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadNotifications();
    }

    public void initActionToolbar() {
        Toolbar actionBar = (Toolbar) findViewById(R.id.activity_nav_drawer_toolbar);

        ImageView button = (ImageView) findViewById(R.id.fragment_toolbar_notification_icon);
        if(button != null){
            button.setVisibility(View.GONE);
        }

        if(actionBar != null){
            actionBar.setContentInsetsAbsolute(0, 0);
            actionBar.getContentInsetEnd();
            actionBar.setPadding(0, 0, 0, 0);

            setSupportActionBar(actionBar);

            if(getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                getSupportActionBar().setDisplayShowCustomEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.cancel);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return true;
    }

    private void initRecyclerView(final RecyclerView recyclerView){
        if(recyclerView != null){
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);

            notificationAdapter = new NotificationListAdapter(
                    null,
                    this,
                    new NotificationItemCLickListener() {
                        @Override
                        public void onItemClicked(NotificationListItem notificationListItem) {
                            if(notificationListItem != null && notificationListItem.getAction().equals("notification")){
                                NotificationCenter.setNotificationSeen(notificationListItem.getId(), NotificationActivity.this);
                                Intent intent = new Intent(getApplicationContext() , OrderDetailsActivity.class) ;
                                intent.putExtra("extra_order_id" , notificationListItem.getOrderId()) ;
                                startActivity(intent);
                            }

                            if(notificationListItem != null && notificationListItem.getAction().equals("notification_location")){
                                NotificationCenter.setNotificationSeen(notificationListItem.getId(), NotificationActivity.this);
                                Intent intent = new Intent(getApplicationContext() , ShowLocationActivity.class) ;
                                intent.putExtra("latlung" , notificationListItem.getLatlung()) ;
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onDeleteIconClicked(NotificationListItem notificationListItem) {
                            deleteNotification(notificationListItem);
                        }
                    }
            );

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(notificationAdapter);
        }
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(0, R.anim.activity_slide_down);
    }

    private void showEmptyLayout(boolean show){
        if(emptyLayout != null){
            emptyLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    public void showLoading(boolean show){
        if(loadingLayout != null){
            loadingLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    private void loadNotifications(){
        String secret = null;

        try {
            secret = MadarApplication.getInstance().getPrefManager().getUser().getSecret();
        }
        catch (Exception e){
            // ignore
        }

        if(secret != null){
            showLoading(true);

            final Call<GetNotificationResponse> receiptItemCall = Injector.Api()
                    .getNotifications("Notifications", secret);

            receiptItemCall.enqueue(new CallbackWithRetry<GetNotificationResponse>(
                    CallbackWithRetry.HTTP_REQUEST_RETRY_COUNT,
                    CallbackWithRetry.HTTP_REQUEST_RETRY_INTERVAl_MILISECONDS,
                    receiptItemCall,
                    new onRequestFailure() {
                        @Override
                        public void onFailure() {
                            Log.e("orders Request", "failure");
                            showNoConn(
                                    new NoConn() {
                                        @Override
                                        public void onRetry() {
                                            loadNotifications();
                                        }
                                    }
                            );

                            showLoading(false);
                        }
                    })
            {

                @Override
                public void onResponse(Call<GetNotificationResponse> call, Response<GetNotificationResponse> response) {
                    GetNotificationResponse getNotificationResponse = response.body();
                    if(getNotificationResponse != null){
                        final ArrayList<NotificationListItem> notificationListItems =
                                getNotificationResponse.getNotificationListItems();


                        if(MadarApplication.getExecutorService() != null){
                            MadarApplication.getExecutorService()
                                    .submit(
                                            new Runnable() {
                                                @Override
                                                public void run() {
                                                    initNotifs(notificationListItems);

                                                    Handler uiHandler = new Handler(Looper.getMainLooper());
                                                    uiHandler.post(
                                                            new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    if(notificationAdapter != null){
                                                                        notificationAdapter.updateData(notificationListItems);

                                                                        if(notificationAdapter.isDataSetEmpty()){
                                                                            showEmptyLayout(true);
                                                                        }

                                                                        else{
                                                                            showEmptyLayout(false);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                    );
                                                }
                                            }
                                    );
                        }
                    }

                    showLoading(false);
                }
            });
        }
    }

    private void initNotifs(ArrayList<NotificationListItem> notificationItems){
        // do not isFoundModelCall on ui thread
        if(notificationItems != null && !notificationItems.isEmpty()){
            for (NotificationListItem notificationListItem : notificationItems){
                notificationListItem.initDate();
                notificationListItem.setSeen(
                        NotificationCenter.isNotificationSeen(notificationListItem.getId(), NotificationActivity.this)
                                ? "1"
                                : "0");
            }
        }
    }

    private void deleteNotification(final NotificationListItem notificationListItem){
        String secret = null;

        try {
            secret = MadarApplication.getInstance().getPrefManager().getUser().getSecret();
        }
        catch (Exception e){
            // ignore
        }

        if(secret != null){
            showLoading(true);

            final Call<DeleteNotificationResponse> receiptItemCall = Injector.Api()
                    .deleteNotification("DeleteNotification", secret, notificationListItem.getId());

            receiptItemCall.enqueue(new CallbackWithRetry<DeleteNotificationResponse>(
                    5,
                    3000,
                    receiptItemCall,
                    new onRequestFailure() {
                        @Override
                        public void onFailure() {
                            showNoConn(
                                    new NoConn() {
                                        @Override
                                        public void onRetry() {
                                            deleteNotification(notificationListItem);
                                        }
                                    }
                            );

                            showLoading(false);
                        }
                    })
            {

                @Override
                public void onResponse(Call<DeleteNotificationResponse> call, Response<DeleteNotificationResponse> response) {
                    DeleteNotificationResponse deleteNotificationResponse = response.body();

                    if(notificationAdapter != null){
                        notificationAdapter.removeItem(notificationListItem);
                    }

                    showLoading(false);
                }
            });
        }
    }

    public interface NotificationItemCLickListener{
        void onItemClicked(NotificationListItem notificationListItem);
        void onDeleteIconClicked(NotificationListItem notificationListItem);
    }
}
