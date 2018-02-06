package com.madar.madardemo.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;

import com.madar.madardemo.Fragment.Abstract.BaseFragment;
import com.madar.madardemo.R;
import com.madar.madardemo.Util.AnimationUtils;

/**
 * Created by Ahmed on 8/31/2017.
 */

public class MainFragment extends BaseFragment implements View.OnClickListener {

    private View signInButton;
    private View signUpButton;
    private View backgroundContents;
    private View buttonContainer;

    private static boolean FLAG_OPENED_BEFORE = false;

    private final static long FIRST_ANIM_DURATION = 1000L;
    private final static long SECOND_ANIM_DURATION = 650L;

    public static MainFragment getInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        initView(v);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void animateViews(){
        if(!FLAG_OPENED_BEFORE){
            AnimationUtils.slideUpView(
                    buttonContainer,
                    getContext(),
                    new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {}

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            buttonContainer.setVisibility(View.VISIBLE);

                            Animation.AnimationListener animationListener =
                                    new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {}

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            signInButton.setVisibility(View.VISIBLE);
                                            signUpButton.setVisibility(View.VISIBLE);
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    };

                            AnimationUtils.slideUpView(signInButton, getContext(), null, SECOND_ANIM_DURATION);
                            AnimationUtils.slideUpView(signUpButton, getContext(), animationListener, SECOND_ANIM_DURATION);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {}
                    },
                    FIRST_ANIM_DURATION
            );

            AnimationUtils.slideDownView(
                    backgroundContents,
                    getContext(),
                    new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {}

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            backgroundContents.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {}
                    },
                    FIRST_ANIM_DURATION
            );

            FLAG_OPENED_BEFORE = true;
        }
        //AnimationUtils.slideUpView(signUpButton, getContext());
        //AnimationUtils.slideDownView(backgroundContents, getContext());
    }

    private void initView(View fragmentView){
        if(fragmentView != null){
            /*background = fragmentView.findViewById(R.id.fragment_main_background);

            Glide.with(this)
                    .load(R.drawable.bg)
                    .into(background);*/

            signInButton = fragmentView.findViewById(R.id.fragment_main_sign_in_button);
            if(signInButton != null){
                signInButton.setOnClickListener(this);
            }

            signUpButton = fragmentView.findViewById(R.id.fragment_main_sign_up_button);
            if(signUpButton != null){
                signUpButton.setOnClickListener(this);
            }

            backgroundContents = fragmentView.findViewById(R.id.fragment_main_background_contents);

            buttonContainer = fragmentView.findViewById(R.id.fragment_main_button_container);

            if(FLAG_OPENED_BEFORE)
            {
                backgroundContents.setVisibility(View.VISIBLE);
                buttonContainer.setVisibility(View.VISIBLE);
                signInButton.setVisibility(View.VISIBLE);
                signUpButton.setVisibility(View.VISIBLE);
            }

            else
            {
                backgroundContents.setVisibility(View.INVISIBLE);
                buttonContainer.setVisibility(View.INVISIBLE);
                signInButton.setVisibility(View.INVISIBLE);
                signUpButton.setVisibility(View.INVISIBLE);

                fragmentView.getViewTreeObserver().addOnGlobalLayoutListener(
                        new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                runOnUiThread(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                animateViews();
                                            }
                                        }
                                );
                            }
                        }
                );
            }
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fragment_main_sign_in_button:
                Fragment f1 = LoginFragment.getInstance();
                showFragment(f1,true);
                break;

            case R.id.fragment_main_sign_up_button:
                Fragment f2 = SignUpFragment.getInstance();
                showFragment(f2,true);
                break;
        }
    }
}
