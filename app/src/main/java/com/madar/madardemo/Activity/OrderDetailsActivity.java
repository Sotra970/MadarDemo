package com.madar.madardemo.Activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.madar.madardemo.AppManger.MadarApplication;
import com.madar.madardemo.Fragment.AddOrder.PackageDetailsFragment;
import com.madar.madardemo.Interface.NoConn;
import com.madar.madardemo.Model.OrderItem;
import com.madar.madardemo.Model.OrderModel;
import com.madar.madardemo.Model.ServerResponse.ConfirmationResponse;
import com.madar.madardemo.Model.ServerResponse.UserOrdersResponse;
import com.madar.madardemo.R;
import com.madar.madardemo.Service.CallbackWithRetry;
import com.madar.madardemo.Service.Injector;
import com.madar.madardemo.Service.onRequestFailure;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class OrderDetailsActivity extends FragmentSwitchActivity {

    @BindView(R.id.progressView)
    View progrssView;
    @BindView(R.id.container)
    View containerHolder;

    @BindView(R.id.details_id)
    TextView details_id;

    OrderItem orderItem ;
    @BindView(R.id.order_delete)
    View order_delete_icon ;

    @Override
    public void showLoading(boolean show) {
//        super.showLoading(show);
        progrssView.setVisibility(show ? View.VISIBLE : View.GONE);
        containerHolder.setVisibility(!show ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        overridePendingTransition(R.anim.enter_from_right , R.anim.exit_to_left);
        ButterKnife.bind(this);
        initLoading(this.progrssView, this.containerHolder);
        showLoading(true);

        if (savedInstanceState !=null)
        orderItem = (OrderItem) savedInstanceState.getSerializable("orderItem");


        getOrderDetails();
    }

    void getOrderDetails(){
        if ( orderItem==null && getIntent().getExtras().get("extra_order") !=null){
            this.orderItem  = (OrderItem) getIntent().getExtras().get("extra_order");
            details_id.setText(orderItem.getID());
            showOrderDetailsFragment();

        }else{
            String extra_order_id  =  getIntent().getExtras().getString("extra_order_id");
            details_id.setText(extra_order_id);
            Call<UserOrdersResponse> call = Injector.Api().getOrderByID(
                    extra_order_id,
                    MadarApplication.getInstance().getPrefManager().getUser().getSecret()
            );
            call.enqueue(new CallbackWithRetry<UserOrdersResponse>(5, 30000, call, new onRequestFailure() {
                @Override
                public void onFailure() {
                    showNoConn(new NoConn() {
                        @Override
                        public void onRetry() {
                            getOrderDetails();
                        }
                    });
                }
            }) {
                @Override
                public void onResponse(Call<UserOrdersResponse> call, Response<UserOrdersResponse> response) {
                    if (response.body().getStatus().isSuccessful()){
                        orderItem = response.body().getOrders().get(0);
                        showOrderDetailsFragment();
                    }
//                    showLoading(false);
                }
            });
        }
    }
    void showOrderDetailsFragment(){
        showLoading(false);
        PackageDetailsFragment packageDetailsFragment = new PackageDetailsFragment();
        packageDetailsFragment.setOrderItem(orderItem);
        showFragment(packageDetailsFragment , false);
        Log.e("ids",orderItem.getCustomer_ID()+"  " +MadarApplication.getInstance().getPrefManager().getUser().getID());
        if (orderItem.getCustomer_ID().equals(MadarApplication.getInstance().getPrefManager().getUser().getID())
                && orderItem.getCase_ID().equals("1")
                ){
            order_delete_icon.setVisibility(View.VISIBLE);
        }else {
            order_delete_icon.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_from_left , R.anim.exit_to_right);
    }


    @OnClick(R.id.back_btt)
    void back(){
        onBackPressed();
    }

    @OnClick(R.id.order_delete)
    void order_delete(){
       new AlertDialog.Builder(this).
               setMessage(getString(R.string.delete_confirm))
               .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                    delete_req_conf();
                   }
               }).setNegativeButton(getString(R.string.text_cancel), new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {

           }
       }).create().show(); ;

    }


    void delete_req_conf(){
        showLoading(true);
        Call<ConfirmationResponse> call = Injector.Api().RemoveRequest(
                MadarApplication.getInstance().getPrefManager().getUser().getSecret() ,
                orderItem.getID()
        ) ;
        call.enqueue(new CallbackWithRetry<ConfirmationResponse>(3, 3000, call, new onRequestFailure() {
            @Override
            public void onFailure() {
                showNoConn(new NoConn() {
                    @Override
                    public void onRetry() {
                        delete_req_conf();
                    }
                });
            }
        }) {
            @Override
            public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {
                if (response.body().getResponseItem() !=null){
                    if (response.body().getResponseItem().isSuccessful()){
                        showLoading(false);
                        Toast.makeText(getApplicationContext() , getString(R.string.order_deleted)  , Toast.LENGTH_LONG).show();
                        back();
                    }else if (response.body().getResponseItem().getStatusCode() == 500){
                        showLoading(false);
                        Toast.makeText(getApplicationContext() , getString(R.string.order_cant_be_deleted)  , Toast.LENGTH_LONG).show();
                    }else showLoading(false);
                }
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("orderItem" , orderItem);
    }


}
