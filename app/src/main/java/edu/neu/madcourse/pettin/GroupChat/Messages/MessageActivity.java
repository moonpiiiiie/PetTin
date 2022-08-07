package edu.neu.madcourse.pettin.GroupChat.Messages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.neu.madcourse.pettin.R;

public class MessageActivity extends AppCompatActivity {

    private TextView username;
    private ImageView imageView;

    private FirebaseUser currentUser;
    private FirebaseFirestore dbInstance;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

//        imageView = findViewById(R.id.match_image);

    }



}