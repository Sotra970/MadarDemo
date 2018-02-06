package com.madar.madardemo.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.madar.madardemo.Fragment.BaseOrderListFragment;

import java.util.ArrayList;

/**
 * Created by Ahmed on 8/28/2017.
 */

public class OrderFragmentsAdapter extends FragmentPagerAdapter {

    private ArrayList<BaseOrderListFragment> fragments;
    private Context context;

    public OrderFragmentsAdapter(FragmentManager fm, ArrayList<BaseOrderListFragment> fragments, Context context) {
        super(fm);
        this.fragments = fragments;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(fragments != null && fragments.size() > position){
            return fragments.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if(fragments != null){
            return fragments.size();
        }
        return 0;
    }

    public void updateFragments(ArrayList<BaseOrderListFragment> fragments){
        this.fragments = fragments;
        notifyDataSetChanged();
    }
}
