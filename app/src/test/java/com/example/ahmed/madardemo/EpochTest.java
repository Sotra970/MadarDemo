package com.example.ahmed.madardemo;


import com.madar.madardemo.Util.Utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Ahmed on 8/23/2017.
 */

public class EpochTest {

    @Test
    public void dummyTestEpoch() {
        long now = Utils.getSystemTime();
        long afterNow = Utils.getSystemTime();
        Assert.assertNotEquals("Conversion from celsius to fahrenheit failed", now, afterNow);
    }

}
