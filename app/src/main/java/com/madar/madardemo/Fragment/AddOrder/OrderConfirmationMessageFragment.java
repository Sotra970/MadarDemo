package com.madar.madardemo.Fragment.AddOrder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.madar.madardemo.Fragment.Abstract.AddOrderBaseFragment;
import com.madar.madardemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ahmed on 8/15/2017.
 */

public class OrderConfirmationMessageFragment extends AddOrderBaseFragment {

    @BindView(R.id.progressView)
    View progrssView ;
    @BindView(R.id.container)
    View containerHolder ;


    @BindView(R.id.order_num)
    TextView order_num ;




    public static OrderConfirmationMessageFragment getInstance() {
        return new OrderConfirmationMessageFragment();
    }

    View v ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (v == null) {
            v = inflater.inflate(R.layout.fragment_order_confirmation_message, container, false);
            ButterKnife.bind(this, v);
            initLoading(this.progrssView, this.containerHolder);
        }


        return v;
    }

    @Override
    public void onPageSelected(int pos) {
        order_num.setText(getAddOrderActivity().orderItem.getID());
    }

    @OnClick(R.id.next)
    void next(){
       getActivity().onBackPressed();
    }

}
