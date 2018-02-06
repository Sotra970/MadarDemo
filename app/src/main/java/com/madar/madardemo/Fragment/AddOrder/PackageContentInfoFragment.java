package com.madar.madardemo.Fragment.AddOrder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.madar.madardemo.AppManger.MadarApplication;
import com.madar.madardemo.Fragment.Abstract.AddOrderBaseFragment;
import com.madar.madardemo.Interface.NoConn;
import com.madar.madardemo.Model.OrderModel;
import com.madar.madardemo.Model.ServerResponse.PriceResponse;
import com.madar.madardemo.R;
import com.madar.madardemo.Service.CallbackWithRetry;
import com.madar.madardemo.Service.Injector;
import com.madar.madardemo.Service.onRequestFailure;
import com.madar.madardemo.Util.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Ahmed on 8/15/2017.
 */

public class PackageContentInfoFragment extends AddOrderBaseFragment {

    @BindView(R.id.progressView)
    View progrssView ;
    @BindView(R.id.container)
    View containerHolder ;




    @BindView(R.id.content_title_input)
    EditText content_title_input  ;


    @BindView(R.id.size_small_rd)
    RadioButton size_small_rd  ;

    @BindView(R.id.size_mid_rd)
    RadioButton size_mid_rd  ;

    @BindView(R.id.size_big_rd)
    RadioButton size_big_rd  ;


    @BindView(R.id.type_normal)
    RadioButton type_normal  ;

    @BindView(R.id.type_cold)
    RadioButton type_cold  ;

    @BindView(R.id.type_hot)
    RadioButton type_hot  ;


    @BindView(R.id.pack_count_txt)
    EditText pack_count_txt  ;

    @BindView(R.id.price_txt)
    TextView price_txt  ;

    @BindView(R.id.title_err)
    TextView title_err  ;

    @BindView(R.id.size_err)
    TextView size_err  ;

    @BindView(R.id.type_err)
    TextView type_err  ;


    @BindView(R.id.img_err)
    TextView img_err  ;
    @BindView(R.id.currency_txt)
    TextView currency_txt  ;











    public static PackageContentInfoFragment getInstance() {
        return new PackageContentInfoFragment();
    }

    @Override
    public void onPageSelected(int pos) {
        setupPagerPullet(true, pos);
        price_txt.setText("--");
//        getprice();
    }

    View v ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (v == null) {
            v = inflater.inflate(R.layout.fragment_package_content_info ,  container, false);
            ButterKnife.bind(this, v);
            initLoading(this.progrssView, this.containerHolder);
            pack_count_txt.addTextChangedListener(new Validation.PackageCountTextWatcher(pack_count_txt));
        }


