package edu.neu.madcourse.pettin.GroupChat.Messages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;

import edu.neu.madcourse.pettin.ChatActivity;
import edu.neu.madcourse.pettin.Classes.Message;
import edu.neu.madcourse.pettin.Classes.User;
import edu.neu.madcourse.pettin.R;

public class MessageActivity extends AppCompatActivity {

    private TextView username;
    private ImageView imageView;

    // get the current user
    private FirebaseUser currentUser;

    // get db instance
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    // get the users collection
    private CollectionReference usersCollectionReference = db.collection("users");
    private DocumentReference documentReference;
    private CollectionReference chatsCollectionReference = db.collection("chats");


    private Intent intent;

    private static final String TAG = "MessageActivity ";
    private User messagedUser;

    private BottomNavigationView bottomNavigationView;

    // setting the view for the current user's functionality
    private RecyclerView recyclerView;
    private EditText userTextToSend;
    private ImageButton sendButton;
    private ImageView backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        // get rid of the action bar at the top
        getSupportActionBar().hide();

        username = findViewById(R.id.toolbar_username);
        imageView = findViewById(R.id.toolbar_image);

        recyclerView = findViewById(R.id.messages);
        userTextToSend = findViewById(R.id.text_to_send);
        sendButton = findViewById(R.id.send_button_image);
        backButton = findViewById(R.id.toolbar_back_button);

        // back button click action
        backButtonAction();

        intent = getIntent();

        // getting the previous id from the prev action
        String userId = intent.getStringExtra("userId");
        if (userId != null) {
            Log.v(TAG + "userId ", userId);
        }
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.v(TAG + "currentUser ", currentUser.toString());

        documentReference = usersCollectionReference.document(userId);

        getMessagedUser();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = userTextToSend.getText().toString();
                if (message != null) {
                    sendMessageFunctionality(currentUser.getUid(), userId, message);
                } else {
                    return;
                }
                userTextToSend.setText("");
            }
        });

    }

    private void backButtonAction() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessageActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
    }



    private void sendMessageFunctionality(String sender, String receiver, String message) {
        String id = "";
        Message messageToSend = new Message(sender, receiver, message);
        Log.v(TAG, "Generated Id " + id);
        chatsCollectionReference
                .add(messageToSend)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.v(TAG, "generated id " + documentReference.getId());
                        String id = documentReference.getId();
                        chatsCollectionReference.document(id)
                                .update("id", id)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.v(TAG, id);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.v(TAG, e.toString());
                                    }
                                });
                    }
                });

    }

    /**
     * Method gets the user that was clicked on and sets the activity.
     */
    private void getMessagedUser() {
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(MessageActivity.this, "Error found", Toast.LENGTH_SHORT).show();
                }
                if (value != null && value.exists()) {
                    User user = value.toObject(User.class);
                    username.setText(user.getUsername());
                }

                // TODO : Replace default image to image of dog; use the Glide library
            }
        });
    }

    private void setSupportActionBar(Toolbar toolbar) {


    }



}