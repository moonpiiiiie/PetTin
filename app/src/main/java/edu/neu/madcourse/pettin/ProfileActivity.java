package edu.neu.madcourse.pettin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.neu.madcourse.pettin.Classes.Dogs;
import edu.neu.madcourse.pettin.Classes.User;

public class ProfileActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;
    ExtendedFloatingActionButton button_SignOut, button_ChangePW;
    FirebaseFirestore db;
    FirebaseAuth auth;
    FirebaseUser curUser;
    String userName;
    String email;
    TextView textView_userName;

    // my dog recyclerview
    RecyclerView myDogRecyclerView;
    ArrayList<Dogs> myDogs;
    MyDogAdapter myDogAdapter;
    ArrayList<String> dogIds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // firebase
        auth = FirebaseAuth.getInstance();
        curUser = auth.getCurrentUser();
        dogIds = new ArrayList<>();
        textView_userName = findViewById(R.id.textView_userName);
        if (curUser != null) {
            String userId = curUser.getUid();
            db = FirebaseFirestore.getInstance();
            DocumentReference userRef = db.collection("users").document(userId);
            userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User user = documentSnapshot.toObject(User.class);
                    dogIds = user.getDogs();
                    userName = user.getUsername();
                    email = user.getEmail();
                    textView_userName.setText(userName);
                }
            });
        }

        // my dog recyclerview
        myDogs = new ArrayList<>();
        myDogRecyclerView = findViewById(R.id.recyclerView_mydog);
        myDogRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myDogAdapter = new MyDogAdapter(ProfileActivity.this, myDogs);
        myDogRecyclerView.setAdapter(myDogAdapter);

        fetchMyDog();

        // buttons
        button_SignOut = findViewById(R.id.button_Signout);
        button_SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                signOutUser();
            }
        });

        button_ChangePW = findViewById(R.id.btn_changePW);
        button_ChangePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // bottom nav
        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_profile);
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
                    return true;
            }
            return false;
        });
    }

    private void fetchMyDog() {
        CollectionReference playRef = db.collection("dogs");
        playRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document: task.getResult()) {
                        Dogs dog = document.toObject(Dogs.class);
                        if (dogIds.contains(dog.getDog_id())) {
                            myDogs.add(dog);
                        }
                        myDogAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.d("fetch my dog", "failed", task.getException());
                }
            }
        });

    }

    private void signOutUser() {
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        finish();
    }
}
