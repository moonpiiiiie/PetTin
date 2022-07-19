package edu.neu.madcourse.pettin;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class PlayDateActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;

    Button button_addPlaydate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_date);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button_addPlaydate = findViewById(R.id.button_addPlaydate);
        button_addPlaydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddPlayDateActivity.class));
            }
        });

        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_playdate);
        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_chat:
                    startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_playdate:
                    return true;
                case R.id.nav_post:
                    startActivity(new Intent(getApplicationContext(), PostActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_profile:
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
    }

}
