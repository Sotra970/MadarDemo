package com.example.ahmed.madardemo;


import com.madar.madardemo.Util.TimeUtils;
import com.madar.madardemo.Util.Utils;

import org.junit.Test;

/**
 * Created by Ahmed on 8/23/2017.
 */

public class MonthTest {

    @Test
    public void dummyMonthTest(){
        long now = Utils.getSystemTime();
        String month = TimeUtils.getMonth(now);
    }
}
