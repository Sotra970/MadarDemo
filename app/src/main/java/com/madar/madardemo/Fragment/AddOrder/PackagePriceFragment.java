package com.madar.madardemo.Fragment.AddOrder;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.madar.madardemo.AppManger.MadarApplication;
import com.madar.madardemo.Fragment.Abstract.AddOrderBaseFragment;
import com.madar.madardemo.Interface.NoConn;
import com.madar.madardemo.Model.AddOrderResponseModel;
import com.madar.madardemo.R;
import com.madar.madardemo.Service.CallbackWithRetry;
import com.madar.madardemo.Service.Injector;
import com.madar.madardemo.Service.onRequestFailure;
import com.madar.madardemo.Util.Validation;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Ahmed on 8/15/2017.
 */

public class PackagePriceFragment extends AddOrderBaseFragment {

    @BindView(R.id.progressView)
    View progrssView ;
    @BindView(R.id.container)
    View containerHolder ;


    @BindView(R.id.pack_value_rd1)
    RadioButton pack_value_rd1 ;

    @BindView(R.id.pack_value_rd2)
    RadioButton pack_value_rd2 ;


    @BindView(R.id.credit_rd)
    RadioButton credit_rd ;


    @BindView(R.id.debit_rd)
    RadioButton debit_rd ;

    @BindView(R.id.pack_value_rd1_text)
    TextView pack_value_rd1_text ;

    @BindView(R.id.pack_value_rd2_text)
    TextView pack_value_rd2_text ;

    @BindView(R.id.pack_count_title)
    TextView pack_count_title ;
    @BindView(R.id.pack_count_txt)
    EditText pack_count_txt ;


    @BindView(R.id.debit_text)
    TextView debit_text ;
    @BindView(R.id.credit_rd_text)
    TextView credit_rd_text ;

    @BindView(R.id.count_plus)
    ImageView count_plus ;

    @BindView(R.id.count_minus)
    ImageView count_minus ;

    @BindView(R.id.pack_value_sec)
    View pack_value_sec ;
    @BindView(R.id.pck_warn_text)
    View pck_warn_text ;
    @BindView(R.id.pack_value_rd2_container)
    View pack_value_rd2_container ;



    int color_grey , color_black , yellow1 ;

    public static PackagePriceFragment getInstance() {
        return new PackagePriceFragment();
    }
    View v ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (v == null) {
            v = inflater.inflate(R.layout.fragment_package_price ,  container, false);
            ButterKnife.bind(this, v);
            initLoading(this.progrssView, this.containerHolder);
//            pack_count_txt.addTextChangedListener(new Validation.PackageCountTextWatcher(pack_count_txt));
            count_plus.setClickable(false);
            count_minus.setClickable(false);
            pack_count_txt.setEnabled(false);
            color_grey  = ContextCompat.getColor(getActivity() , R.color.grey_700) ;
            color_black  = ContextCompat.getColor(getActivity() , R.color.black) ;
            yellow1  = ContextCompat.getColor(getActivity() , R.color.app_yellow_1) ;

            if (getAddOrderActivity().isOther()){
                pack_value_sec.setVisibility(View.GONE);
                pck_warn_text.setVisibility(View.GONE);
                pack_value_rd2_container.setVisibility(View.GONE);
            }else{
                pack_value_sec.setVisibility(View.VISIBLE);
                pck_warn_text.setVisibility(View.VISIBLE);
                pack_value_rd2_container.setVisibility(View.VISIBLE);
            }



        }


