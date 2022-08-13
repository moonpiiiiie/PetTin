package edu.neu.madcourse.pettin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        getSupportActionBar().hide();

        firebaseAuth = FirebaseAuth.getInstance();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Check to see if the user is currently signed in.
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser == null) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, PlayDateActivity.class));
                }
                finish();
            }
        }, 2500);
    }
}