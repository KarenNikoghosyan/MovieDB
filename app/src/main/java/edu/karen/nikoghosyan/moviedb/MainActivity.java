package edu.karen.nikoghosyan.moviedb;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import edu.karen.nikoghosyan.moviedb.models.api.bookmarks.BookmarksAPIManager;
import edu.karen.nikoghosyan.moviedb.ui.genre.GenreMovieFragment;
import edu.karen.nikoghosyan.moviedb.ui.home.HomeMovieFragment;
import edu.karen.nikoghosyan.moviedb.ui.main.ViewPagerAdapter;
import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {
    public static AnimatedBottomBar animatedBottomBar;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), 1);

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

            Fragment f = getSupportFragmentManager().findFragmentByTag("GenreTag");
            if (f instanceof GenreMovieFragment) {
                getSupportFragmentManager().popBackStack();
                animatedBottomBar.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.VISIBLE);
            }

            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentByTag("GenreTag");
            if (HomeMovieFragment.isGenre && fragment != null) {
                fm
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(R.id.fragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit();
                HomeMovieFragment.isGenre = false;
            }
            else {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                animatedBottomBar.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.VISIBLE);
            }

        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("Quit")
                    .setCancelable(false)
                    .setIcon(R.drawable.ic_baseline_power_settings_new_24)
                    .setMessage("You're about to quit the app, are you sure?")
                    .setNegativeButton("Cancel", (dialog, which) -> {

                    }).setPositiveButton("OK", (dialog, which) -> {
                        BookmarksAPIManager.movieList.clear();
                        finishAffinity();
                        finish();
                    });

            AlertDialog dialog = builder.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(this.getColor(R.color.dark_purple));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(this.getColor(R.color.dark_purple));

        }
    }
}