package com.madar.madardemo.Activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.messaging.FirebaseMessaging;
import com.madar.madardemo.Adapter.NavDrawerItemAdapter;
import com.madar.madardemo.AppManger.Config;
import com.madar.madardemo.AppManger.MadarApplication;
import com.madar.madardemo.AppManger.MyPreferenceManager;
import com.madar.madardemo.Decorator.DividerItemDecoration;
import com.madar.madardemo.Fragment.AboutUsFragment;
import com.madar.madardemo.Fragment.ContactUsFragment;
import com.madar.madardemo.Fragment.DeliveryDestinationFragment;
import com.madar.madardemo.Fragment.OrdersFragment;
import com.madar.madardemo.Fragment.PreferenceFragment;
import com.madar.madardemo.Fragment.ProfileFragment;
import com.madar.madardemo.Fragment.ReceiptsFragment;
import com.madar.madardemo.Fragment.ShowUSerBanksFragment;
import com.madar.madardemo.Fragment.TransferSummaryFragment;
import com.madar.madardemo.Listener.NavDrawerItemClickListener;
import com.madar.madardemo.Model.NavDrawerPageItem;
import com.madar.madardemo.Model.isFoundModel;
import com.madar.madardemo.R;
import com.madar.madardemo.SecureJobScheduler.Util;
import com.madar.madardemo.Service.Injector;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ahmed on 8/16/2017.
 */

public class NavDrawerActivity extends FragmentSwitchActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        NavDrawerItemClickListener
{
    private TextView titleTextView;
    private DrawerLayout drawerLayout;

    private NavDrawerItemAdapter navDrawerItemAdapter;
    private ArrayList<NavDrawerPageItem> navDrawerPageItems;

    private Fragment currentFragment;

    private ArrayList<DrawerLayout.DrawerListener> drawerListeners;

    ImageView profile_bg;
    ImageView profile_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        ButterKnife.bind(this);

        check_google_play_version();
        titleTextView = (TextView)
                findViewById(R.id.activity_toolbar_title);

        initActionToolbar();
        initNavDrawer();

        currentFragment = DeliveryDestinationFragment.getInstance();
        showFragment(currentFragment , false);

        refresh_notfication();
        overridePendingTransition(R.anim.activity_slide_up, R.anim.fade_out);
        Log.e("incom" , "notifyallstore") ;
        FirebaseMessaging.getInstance().subscribeToTopic("Country_0");
        FirebaseMessaging.getInstance().subscribeToTopic("Country_"+MadarApplication.getUser().getCountryID());
        FirebaseMessaging.getInstance().subscribeToTopic("Country_"+MadarApplication.getUser().getCountryID()+"_City_"+MadarApplication.getUser().getCityID());
//        FirebaseMessaging.getInstance().subscribeToTopic("notifyalldrivers");

