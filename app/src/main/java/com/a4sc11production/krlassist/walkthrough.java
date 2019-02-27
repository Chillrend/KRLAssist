package com.a4sc11production.krlassist;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

public class walkthrough extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SliderPage sliderPage = new SliderPage();
        sliderPage.setTitle("Hello World!");
        sliderPage.setDescription("Prostrate yourself!");
        sliderPage.setImageDrawable(R.drawable.ic_android_24dp);
        sliderPage.setBgColor(Color.parseColor("#"));
        addSlide(AppIntroFragment.newInstance(sliderPage));
    }
}