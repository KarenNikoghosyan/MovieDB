package edu.karen.nikoghosyan.moviedb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.karen.nikoghosyan.moviedb.ui.home.HomeMovieFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, HomeMovieFragment.newInstance())
                    .commitNow();
        }
    }
}