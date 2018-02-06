package com.madar.madardemo.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madar.madardemo.Adapter.PagerViewAdapter;
import com.madar.madardemo.Fragment.Abstract.TitledFragment;
import com.madar.madardemo.R;
import com.rd.PageIndicatorView;

/**
 * Created by Ahmed on 9/15/2017.
 */

public class StartFragment extends TitledFragment {

    private ViewPager viewPager;
    private PageIndicatorView pageIndicatorView;

    public static StartFragment getInstance() {
        return new StartFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_delivery_destination, container, false);

        viewPager = (ViewPager)
                v.findViewById(R.id.fragment_delivery_dest_view_pager);

        pageIndicatorView = (PageIndicatorView)
                v.findViewById(R.id.fragment_delivery_dest_indicator);

        initViewPager();

        return v;
    }

    private void initViewPager(){
        PagerViewAdapter pagerViewAdapter = new PagerViewAdapter(getContext());
        viewPager.setAdapter(pagerViewAdapter);
        pageIndicatorView.setViewPager(viewPager);
    }


}
