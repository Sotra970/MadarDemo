package com.madar.madardemo.Activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;

import com.madar.madardemo.Adapter.No_Title_Pager_Adapter;
import com.madar.madardemo.AppManger.Config;
import com.madar.madardemo.Fragment.AddOrder.FavLocationsFragment;
import com.madar.madardemo.Fragment.AddOrder.OrderConfirmationMessageFragment;
import com.madar.madardemo.Fragment.AddOrder.PackageContentInfoFragment;
import com.madar.madardemo.Fragment.AddOrder.PackageDetailsFragment;
import com.madar.madardemo.Fragment.AddOrder.PackagePriceFragment;
import com.madar.madardemo.Fragment.AddOrder.ReciverInfo.PackageReciverInfoFragment;
import com.madar.madardemo.Fragment.AddOrder.ReceivingDateTimeFragment;
import com.madar.madardemo.Fragment.AddOrder.ReceivingPlace1Fragment;
import com.madar.madardemo.Fragment.AddOrder.ReceivingPlace2Fragment;
import com.madar.madardemo.Interface.OnBackClick;
import com.madar.madardemo.Interface.OnNextClick;
import com.madar.madardemo.Model.OrderItem;
import com.madar.madardemo.Model.OrderModel;
import com.madar.madardemo.Model.TabModel;
import com.madar.madardemo.R;
import com.madar.madardemo.View.Widget.NonSwipeViewPager;
import com.madar.madardemo.ViewHolder.TabTitleHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddOrderActivity extends FragmentSwitchActivity implements OnNextClick , OnBackClick {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout ;
    @BindView(R.id.pager)
    NonSwipeViewPager pager ;
    No_Title_Pager_Adapter pager_adapter ;

    static public OrderModel orderModel ;
    static public OrderItem orderItem ;
    static public String extra_type ;
    final static public String extra_type_same = "same" ;
    final  static public String extra_type_other = "other" ;


   public boolean isSame(){
        return  extra_type.equals(extra_type_same) ;
    }


    public  boolean isOther(){
        return  extra_type.equals(extra_type_other) ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        overridePendingTransition(R.anim.enter_from_right , R.anim.exit_to_left);
         extra_type =  getIntent().getExtras().getString("extra_type") ;
        orderModel = new OrderModel() ;
        ButterKnife.bind(this);
        setup_pager();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Changa-Medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );


    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    ArrayList<TabModel> tabModels = new ArrayList<>() ;
    void  setup_pager(){
        ReceivingPlace1Fragment receivingPlace1Fragment = ReceivingPlace1Fragment.getInstance() ;
//        ReceivingPlace2Fragment receivingPlace2Fragment = ReceivingPlace2Fragment.getInstance() ;
        ReceivingDateTimeFragment receivingDateTimeFragment = ReceivingDateTimeFragment.getInstance() ;
        PackageContentInfoFragment packageContentInfoFragment = PackageContentInfoFragment.getInstance() ;
        PackageReciverInfoFragment packageReciverInfoFragment = PackageReciverInfoFragment.getInstance() ;
        PackagePriceFragment packagePriceFragment = PackagePriceFragment.getInstance() ;
        PackageDetailsFragment packageDetailsFragment = PackageDetailsFragment.getInstance() ;
        OrderConfirmationMessageFragment orderConfirmationMessageFragment = OrderConfirmationMessageFragment.getInstance() ;

        tabModels.add(new TabModel(receivingPlace1Fragment , getString(R.string.reciving_place) ));
//        tabModels.add(new TabModel(receivingPlace2Fragment , getString(R.string.reciving_place) ));
        tabModels.add(new TabModel(receivingDateTimeFragment , getString(R.string.reciving_date_time) ));

        tabModels.add(new TabModel(packageReciverInfoFragment , getString(R.string.package_receiver_info) ));
        tabModels.add(new TabModel(packageContentInfoFragment , getString(R.string.package_content) ));

        tabModels.add(new TabModel(packagePriceFragment, getString(R.string.package_price) ));
        tabModels.add(new TabModel(packageDetailsFragment, getString(R.string.package_details) ));
        tabModels.add(new TabModel(orderConfirmationMessageFragment, getString(R.string.order_done) ));

        Config.pages_count = tabModels.size()-1 ;

        int margin_dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics());
//         Disable clip to padding
        pager.setClipToPadding(false);
        // set padding manually, the more you set the padding the more you see of prev & next page
        //     viewPager.setPadding(padding_dp, 0, padding_dp, 0);
        // sets a margin b/w individual pages to ensure that there is a gap b/w them
        pager.setPageMargin(margin_dp);
        pager.setOffscreenPageLimit(1);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int pos) {
                tabModels.get(pos).getFragment().onPageSelected(pos);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        pager_adapter = new No_Title_Pager_Adapter(getSupportFragmentManager()) ;
        add_tabs();

    }

    void add_tabs(){
        for (TabModel tabModel : tabModels){
            pager_adapter.addFragment(tabModel.getFragment());
        }
        pager.setAdapter(pager_adapter);
        tabLayout.setupWithViewPager(pager);
        for (int i=0 ;  i<tabModels.size() ; i++){
            final TabModel tabModel = tabModels.get(i) ;
            final int tab_pos = i ;
                    tabLayout.getTabAt(tab_pos).setCustomView(new TabTitleHolder(tabModel.getTitle() , getApplicationContext()).getView()) ;
        }
        pager.setCurrentItem(0);
    }

    @OnClick(R.id.back_btt)
    void back(){
       onBackPressed();
    }


    @Override
    public void onBackPressed() {
       if (getSupportFragmentManager().getBackStackEntryCount() == 0){
           finish();
           overridePendingTransition(R.anim.enter_from_left , R.anim.exit_to_right);
       }else         super.onBackPressed();

    }

    @Override
    public void next(OrderModel orderModel) {
        this.orderModel = orderModel ;
        pager.setCurrentItem((pager.getCurrentItem()+1) , true);
    }


    @Override
    public void backClick() {
        pager.setCurrentItem((pager.getCurrentItem()-1) , true);
    }
}
