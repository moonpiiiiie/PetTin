package edu.neu.madcourse.pettin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.ProgressBar;

import android.widget.Button;

import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.neu.madcourse.pettin.Classes.User;

public class ProfileActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;
    Button button_SignOut;
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

    // match received recyclerview
    RecyclerView matchedDogRecyclerview;
    ArrayList<Dogs> matchDogs;
    ArrayList<String> matchDogIds;
    MatchReceivedAdapter matchReceivedAdapter;

    ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        pb = findViewById(R.id.progressBar_profile);
        pb.setVisibility(View.VISIBLE);
        // firebase

        auth = FirebaseAuth.getInstance();
        curUser = auth.getCurrentUser();

        textView_userName = findViewById(R.id.textView_userName);
        if (curUser != null) {
            String userId = curUser.getUid();
            db = FirebaseFirestore.getInstance();
            DocumentReference userRef = db.collection("users").document(userId);
            userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override

                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        User user = task.getResult().toObject(User.class);
                        dogIds = user.getDogs();
                        userName = user.getUsername();
                        email = user.getEmail();
                        textView_userName.setText("Username: " + userName);
                    }

                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User user = documentSnapshot.toObject(User.class);
                    userName = user.getUsername();
                    email = user.getEmail();
                    textView_userName.setText(userName);

                }
            });
//            userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                @Override
//                public void onSuccess(DocumentSnapshot documentSnapshot) {
//                    User user = documentSnapshot.toObject(User.class);
//                    dogIds = user.getDogs();
//                    userName = user.getUsername();
//                    email = user.getEmail();
//                    textView_userName.setText("Username: " + userName);
//                }
//            });
        }


        // my dog recyclerview
        myDogs = new ArrayList<>();
        fetchMyDog();
        myDogRecyclerView = findViewById(R.id.recyclerView_mydog);
        myDogRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myDogAdapter = new MyDogAdapter(ProfileActivity.this, myDogs);
        myDogRecyclerView.setAdapter(myDogAdapter);





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
        System.out.println("on create my dog " + myDogs);
        // match received recyclerview
        matchDogs = new ArrayList<>();

        matchedDogRecyclerview = findViewById(R.id.recyclerView_matchReceived);
        matchedDogRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        matchReceivedAdapter = new MatchReceivedAdapter(ProfileActivity.this, matchDogs);
        matchedDogRecyclerview.setAdapter(matchReceivedAdapter);
        fetchMatch();
        pb.setVisibility(View.INVISIBLE);
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

    private void fetchMatch() {

        CollectionReference playRef = db.collection("dogs");
        playRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document: task.getResult()) {
                        Dogs dog = document.toObject(Dogs.class);
                        // dogId -> owned dog

                        if (dogIds.contains(dog.getSentMatch())) {
                            matchDogs.add(dog);
                        }
                        matchReceivedAdapter.notifyDataSetChanged();
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