package edu.karen.nikoghosyan.moviedb;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoadingActivity extends AppCompatActivity {
    public static boolean isLogged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        new Handler().postDelayed(() -> {

            FirebaseAuth.getInstance().addAuthStateListener(firebaseAuth -> {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                if (currentUser == null) {
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
            });
        },1500);
    }
}