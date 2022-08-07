//package edu.neu.madcourse.pettin.GroupChat.Fragments;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.firebase.ui.firestore.FirestoreRecyclerOptions;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.Query;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import java.sql.Array;
//import java.util.ArrayList;
//
//import edu.neu.madcourse.pettin.Classes.User;
//import edu.neu.madcourse.pettin.GroupChat.UserAdapter;
//import edu.neu.madcourse.pettin.R;
//
//public class UserFragment extends Fragment {
//
//    private static final String TAG = "UserFragment";
//
//    private RecyclerView recyclerView;
//    private UserAdapter userAdapter;
//    private ArrayList<User> listOfUsers = new ArrayList<>();
//
//    // get instance of the db
////    private FirebaseFirestore db = FirebaseFirestore.getInstance();
//    private Query query = FirebaseFirestore.getInstance().collection("users");
//
//    public UserFragment() {
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.chat_users_fragment, container, false);
//        Log.v(TAG, "view inflated");
//        recyclerView = view.findViewById(R.id.chat_users_rv);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
////        userAdapter = new UserAdapter(this.getContext(), this.listOfUsers);
////        listOfUsers = new ArrayList<>();
////        retrievedUsersFromDB();
////        recyclerView.setAdapter(userAdapter);
//        Log.v(TAG, recyclerView.toString());
//        Log.v(TAG, query.get().toString());
////        firebaseRecyclerBuild();
//        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
//                .setQuery(query, User.class)
//                .build();
//        userAdapter = new UserAdapter(options);
//        recyclerView.setAdapter(userAdapter);
//        return view;
//    }
//
////    private void retrievedUsersFromDB() {
////        // get the current user - read the user
////        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
////        Log.v(TAG, firebaseUser.toString());
////        CollectionReference usersCollections = db.collection("users");
////        Log.v(TAG, "after usersCollection");
////        usersCollections.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
////            @Override
////            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
////                // use for each loop to get data from firebase
////                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
////                    User user = snapshot.toObject(User.class);
////                    Log.v(TAG, user.getUsername());
////                    listOfUsers.add(user);
////                }
////            }
////        });
////
////    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        if (userAdapter != null) {
//            userAdapter.startListening();
//        }
//    }
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (userAdapter != null) {
//            userAdapter.stopListening();
//        }
//    }
//    private void firebaseRecyclerBuild() {
//        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
//                .setQuery(query, User.class)
//                .build();
//        userAdapter = new UserAdapter(options);
//        recyclerView.setAdapter(userAdapter);
//        Log.v(TAG, userAdapter.toString());
//        userAdapter.startListening();
//    }
//
//}
