package com.example.ahmed.madardemo;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


import com.madar.madardemo.Activity.NavDrawerActivity;
import com.madar.madardemo.R;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by Ahmed on 8/23/2017.
 */

@RunWith(AndroidJUnit4.class)
public class DummyIntrumentationTest {

    @Rule
    public ActivityTestRule<NavDrawerActivity> navDrawerActivityActivityTestRule =
            new ActivityTestRule<>(NavDrawerActivity.class);

    @Test
    public void navDrawerTest(){
        Espresso
                .onView(ViewMatchers.withId(R.id.fragment_toolbar_notification_icon))
                .check(matches(notNullValue()))
                .perform(ViewActions.click());

        FragmentManager fragmentManager =
                ((AppCompatActivity) navDrawerActivityActivityTestRule.getActivity()).getSupportFragmentManager();

        int in = fragmentManager.getBackStackEntryCount();

        FragmentManager.BackStackEntry backStackEntry =
                fragmentManager.getBackStackEntryAt(0);

        String name = backStackEntry.getName();

        Assert.assertEquals(name, "Fragment");
    }

}
