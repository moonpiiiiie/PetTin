package edu.neu.madcourse.pettin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.neu.madcourse.pettin.Classes.Dogs;

public class PlayDateActivity extends AppCompatActivity implements DogPlayDateAdapter.OnDogListener{
    BottomNavigationView bottomNav;
    Button button_addPlaydate;

    RecyclerView recyclerView;
    DogPlayDateAdapter dogPlayDateAdapter;
    ArrayList<Dogs> dogs;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    // TODO search
    // TODO advanced filter dialog
    // TODO swipe left/right to dislike and request
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_date);


        button_addPlaydate = findViewById(R.id.button_addPlaydate);
        button_addPlaydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddPlayDateActivity.class));
            }
        });

        recyclerView = findViewById(R.id.recyclerView_playdate);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        dogs = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dogPlayDateAdapter = new DogPlayDateAdapter(PlayDateActivity.this, dogs, this);
        recyclerView.setAdapter(dogPlayDateAdapter);

        fetchPlayDate();


        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.swipeLayout);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dogs.clear();
                fetchPlayDate();
                pullToRefresh.setRefreshing(false);
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
                case R.id.nav_restaurant:
                    startActivity(new Intent(getApplicationContext(), RestaurantsNearByMeActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
    }

    private void fetchPlayDate() {
        CollectionReference playRef = db.collection("dogs");
        playRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document: task.getResult()) {
                        dogs.add(document.toObject(Dogs.class));
                        dogPlayDateAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.d("fetch playdate", "failed", task.getException());
                }
            }
        });
    }

    @Override
    public void onDogClick(int position) {
        Intent intent = new Intent(this, SingleDogActivity.class);
        String name = dogs.get(position).getName();
        String dogId = dogs.get(position).getDog_id();
        String gender = dogs.get(position).getGender();
        int age = dogs.get(position).getAge();
        int energyLevel = dogs.get(position).getEnergyLevel();
        Double weight = dogs.get(position).getWeight();
        String spayed = dogs.get(position).getSpayed();
        String breed = dogs.get(position).getBreed();
        String city = dogs.get(position).getLocation();
        intent.putExtra("name", name);
        intent.putExtra("dogId", dogId);
        intent.putExtra("age", age);
        intent.putExtra("gender", gender);
        intent.putExtra("energyLevel", energyLevel);
        intent.putExtra("weight", weight);
        intent.putExtra("spayed", spayed);
        intent.putExtra("breed", breed);
        intent.putExtra("city", city);
        startActivity(intent);
    }
}
