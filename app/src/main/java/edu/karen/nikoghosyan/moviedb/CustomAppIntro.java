package edu.karen.nikoghosyan.moviedb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.appintro.AppIntro2;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.AppIntroPageTransformerType;

import maes.tech.intentanim.CustomIntent;

public class CustomAppIntro extends AppIntro2 {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getApplicationContext().getSharedPreferences("MovieDBPrefs", 0);
        editor = prefs.edit();

        addSlide(AppIntroFragment.newInstance(
                "Welcome to MovieDB!", "Here's a short intro on how to use the app.", R.drawable.icon_tv, Color.rgb(255, 127, 80)
        ));
        addSlide(AppIntroFragment.newInstance(
                "Navigation", "To navigate between the pages use the bottom navigation bar or by swiping the page.", R.drawable.bottom_nav, Color.rgb(100, 149, 237)
        ));
        addSlide(AppIntroFragment.newInstance(
                "Profile Image", "To change the image profile, tap the profile icon and choose the image from the gallery.", R.drawable.change_profile_image, Color.rgb(25, 42, 81)
        ));
        addSlide(AppIntroFragment.newInstance(
                "Bookmarks", "To add/remove movies from bookmarks, tap the bookmark icon as shown in the image.", R.drawable.add_to_bookmarks, Color.rgb(235, 81, 96)
        ));
        addSlide(AppIntroFragment.newInstance(
                "Searching", "To search for any movie, just type the movie's name.", R.drawable.movie_search, Color.rgb(170, 161, 200)
        ));
        addSlide(AppIntroFragment.newInstance(
                "Intro's End", "I hope this short intro will help you to get started using the app.", R.drawable.icon_movie_intro, Color.rgb(239, 164, 139)
        ));

        setImmersiveMode();
        setTransformer(AppIntroPageTransformerType.Depth.INSTANCE);
        setSkipButtonEnabled(false);

    }

    @Override
    protected void onSkipPressed(@Nullable Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
    }

    @Override
    protected void onDonePressed(@Nullable Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        CustomIntent.customType(this, "left-to-right");
        Toast.makeText(this, "Enjoy!", Toast.LENGTH_LONG).show();

        editor.putBoolean("intro", true);
        editor.apply();

        finish();
    }


}