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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        new Handler().postDelayed(() -> FirebaseAuth.getInstance().addAuthStateListener(firebaseAuth -> {
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();

            prefs = getApplicationContext().getSharedPreferences("MovieDBPrefs", 0);

            if (!prefs.contains("intro")) {
                Intent intentToIntroActivity = new Intent(this, CustomAppIntro.class);
                startActivity(intentToIntroActivity);
                finish();
            }

            else if (currentUser == null) {
                Intent intentToLoginActivity = new Intent(this, LoginActivity.class);
                startActivity(intentToLoginActivity);
                finish();
            }

            else {
                Intent intentToMainActivity = new Intent(this, MainActivity.class);
                startActivity(intentToMainActivity);
                isLogged = true;
                finish();
            }
        }),1500);
    }
}