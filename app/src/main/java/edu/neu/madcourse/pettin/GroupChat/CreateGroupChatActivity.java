package edu.neu.madcourse.pettin.GroupChat;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.stream.Collectors;

import edu.neu.madcourse.pettin.ChatActivity;
import edu.neu.madcourse.pettin.Classes.User;
import edu.neu.madcourse.pettin.GroupChat.UserMatches.MatchedUsersAdapter;
import edu.neu.madcourse.pettin.R;



public class CreateGroupChatActivity extends AppCompatActivity {

    private static final String TAG = "CreateGroupChatActivity ";
    private EditText groupName;
    private ImageView backButton;

    // get the current year
    private FirebaseUser currentUser;

    // android widgets
    private RecyclerView matchedUsersRV;
    private MatchedUsersAdapter matchedUsersAdapter;

    private ArrayList<User> listOfUsers;



    // get db instance
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference groupCollectionReference = db.collection("groups");

    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group_chat);

        backButton();

        // recycler view to display matched users to add to group
        matchedUsersRV = findViewById(R.id.create_group_matched_users);

        // need to build the group chat with title, id, participants
        retrieveUsers();


    }

    private void backButton() {
        backButton = findViewById(R.id.create_group_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateGroupChatActivity.this, ChatActivity.class));
            }
        });
    }

    private void retrieveUsers() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        matchedUsersRV = findViewById(R.id.create_group_matched_users);
        matchedUsersRV.setHasFixedSize(true);
        matchedUsersRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        db = FirebaseFirestore.getInstance();
        listOfUsers = new ArrayList<User>();
        matchedUsersAdapter = new MatchedUsersAdapter(CreateGroupChatActivity.this, this.listOfUsers);


        DocumentReference currentUserDocRef = db.collection("users").document(currentUser.getUid());
        currentUserDocRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()) {
                    User user = value.toObject(User.class);
                    for (User matchedUser : user.getMatchedUsers()) {
                        listOfUsers.add(matchedUser);
                    }
                    matchedUsersAdapter.notifyDataSetChanged();
                }
            }
        });

        matchedUsersRV.setAdapter(matchedUsersAdapter);
        ArrayList<User> groupMembers = (ArrayList<User>) matchedUsersAdapter
                                            .getGroupMembers()
                                            .stream()
                                            .distinct()
                                            .collect(Collectors.toList());
        Log.v(TAG + "size of groupMembers", String.valueOf(groupMembers.size()));

    }


}