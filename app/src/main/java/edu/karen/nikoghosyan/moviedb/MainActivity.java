package edu.karen.nikoghosyan.moviedb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import edu.karen.nikoghosyan.moviedb.ui.favorite.FavoriteMovieFragment;
import edu.karen.nikoghosyan.moviedb.ui.home.HomeMovieFragment;
import edu.karen.nikoghosyan.moviedb.ui.search.SearchMovieFragment;
import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {
    AnimatedBottomBar animatedBottomBar;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    int currentPosition = 0;
    int newPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            loadFragment();
        }

        animatedBottomBar = findViewById(R.id.bottom_bar);
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
                } else if (id == R.id.nav_favorite) {
                    fragment = new FavoriteMovieFragment();
                    newPosition = 3;
                }

                loadFragment();
                currentPosition = newPosition;
            }

            @Override
            public void onTabReselected(int i, @NotNull AnimatedBottomBar.Tab tab) {

            }
        });
    }

    private void loadFragment() {
        if (currentPosition == 0) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, HomeMovieFragment.newInstance())
                    .commitNow();
        }

        if (fragment != null) {
            if (currentPosition > newPosition) {

                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.fragmentContainer, fragment)
                        .commit();
            }
            if (currentPosition < newPosition) {

                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(R.id.fragmentContainer, fragment)
                        .commit();
            }
        }
    }
}