        return v;
    }


    @OnClick(R.id.select_img)
    void select_img(){
        getAddOrderActivity().pick_image_permission();
    }


    @OnClick(R.id.capture_img)
    void capture_img  (){
        getAddOrderActivity().capture_img_permission();
    }

    @OnClick(R.id.size_small_rd)
    void size_small_rd_clicked(){
        size_mid_rd.setChecked(false);
        size_big_rd.setChecked(false);
        size_small_rd.setChecked(true);
        getOrderModel().setShippingSize(1);
        getprice(getString(R.string.car_size)+getString(R.string.small) ,size_small_rd );
    }


    @OnClick(R.id.size_mid_rd)
    void size_mid_rd_clicked(){
        size_small_rd.setChecked(false);
        size_big_rd.setChecked(false);
        size_mid_rd.setChecked(true);
        getOrderModel().setShippingSize(2);
        getprice(getString(R.string.car_size)+getString(R.string.mid) , size_mid_rd);
    }



    @OnClick(R.id.size_big_rd)
    void size_big_rd_clicked(){
        size_mid_rd.setChecked(false);
        size_small_rd.setChecked(false);
        size_big_rd.setChecked(true);
        getOrderModel().setShippingSize(3);
        getprice(getString(R.string.car_size)+getString(R.string.big) , size_big_rd);
    }



    @OnClick(R.id.type_normal)
    void type_normal_clicked(){
        type_hot.setChecked(false);
        type_cold.setChecked(false);
        type_normal.setChecked(true);
        getOrderModel().setCar_Type_ID(1);
        getprice(getString(R.string.car_type)+getString(R.string.normal),type_normal);
    }


    @OnClick(R.id.type_cold)
    void type_cold_clicked(){
        type_normal.setChecked(false);
        type_hot.setChecked(false);
        type_cold.setChecked(true);
        getOrderModel().setCar_Type_ID(2);
        getprice(getString(R.string.car_type)+getString(R.string.cold) , type_cold);
    }



    @OnClick(R.id.type_hot)
    void type_hot_clicked(){
        type_normal.setChecked(false);
        type_cold.setChecked(false);
        type_hot.setChecked(true);
        getOrderModel().setCar_Type_ID(3);
        getprice(getString(R.string.car_type)+getString(R.string.hot) , type_hot);
    }


    @OnClick(R.id.count_minus)
    void count_minus() {
        int count = Integer.parseInt(pack_count_txt.getText().toString());
        if (count !=1)
        count--;
        pack_count_txt.setText(count + "");
        getOrderModel().setPack_Num(count);

    }


    @OnClick(R.id.count_plus)
    void count_plus() {
        int count = Integer.parseInt(pack_count_txt.getText().toString());
        count++;
        pack_count_txt.setText(count + "");
        getOrderModel().setPack_Num(count);
    }





    @OnClick(R.id.next)
    void next(){
        String title  = content_title_input.getText().toString().trim() ;
       if (TextUtils.isEmpty(title))
       {
           title_err.setVisibility(View.VISIBLE);
           return;
       }else{
           title_err.setVisibility(View.GONE);
            getOrderModel().setTitle(title);
       }

       if (price_txt.getText().toString().trim().equals("--")){

           if (!size_small_rd.isChecked()  && !size_mid_rd.isChecked() && !size_big_rd.isChecked() ){
               size_err.setVisibility(View.VISIBLE);
               return;
           }else {
               size_err.setVisibility(View.GONE);
           }



           if (!type_normal.isChecked()  && !type_cold.isChecked() && !type_hot.isChecked() ){
               type_err.setVisibility(View.VISIBLE);
               return;
           }else {
               type_err.setVisibility(View.GONE);
           }


           showLongToast(containerHolder , getString(R.string.price_error));
           return;

       }


       int count  = Integer.parseInt(pack_count_txt.getText().toString().trim());
        getOrderModel().setPack_Num(count);

//       if (getAddOrderActivity().getUpload_img_result_ids().isEmpty())
//       {
//           img_err.setVisibility(View.VISIBLE);
//           return;
//       }else {
//           img_err.setVisibility(View.GONE);
//       }
//
        getOrderModel().setImgs(getAddOrderActivity().getUpload_img_result_ids());




        getOnNextClick().next(getOrderModel());
        showLoading(false);
    }


    @OnClick(R.id.cancel)
    void cancel_order(){
        getAddOrderActivity().backClick();
    }






    void getprice(final String last_action , final RadioButton current_rd){
        if (getOrderModel().getCar_Type_ID()  == 0 || getOrderModel().getShippingSize()  == 0 ){
            currency_txt.setText("--");
            return;
        }

        showLoading(true);
        OrderModel orderModel =   getOrderModel() ;
        Call<PriceResponse> call = Injector.Api().getPrice(
               orderModel.getFrom_City() ,
                orderModel.getTo_City() ,
                orderModel.getShippingSize(),
                orderModel.getCar_Type_ID()
        );
        call.enqueue(new CallbackWithRetry<PriceResponse>(5, 3000, call, new onRequestFailure() {
            @Override
            public void onFailure() {
                showNoConn(new NoConn() {
                    @Override
                    public void onRetry() {
                        getprice(last_action , current_rd);

                    }
                });
                showLoading(false);
            }
        }) {
            @Override
            public void onResponse(Call<PriceResponse> call, Response<PriceResponse> response) {
                if (response.body().responseItem !=null){
                    if (response.body().responseItem.isSuccessful()){
                        try {
                            String price  = response.body().data.get(0).Price ;
                            price_txt.setText(price);
                            currency_txt.setText(MadarApplication.getUser().getCurrency_Code());
                            showLoading(false);
                        }catch (Exception e){
                            price_txt.setText("--");
                            currency_txt.setText("--");
                            current_rd.setChecked(false);
                            showLoading(false);
                            showLongToast(containerHolder , last_action+getString(R.string.unavailable_now));
                        }
                    }else {
                        price_txt.setText("--");
                        currency_txt.setText("--");
                        current_rd.setChecked(false);
                        showLoading(false);
                        showLongToast(containerHolder , getString(R.string.unavailable_now));
                    }
                }showLoading(false);
            }
        });
    }


    public  interface  PriceFailAction{
        void onfail();
    }


}
