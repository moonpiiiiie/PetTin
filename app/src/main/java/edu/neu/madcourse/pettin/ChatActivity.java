package edu.neu.madcourse.pettin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.neu.madcourse.pettin.Classes.User;
import edu.neu.madcourse.pettin.GroupChat.UserMatches.UserAdapter;


public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";

    private BottomNavigationView bottomNav;
    private RecyclerView recyclerView;
    private ArrayList<User> listOfUsers;
    private UserAdapter userAdapter;

    private FirebaseFirestore dbInstance;

    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        createNavBar();


//        LoadingDialog loadingDialog = new LoadingDialog(ChatActivity.this);
//        loadingDialog.loadingDialog();

        recyclerView = findViewById(R.id.matched_users);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        dbInstance = FirebaseFirestore.getInstance();
        listOfUsers = new ArrayList<User>();
        userAdapter = new UserAdapter(ChatActivity.this, this.listOfUsers);

        recyclerView.setAdapter(userAdapter);
        retrieveUsers();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

//        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
//        userId = auth.getCurrentUser().getUid();

    }

    private void retrieveUsers() {
        dbInstance.collection("users").orderBy("username", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            // there is an error
                            Log.v("Firestore error", error.getMessage());
                            return;
                        }
                        // get all the data from the firestore
                        // TODO - want to retrieve matched users - will need to get the field matchedUsers which is an array
                        for (DocumentChange document : value.getDocumentChanges()) {
                            if (document.getType() == DocumentChange.Type.ADDED) {
                                // fetch data
                                User user = document.getDocument().toObject(User.class);
                                if (!user.getUserId().equals(currentUser.getUid())) {
                                    listOfUsers.add(user);
                                }

//                                listOfUsers.add(document.getDocument().toObject(User.class));
                            }
                            userAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }


    public void createNavBar() {
        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_chat);
        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_chat:
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
            }
            return false;
        });
    }



}