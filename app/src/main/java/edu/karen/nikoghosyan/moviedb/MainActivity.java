package edu.karen.nikoghosyan.moviedb;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import edu.karen.nikoghosyan.moviedb.models.movies.movie.Movie;
import edu.karen.nikoghosyan.moviedb.ui.main.ViewPagerAdapter;
import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {
    private AnimatedBottomBar animatedBottomBar;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), 1);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

        animatedBottomBar = findViewById(R.id.animatedBottomBar);
        animatedBottomBar.setupWithViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        animatedBottomBar.selectTabAt(viewPager.getCurrentItem(), false);
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            animatedBottomBar.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.VISIBLE);

        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("Quit")
                    .setCancelable(false)
                    .setIcon(R.drawable.ic_baseline_power_settings_new_24)
                    .setMessage("You're about to quit the app, are you sure?")
                    .setNegativeButton("Cancel", (dialog, which) -> {

                    }).setPositiveButton("OK", (dialog, which) -> {
                        finishAffinity();
                        finish();
                    });

            AlertDialog dialog = builder.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(this.getColor(R.color.dark_purple));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(this.getColor(R.color.dark_purple));

        }
    }
}