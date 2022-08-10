package edu.neu.madcourse.pettin.GroupChat;


import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.neu.madcourse.pettin.R;



public class CreateGroupChatActivity extends AppCompatActivity {

    private static final String TAG = "CreateGroupChatActivity "
    private EditText groupName;

    // get the current year
    private FirebaseUser currentUser;

    // get db instance
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference groupCollectionReference = db.collection("groups");

    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group_chat);

        // need to build the group chat with title, id, participants


    }


}