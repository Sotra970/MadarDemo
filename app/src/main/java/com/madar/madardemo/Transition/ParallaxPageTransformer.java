package com.madar.madardemo.Transition;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.madar.madardemo.R;


/**
 * Created by sotra on 4/8/2017.
 */
public class ParallaxPageTransformer implements ViewPager.OnPageChangeListener  {

        TabLayout tabStrip ;


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


        int pageWidth = (int) (1f - positionOffset);
        View tab = tabStrip.getTabAt(position).getCustomView();
        if (positionOffset < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            tab.setAlpha(1);



        } else if (positionOffset <= 1) { // [-1,1]

            Log.e("ParallaxPageTransformer","position " + position);
            tab.findViewById(R.id.tab_title).setTranslationX(-position * (pageWidth / 2)); //Half the normal speed
        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            tab.setAlpha(1);
        }

    }


    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}