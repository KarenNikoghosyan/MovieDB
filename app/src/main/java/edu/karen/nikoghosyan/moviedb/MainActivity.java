package edu.karen.nikoghosyan.moviedb;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import edu.karen.nikoghosyan.moviedb.ui.favorite.FavoriteMovieFragment;
import edu.karen.nikoghosyan.moviedb.ui.home.HomeMovieFragment;
import edu.karen.nikoghosyan.moviedb.ui.search.SearchMovieFragment;
import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {
    private AnimatedBottomBar animatedBottomBar;
    //ViewPager2 pager2;
    //SectionsPagerAdapter adapter;

    private Fragment fragment = null;

    public static int currentPosition = 0;
    public static int newPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            currentPosition = 0;
            loadFragment();

            animatedBottomBar = findViewById(R.id.animatedBottomBar);
            //pager2 = findViewById(R.id.viewPager2);

            //FragmentManager fm = getSupportFragmentManager();
            //adapter = new SectionsPagerAdapter(fm, getLifecycle());
            //pager2.setAdapter(adapter);

            //animatedBottomBar.setupWithViewPager2(pager2);
        }

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
            animatedBottomBar.setVisibility(View.VISIBLE);
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
        else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("Quit")
                    .setCancelable(false)
                    .setIcon(R.drawable.ic_baseline_power_settings_new_24)
                    .setMessage("You're about to quit the app, are you sure?")
                    .setNegativeButton("Cancel", (dialog, which) -> {

                    }).setPositiveButton("OK", (dialog, which) -> {
                        currentPosition = 0;
                        finishAffinity();
                        finish();
                    });

            AlertDialog dialog = builder.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(this.getColor(R.color.dark_purple));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(this.getColor(R.color.dark_purple));

        }
    }
}