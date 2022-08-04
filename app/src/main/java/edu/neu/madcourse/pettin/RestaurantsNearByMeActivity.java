package edu.neu.madcourse.pettin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RestaurantsNearByMeActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_near_by_me);

        Button button_test;
        button_test = findViewById(R.id.button_test);
        button_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String latitude = "138+Congress+St,+Portland,+ME+04101";
                String longitude = "-70.24822142112734";
                String uri = "geo:43.66545701336908, -70.24827775767166?q= Portland Observatory";
                //                String uri = "https://www.google.com/maps/place/" + latitude + "," + longitude;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });


        //Navigation Bar Start
        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_restaurant);
        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_chat:
                    startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_playdate:
                    startActivity(new Intent(getApplicationContext(), PlayDateActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_post:
                    startActivity(new Intent(getApplicationContext(), PostActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_profile:
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_restaurant:
                    return true;

            }
            return false;
        });//Navigation Bar End
    }
}