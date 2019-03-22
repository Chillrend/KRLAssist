package com.a4sc11production.krlassist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.a4sc11production.krlassist.onboardingSlide.slide_0;
import com.a4sc11production.krlassist.onboardingSlide.slide_1;
import com.a4sc11production.krlassist.onboardingSlide.slide_2;
import com.airbnb.lottie.LottieAnimationView;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

import java.net.URI;
import java.util.ArrayList;

public class walkthrough extends AppIntro2 implements slide_1.OnFragmentInteractionListener,
        slide_0.OnFragmentInteractionListener, slide_2.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);


        addSlide(slide_0.newInstance("from", "walkthrough"));

        addSlide(slide_1.newInstance("from", "walkthrough"));

        addSlide(slide_2.newInstance("from", "walkthrough"));

        setColorTransitionsEnabled(true);

        getPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch(i){
                    case 0:

                        break;

                    case 1:

                        break;

                    case 2:
                        LottieAnimationView lv = (LottieAnimationView) findViewById(R.id.walkthrough_animation);

                        lv.playAnimation();

                        SharedPreferences sharedPreferences = getSharedPreferences("isFirstStart", MODE_PRIVATE);

                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putBoolean("isFirstStart", false);
                        editor.commit();

                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);


    }
    @Override
    public void onDonePressed() {
        Intent i = new Intent(walkthrough.this, HomeActivity.class);
        startActivity(i);
        // Do something when users tap on Done button.
        finish();
    }

    @Override
    public void onFragmentInteraction(Uri uri){

    }
}