        return v;
    }


    @OnClick(R.id.count_minus)
    void count_minus() {
        Log.e("count_minus" , "count_minus_clicked") ;
        try {
            int count = Integer.parseInt(pack_count_txt.getText().toString());
            count-- ;
            pack_count_txt.setText(count + "");
            getOrderModel().setPack_Num(count);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @OnClick(R.id.count_plus)
    void count_plus() {
      try{
          int count = Integer.parseInt(pack_count_txt.getText().toString());
          count++;
          pack_count_txt.setText(count + "");
          getOrderModel().setPack_Num(count);
      }catch (Exception e){
          e.printStackTrace();
      }
    }



    @OnClick(R.id.pack_value_rd1)
    void rd1(){
        pack_value_rd1.setChecked(true);
        pack_value_rd1_text.setTextColor(color_black);

        pack_value_rd2.setChecked(false);
        pack_value_rd2_text.setTextColor(color_grey);
        pack_count_title.setTextColor(color_grey);
        pack_count_txt.setTextColor(color_grey);

        count_plus.setColorFilter(color_grey , PorterDuff.Mode.SRC_ATOP);
        count_minus.setColorFilter(color_grey , PorterDuff.Mode.SRC_ATOP);

        getOrderModel().setShip_Type_ID(1);

        count_plus.setClickable(false);
        count_minus.setClickable(false);
        pack_count_txt.setEnabled(false);

    }


    @OnClick(R.id.pack_value_rd2)
    void rd2(){
        pack_value_rd1.setChecked(false);
        pack_value_rd1_text.setTextColor(color_grey);

        pack_value_rd2.setChecked(true);
        pack_value_rd2_text.setTextColor(color_black);
        pack_count_title.setTextColor(color_black);
        pack_count_txt.setTextColor(color_black);

        count_plus.setColorFilter(yellow1 , PorterDuff.Mode.SRC_ATOP);
        count_minus.setColorFilter(yellow1 , PorterDuff.Mode.SRC_ATOP);

        getOrderModel().setShip_Type_ID(2);

        count_plus.setClickable(true);
        count_minus.setClickable(true);
        pack_count_txt.setEnabled(true);

    }




    @OnClick(R.id.credit_rd)
    void credit_rd(){
        credit_rd.setChecked(true);
        credit_rd_text.setTextColor(color_black);

        debit_rd.setChecked(false);
        debit_text.setTextColor(color_grey);

        getOrderModel().setPayment_Method_ID(1);

        Log.e("payment" , getOrderModel().getPayment_Method_ID() +"") ;

    }


    @OnClick(R.id.debit_rd)
    void debit_rd(){
        debit_rd.setChecked(true);
        debit_text.setTextColor(color_black);

        credit_rd.setChecked(false);
        credit_rd_text.setTextColor(color_grey);
        getOrderModel().setPayment_Method_ID(2);

        Log.e("payment" , getOrderModel().getPayment_Method_ID() +"") ;

    }





    @OnClick(R.id.next)
    void next(){
        try {
            int count  = Integer.parseInt(pack_count_txt.getText().toString().trim());
            if (getOrderModel().getShip_Type_ID() != 1){
                if (count <1){
                    showLongToast(containerHolder , getString(R.string.text_goods_price_err));
                    return;
                }
                getOrderModel().setGoods_Values(count);
            }

        }catch (Exception e){

        }

        Log.e("payment" , getOrderModel().getPayment_Method_ID() +"") ;


      creat_orderO();
    }

    private void creat_orderO() {
        showLoading(true);
        getOrderModel().setStore_ID(MadarApplication.getInstance().getPrefManager().getUser().getSecret());
        Call<AddOrderResponseModel> call = Injector.Api().add_order(
                getOrderModel()
        ) ;
        call.enqueue(new CallbackWithRetry<AddOrderResponseModel>(5, 30000, call, new onRequestFailure() {
            @Override
            public void onFailure() {
                showNoConn(new NoConn() {
                    @Override
                    public void onRetry() {
                        creat_orderO();
                    }
                });
            }
        }) {
            @Override
            public void onResponse(Call<AddOrderResponseModel> call, Response<AddOrderResponseModel> response) {

                if (response.body().getResponseItem().isSuccessful() && !response.body().getOrderItems().isEmpty()){
                    getAddOrderActivity().orderItem = response.body().getOrderItems().get(0) ;
                    showLoading(false);
                    getAddOrderActivity().next(getOrderModel());
                }

            }
        });
    }


    @Override
    public void onPageSelected(int pos) {
        setupPagerPullet(true , pos);
    }
}
