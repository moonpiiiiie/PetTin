package edu.neu.madcourse.pettin.GroupChat.Fragments.ChatsFragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Array;
import java.util.ArrayList;

import edu.neu.madcourse.pettin.Classes.CurrentChats;
import edu.neu.madcourse.pettin.Classes.User;
import edu.neu.madcourse.pettin.GroupChat.UserMatches.CurrentChatsAdapter;
import edu.neu.madcourse.pettin.GroupChat.UserMatches.UserAdapter;
import edu.neu.madcourse.pettin.R;

// to display the current messages
// COMPLETED
public class ChatsFragment extends Fragment {

    private static final String TAG = "ChatFragment ";


    private CurrentChatsAdapter currentChatsAdapter;
    private ArrayList<User> listOfUsers;

    private FirebaseUser currentUser;
    // get db instance
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    // get the users collection
    private CollectionReference usersCollectionReference = db.collection("users");
    private CollectionReference chatsCollectionReference = db.collection("chats");
    private CollectionReference currentChatsCollection = db.collection("current_chats");
    private DocumentReference documentReference;

    private ArrayList<CurrentChats> currentChats;

    private RecyclerView currentChatRv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        currentChatRv = view.findViewById(R.id.single_chats_rv);
        currentChatRv.setHasFixedSize(true);
        currentChatRv.setLayoutManager(new LinearLayoutManager(getContext()));

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        listOfUsers = new ArrayList<>();
        currentChats = new ArrayList<>();

        currentChatsCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                currentChats.clear();
                for (QueryDocumentSnapshot snapshot : value) {
                    CurrentChats currentChat = snapshot.toObject(CurrentChats.class);
                    // add only the Current Chat object if the current user and the sender user match
                    if (currentChat.getSenderId().equals(currentUser.getUid())) {
                        currentChats.add(currentChat);
                    }
                }
                retrieveUsers();
            }
        });

        return view;
    }

    // method gets the users that have chatted with
    private void retrieveUsers() {
        usersCollectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                listOfUsers.clear();
                // iterate through all users
                for (QueryDocumentSnapshot snapshot : value) {
                    User user = snapshot.toObject(User.class);
                    // iterate through all the current chats
                    for (CurrentChats currentChat : currentChats) {
                        if(user.getUserId().equals(currentChat.getReceiverId()) && !listOfUsers.contains(user)) {
                            listOfUsers.add(user);
                        }
                    }
                    currentChatsAdapter = new CurrentChatsAdapter(getContext(), listOfUsers, user.getUserId());
                    currentChatRv.setAdapter(currentChatsAdapter);
                }

                for (User user : listOfUsers) {
                    Log.v(TAG, user.getUsername() + " " + user.getUserId());
                }
                Log.v(TAG, "size " + listOfUsers.size());
            }
        });
    }

}
