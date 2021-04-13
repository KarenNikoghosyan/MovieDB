package edu.karen.nikoghosyan.moviedb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import edu.karen.nikoghosyan.moviedb.ui.favorite.FavoriteMovieFragment;
import edu.karen.nikoghosyan.moviedb.ui.home.HomeMovieFragment;
import edu.karen.nikoghosyan.moviedb.ui.search.SearchMovieFragment;
import maes.tech.intentanim.CustomIntent;
import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {
    private AnimatedBottomBar animatedBottomBar;
    private Fragment fragment = null;

    private static int currentPosition = 0;
    private static int newPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            currentPosition = 0;
            loadFragment();
        }

        animatedBottomBar = findViewById(R.id.animatedBottomBar);
        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NotNull AnimatedBottomBar.Tab newTab) {

                int id = newTab.getId();

                if (id == R.id.nav_home) {
                    fragment = new HomeMovieFragment();
                    newPosition = 1;
                } else if (id == R.id.nav_search) {
                    fragment = new SearchMovieFragment();
                    newPosition = 2;
                } else if (id == R.id.nav_favorites) {
                    fragment = new FavoriteMovieFragment();
                    newPosition = 3;
                }

                loadFragment();
                currentPosition = newPosition;
            }

            @Override
            public void onTabReselected(int i, @NotNull AnimatedBottomBar.Tab tab) {}
        });
    }

    public void loadFragment() {

        if (currentPosition == 0) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, HomeMovieFragment.newInstance())
                    .commitNow();
            currentPosition = 1;
        }

        if (fragment != null) {
            if (currentPosition > newPosition) {

                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.fragmentContainer, fragment)
                        .commitNow();
            }
            if (currentPosition < newPosition) {

                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(R.id.fragmentContainer, fragment)
                        .commitNow();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
            animatedBottomBar.setVisibility(View.VISIBLE);
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
        else {
            currentPosition = 0;
            finish();
            System.exit(0);
        }
    }

    public Fragment getCurrentFragment() {
        if (currentPosition == 1) {
            return new HomeMovieFragment();
        }
        else if (currentPosition == 2) {
            return new SearchMovieFragment();
        }
        return new FavoriteMovieFragment();
    }
}