package edu.neu.madcourse.pettin.GroupChat.Fragments.GroupChatsFragment;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;

import edu.neu.madcourse.pettin.ChatActivity;
import edu.neu.madcourse.pettin.Classes.GroupChat;
import edu.neu.madcourse.pettin.Classes.GroupMessage;
import edu.neu.madcourse.pettin.GroupChat.Messages.MessageActivity;
import edu.neu.madcourse.pettin.R;

public class GroupMessageActivity extends AppCompatActivity {

    private static final String TAG = "GroupMessageActivity ";

    // the current user
    private FirebaseUser currentUser;

    // get db instance
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    // get the group from the database
    private CollectionReference groupsCollectionReference = db.collection("groups");
    // get the group document with the intent - groupId
    private DocumentReference documentReference;

    // group name display on activity
    private TextView groupName;

    // buttons
    private ImageButton sendButton;
    private ImageView backButton;

    // text to send in the group chat
    private EditText userTextToSend;

    // to use to display messages in the chat
    private ArrayList<GroupMessage> listOfMessagesInGroup = new ArrayList<>();
    private RecyclerView messagesRecyclerView;
    private GroupMessageAdapter messageAdapter;

    // getting data from launching the intent
    private Intent intent;
    private String groupId;

    // GroupChat object
    private GroupChat groupChat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_message);
        getSupportActionBar().hide();

        groupName = findViewById(R.id.toolbar_group_name_gc);
        backButton = findViewById(R.id.toolbar_back_button_gc);
        sendButton = findViewById(R.id.send_button_image_gc);
        userTextToSend = findViewById(R.id.text_to_send_gc);
        // backButton functionality
        backButtonAction();

        // set up recycler view
        messagesRecyclerView = findViewById(R.id.messages_gc);
        messagesRecyclerView.setHasFixedSize(true);

        // get intent
        intent = getIntent();
        groupId = intent.getStringExtra("id");
        Log.v(TAG, " group id " + groupId);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        getMessages();

        // getting the group object
        documentReference = groupsCollectionReference.document(groupId);

        sendButton();


    }

    private void backButtonAction() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupMessageActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getMessages() {
        documentReference = groupsCollectionReference.document(groupId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()) {
                    groupChat = value.toObject(GroupChat.class);
                    for (GroupMessage message : groupChat.getListOfMessages()) {
                        listOfMessagesInGroup.add(message);
                    }
                    Log.v(TAG + " after getGroup ",  " group " + groupChat.getGroup());
                    groupName.setText(groupChat.getGroup());

                }
            }
        });
    }

    private void sendButton() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = userTextToSend.getText().toString();
                if (message != null) {
                    sendMessageFunctionality(currentUser.getUid(), message);
                } else {
                    return;
                }
                userTextToSend.setText("");
            }
        });
    }

    private void sendMessageFunctionality(String sender, String message) {
        GroupMessage messageToSend = new GroupMessage(sender, message);
        documentReference = groupsCollectionReference.document(groupId);

//        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot snapshot) {
//                groupChat = snapshot.toObject(GroupChat.class);
//                HashMap<String, Object> data = new HashMap<>();
//                data.put("listOfMessages", groupChat.getListOfMessages().add(messageToSend));
//                documentReference.update(data);
//            }
//        });

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                HashMap<String, Object> data = new HashMap<>();
                data.put("listOfMessages", FieldValue.arrayUnion(messageToSend));
                documentReference.update(data);
            }
        });

//        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                if (value != null && value.exists()) {
//                    groupChat = value.toObject(GroupChat.class);
//                    int count = 1;
//                    if (count == 1) {
//                        groupChat.getListOfMessages().add(messageToSend);
//                        HashMap<String, Object> data = new HashMap<>();
//                        data.put("listOfMessages", groupChat.getListOfMessages());
//                        documentReference.update(data);
//                        count++;
//                    } else {
//                        return;
//                    }
//
//                }
//
//            }
//        });


    }

}