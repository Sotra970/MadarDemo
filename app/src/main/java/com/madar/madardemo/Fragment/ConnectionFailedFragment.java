package com.madar.madardemo.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madar.madardemo.Fragment.Abstract.BaseFragment;
import com.madar.madardemo.Interface.NoConn;
import com.madar.madardemo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ahmed on 8/17/2017.
 */

public class ConnectionFailedFragment extends BaseFragment
    {

        public static ConnectionFailedFragment getInstance() {
            return new ConnectionFailedFragment();
        }
        View resLayout ;
        NoConn noConn ;

        public void setNoConn(NoConn noConn) {
            this.noConn = noConn;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         if (resLayout == null){
             resLayout= inflater.inflate(R.layout.fragment_connection_failed, container, false);
             ButterKnife.bind(this, resLayout) ;
         }
         if (savedInstanceState !=null){
             noConn = (NoConn) savedInstanceState.getSerializable("noConn");
         }
            return resLayout;
        }

        @OnClick(R.id.retry)
        void retry(){
            try {

                noConn.onRetry();
                getActivity().onBackPressed();
            }catch (Exception e){}
        }
    }