        checkUser();


    }

    public void setFragmentTitle(int titleResId){
        if(titleTextView != null){
            titleTextView.setText(titleResId);
        }
    }

    public void initNavDrawer() {
       RecyclerView navDrawerRecyclerView = (RecyclerView)
                findViewById(R.id.navigation_view_recycler_view);

        if (navDrawerRecyclerView != null) {
            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(this);

            navDrawerRecyclerView.setLayoutManager(linearLayoutManager);

            if (navDrawerItemAdapter == null) {

                navDrawerItemAdapter =
                        new NavDrawerItemAdapter(
                                getNavDrawerItems(),
                                this,
                                this
                        );
            }

            DividerItemDecoration horizontalDividerDecoration =
                    new DividerItemDecoration(
                            this,
                            R.drawable.separator
                    );
            horizontalDividerDecoration.setActivated(true);
            navDrawerRecyclerView.addItemDecoration(horizontalDividerDecoration);

            navDrawerRecyclerView.setAdapter(navDrawerItemAdapter);
            try {
                checkNavDrawerSelectedItems();
            }catch (Exception e){}

        }

        drawerLayout = (DrawerLayout)
                findViewById(R.id.activity_nav_drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ){
            @Override
            public void onDrawerOpened(View drawerView) {
                if(drawerListeners != null && !drawerListeners.isEmpty()){
                    for(DrawerLayout.DrawerListener listener : drawerListeners){
                        listener.onDrawerOpened(drawerView);
                    }
                }
                try {
                    checkNavDrawerSelectedItems();
                }catch (Exception e){}

                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if(drawerListeners != null && !drawerListeners.isEmpty()){
                    for(DrawerLayout.DrawerListener listener : drawerListeners){
                        listener.onDrawerClosed(drawerView);
                    }
                }
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        header_bind();
        View logout_action = findViewById(R.id.logout_action);
        logout_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MadarApplication.getInstance().getPrefManager().clear();
            }
        });

    }

    private void header_bind() {
        NavigationView navigationView = (NavigationView)
                findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView hedar_store_name = (TextView) findViewById(R.id.navigation_view_header_store_text_view);
        hedar_store_name.setText(MadarApplication.getInstance().getPrefManager().getUser().getName());

        TextView hedar_agent_name = (TextView) findViewById(R.id.navigation_view_header_title_text_view);
        hedar_agent_name.setText(MadarApplication.getInstance().getPrefManager().getUser().getFirstName());



        profile_img = (ImageView) findViewById(R.id.navigation_view_header_image_view);
        profile_bg = (ImageView) findViewById(R.id.navigation_view_header_image_view_bg);

        Glide.with(this)
                .load(R.drawable.circular_profile_background)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(new DrawableTransitionOptions().crossFade())
                .thumbnail(0.5f)
                .into(profile_bg);

        Glide.with(this)
                .load(MadarApplication.getInstance().getPrefManager().getUser().getImage() )
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .apply(new RequestOptions().centerCrop())
                .apply(new RequestOptions().circleCrop())
                .transition(new DrawableTransitionOptions().crossFade())
                .thumbnail(0.5f)
                .into(profile_img) ;



    }

    private ArrayList<NavDrawerPageItem> getNavDrawerItems(){
        if(navDrawerPageItems == null){
            navDrawerPageItems = new ArrayList<>();

            navDrawerPageItems.add(
                    new NavDrawerPageItem(R.string.nav_drawer_title_main_page, R.drawable.home_menu)
            );

            navDrawerPageItems.add(
                    new NavDrawerPageItem(R.string.nav_drawer_title_profile, R.drawable.user)
            );

            navDrawerPageItems.add(
                    new NavDrawerPageItem(R.string.nav_drawer_title_orders, R.drawable.truck)
            );

            navDrawerPageItems.add(
                    new NavDrawerPageItem(R.string.nav_drawer_title_receipts, R.drawable.contract)
            );



            navDrawerPageItems.add(
                    new NavDrawerPageItem(R.string.nav_drawer_title_wire_transfer_summary, R.drawable.dollar_bill)
            );

            navDrawerPageItems.add(
                    new NavDrawerPageItem(R.string.nav_drawer_title_bank_account, R.drawable.bank_acc)
            );

            navDrawerPageItems.add(
                    new NavDrawerPageItem(R.string.nav_drawer_title_about_us, R.drawable.signature)
            );

            navDrawerPageItems.add(
                    new NavDrawerPageItem(R.string.nav_drawer_title_contact_with_us, R.drawable.phone_call)
            );

            navDrawerPageItems.add(
                    new NavDrawerPageItem(R.string.nav_drawer_title_settings, R.drawable.settings)
            );
        }



        return navDrawerPageItems;
    }

    public void initActionToolbar() {
        Toolbar actionBar = getToolbar();

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
            }
        }
    }

    private Toolbar getToolbar(){
        try{
            return (Toolbar) findViewById(R.id.activity_nav_drawer_toolbar);
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkNavDrawerSelectedItems(){
        if(navDrawerItemAdapter != null){
            if(navDrawerItemAdapter.getSelectedItem() == null){
                if(currentFragment != null && currentFragment instanceof DeliveryDestinationFragment){
                    navDrawerItemAdapter.selectItem(0);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onNavDrawerItemClicked(NavDrawerPageItem navDrawerItem) {
        int titleResId = navDrawerItem.getTitleResId();

        switch (titleResId){
            case R.string.nav_drawer_title_main_page:
                currentFragment = DeliveryDestinationFragment.getInstance();
                break;

            case R.string.nav_drawer_title_profile:
                currentFragment = ProfileFragment.getInstance();
                break;

            case R.string.nav_drawer_title_about_us:
                currentFragment = AboutUsFragment.getInstance();
                break;

            case R.string.nav_drawer_title_contact_with_us:
                currentFragment = ContactUsFragment.getInstance();
                break;

            case R.string.nav_drawer_title_receipts:
                currentFragment = ReceiptsFragment.getInstance();
                break;

            case R.string.nav_drawer_title_wire_transfer_summary:
                currentFragment = TransferSummaryFragment.getInstance();
                break;

            case R.string.nav_drawer_title_settings:
                currentFragment = PreferenceFragment.getInstance();
                break;

            case R.string.nav_drawer_title_bank_account:
                currentFragment = ShowUSerBanksFragment.getInstance();
                break;

            case R.string.nav_drawer_title_orders:
                currentFragment = OrdersFragment.getInstance();
                break;
        }

        if(currentFragment != null){
            showFragment(currentFragment ,false);
        }

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        if(drawerLayout != null){
                            drawerLayout.closeDrawers();
                        }
                    }
                },
                250L
        );
    }

    public void onNotificationItemClicked(View v){
        MadarApplication.getInstance().getPrefManager().CLEAR_NOTFICATiON();
        refresh_notfication();
        Intent i = new Intent(this, NotificationActivity.class);
        startActivity(i);
    }

    @Override
    public void showFragment(Fragment fragment, boolean back) {
        this.currentFragment = fragment;
        super.showFragment(fragment, back);
    }

    @Override
    public void showFragment(Fragment fragment, String tag) {
        this.currentFragment = fragment;
        super.showFragment(fragment, tag);
    }



    void check_google_play_version(){
        // Configure Google
        // start
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        //end
    }

    public void addDrawerListener(DrawerLayout.DrawerListener drawerListener){
        if(drawerListener != null){
            if(drawerListeners == null){
                drawerListeners = new ArrayList<>();
            }
            drawerListeners.add(drawerListener);
        }
    }

    public void removeDrawerListener(DrawerLayout.DrawerListener drawerListener){
        if(drawerListener != null && drawerListeners != null && !drawerListeners.isEmpty()){
            try {
                drawerListeners.remove(drawerListener);
            }
            catch (Exception e){}
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @BindView(R.id.messages_notification_count)
    TextView notfication_count  ;
    @BindView(R.id.messages_notification_count_con)
    View notfication_container ;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("refresh_notfication"  , "onrecive");
            if (intent.getAction().equals(Config.UPDATE_USER_PROFILE)){
                header_bind();
            }else
            try {
                refresh_notfication() ;

            }catch (Exception e){}
        }
    };

    void refresh_notfication(){

        try {
            int cont = MadarApplication.getInstance().getPrefManager().get_notfication()  ;
            Log.e("refresh_notfication"  , "cont "+cont);

            if (cont == 0 )
            {
                notfication_container.setVisibility(View.GONE);
            }
            else{
                notfication_container.setVisibility(View.VISIBLE);
                notfication_count.setText(cont+"");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume" , "onResume") ;

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                broadcastReceiver,
                new IntentFilter( MyPreferenceManager.KEY_INCREMENT_NOTFICATiON));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy" , "onDestroy") ;
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(broadcastReceiver);
    }



    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver , new IntentFilter(Config.UPDATE_USER_PROFILE));
    }


    Call<isFoundModel> isFoundModelCall;
    CountDownTimer timer ;
    boolean firstCheck  = true ;
    void checkUser(){
        if (MadarApplication.getInstance().getPrefManager().getUser() ==null){
            return;
        }
          timer  = new CountDownTimer( firstCheck ? 3000 : 1*60*1000  , 1000 ) {
            @Override
            public void onTick(long l) {
                firstCheck = false ;
            }

            @Override
            public void onFinish()  {
                firstCheck =false ;
                checkSecurerequest();
            }
        };
          timer.start();
    }


    void checkSecurerequest(){
        timer.cancel();
        try {
            if (MadarApplication.getInstance().getPrefManager().getUser() ==null){
                return;
            }
            isFoundModelCall = Injector.Api().iSFound(MadarApplication.getInstance().getPrefManager().getUser().getSecret()) ;
            isFoundModelCall.enqueue(new Callback<isFoundModel>() {
                @Override
                public void onResponse(Call<isFoundModel> call, Response<isFoundModel> response) {
                    if (response !=null && response.body()!=null){
                        if (response.body().getResponseItem().isSuccessful()){
                            checkUser();
                        }else {
                            MadarApplication.getInstance().getPrefManager().clear(false);
                            timer.cancel();
                            AlertDialog  alertDialog =  new AlertDialog.Builder(NavDrawerActivity.this)
                                    .setMessage(R.string.please_relogin)
                                    .setCancelable(false)
                                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            MadarApplication.getInstance().getPrefManager().clear();

                                        }
                                    }).create();
                            alertDialog.show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<isFoundModel> call, Throwable t) {
                    checkUser();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
