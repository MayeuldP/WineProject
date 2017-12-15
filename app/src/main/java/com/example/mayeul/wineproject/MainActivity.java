package com.example.mayeul.wineproject;

import android.app.ActionBar;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.mayeul.wineproject.Home.HomeView;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends  AppIntro {

    private String description = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //setContentView(R.layout.activity_main);
        description = getResources().getString(R.string.tutorial_description);
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.tutorial_title), getResources().getString(R.string.tutorial_description), R.drawable.wine, getResources().getColor(R.color.tutorialBackground)));
        addSlide(AppIntroFragment.newInstance(null, getResources().getString(R.string.tutorial_explanation), R.drawable.swipe, getResources().getColor(R.color.tutorialBackground)));

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        Intent intent = new Intent(this, HomeView.class);
        startActivity(intent);
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
