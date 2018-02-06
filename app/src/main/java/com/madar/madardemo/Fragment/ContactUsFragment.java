package com.madar.madardemo.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madar.madardemo.Fragment.Abstract.TitledFragment;
import com.madar.madardemo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ahmed on 8/18/2017.
 */

public class ContactUsFragment extends TitledFragment {


    public static ContactUsFragment getInstance() {
        return new ContactUsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact_us, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle(R.string.fragment_title_contact_us);
    }

    @OnClick(R.id.fragment_contact_us_go_to_facebook)
    void goFace(){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Madar_Ex-180429922514630"));
        startActivity(i);
    }

    @OnClick(R.id.fragment_contact_us_go_to_twitter)
    void goTwitter(){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/Madar_Ex"));
        startActivity(i);
    }

    @OnClick(R.id.fragment_contact_us_go_to_instagram)
    void goInsta(){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/Madar_Ex"));
        startActivity(i);
    }

    @OnClick(R.id.fragment_contact_us_make_call)
    void call(){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "920020804"));
        startActivity(intent);
    }


}
