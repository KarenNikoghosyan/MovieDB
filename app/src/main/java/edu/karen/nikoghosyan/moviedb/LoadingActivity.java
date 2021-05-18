package edu.karen.nikoghosyan.moviedb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoadingActivity extends AppCompatActivity {
    public static boolean isLogged = false;
    private SharedPreferences prefs;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        prefs = getApplicationContext().getSharedPreferences("MovieDBPrefs", 0);

        FirebaseAuth.getInstance().addAuthStateListener(firebaseAuth -> {
            currentUser = firebaseAuth.getCurrentUser();
        });

        //Checks if the user opening the app for the first time,
        //the user will see this screen only once:
        new Handler().postDelayed(() -> {
            if (!prefs.contains("intro")) {
                Intent intentToIntroActivity = new Intent(this, CustomAppIntro.class);
                startActivity(intentToIntroActivity);
                finish();
            }

            //Checks if the user is connected, if not it will send him to the Login Activity:
            else if (currentUser == null) {
                Intent intentToLoginActivity = new Intent(this, LoginActivity.class);
                startActivity(intentToLoginActivity);
                finish();
            }

            //If the user is connected it will send the user to the Main Activity:
            else {
                Intent intentToMainActivity = new Intent(this, MainActivity.class);
                startActivity(intentToMainActivity);
                isLogged = true;
                finish();
            }
        }, 3000);
    }
}