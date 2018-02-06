package com.madar.madardemo.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madar.madardemo.Fragment.Abstract.TitledFragment;
import com.madar.madardemo.R;

/**
 * Created by Ahmed on 8/17/2017.
 */

public class AboutUsFragment extends TitledFragment {

    public static AboutUsFragment getInstance() {
        return new AboutUsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about_us, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle(R.string.fragment_title_about_us);
    }
}
