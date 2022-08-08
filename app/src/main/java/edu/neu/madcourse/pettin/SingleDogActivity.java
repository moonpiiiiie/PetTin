package edu.neu.madcourse.pettin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.neu.madcourse.pettin.Classes.Dogs;
import edu.neu.madcourse.pettin.Classes.User;

public class SingleDogActivity extends AppCompatActivity {
    //Post section
    Dogs curDog;
    TextView dogName, dogCity, dogAge, dogGender, dogBreed;
    ImageView dogPhoto;
    String dog_id;

    // firebase
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    FirebaseUser curUser;
    String userId;

    // button for dislike and match
    ExtendedFloatingActionButton dislike, match;

    // match my dog dialog
    List<String> myDogtoMatch;
    DogToMatchAdapter dogToMatchAdapter;
    RecyclerView myDogToMatchRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_dog);

        // info section
        dogName = findViewById(R.id.textView_dogName);
        dogCity = findViewById(R.id.textView_dogCity);
        dogPhoto = findViewById(R.id.imageView_singlePostPhoto);
        dogAge = findViewById(R.id.textView_age);
        dogGender = findViewById(R.id.textView_gender);
        dogBreed = findViewById(R.id.textView_dogBreed);



        // data carried from main activity
        Intent intent = getIntent();
        dogName.setText(intent.getStringExtra("name"));
        dog_id = intent.getStringExtra("dogId");
//        dogAge.setText(String.valueOf(intent.getStringExtra("age")));
        dogGender.setText(intent.getStringExtra("gender"));
        dogBreed.setText(intent.getStringExtra("breed"));
        dogCity.setText(intent.getStringExtra("city"));

        // firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        // retrieve data from firebase for the single post
        DocumentReference curDogRef = db.collection("dogs").document(dog_id);
        curDogRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                curDog = documentSnapshot.toObject(Dogs.class);

                // set data to post section
                dogCity.setText("City: " + curDog.getLocation());
                dogAge.setText("Age: " + curDog.getAge());
                dogGender.setText("Gender: " + curDog.getGender());
                String imageUrl = curDog.getImg();
                Glide.with(getApplicationContext()).load(imageUrl).apply(new RequestOptions().override(150, 150)).centerCrop().into(dogPhoto);
            }
        });


        // user
        curUser = firebaseAuth.getCurrentUser();
        // buttons
        dislike = findViewById(R.id.button_dislike);
        match=findViewById(R.id.button_match);

        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (curUser != null) {
                    String userId = curUser.getUid();
                    DocumentReference userRef = db.collection("users").document(userId);
                    // update data in firestore
                    userRef.update("dislikeDog", FieldValue.arrayUnion(curDog.getDog_id()));
                    finish();

                } else {
                    Toast.makeText(SingleDogActivity.this, "Please log in to dislike", Toast.LENGTH_SHORT).show();
                }

            }
        });

        match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMyDogList();
                showMatchDialog();
//                AlertDialog.Builder builderSingle = new AlertDialog.Builder(SingleDogActivity.this);
//
//                builderSingle.setTitle("Select One Pet:-");
//
//                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SingleDogActivity.this, android.R.layout.select_dialog_singlechoice);
//                arrayAdapter.add("Hardik");
//                arrayAdapter.add("Archit");
//                arrayAdapter.add("Jignesh");
//                arrayAdapter.add("Umang");
//                arrayAdapter.add("Gatti");
//
//                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
//                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String strName = arrayAdapter.getItem(which);
//                        AlertDialog.Builder builderInner = new AlertDialog.Builder(SingleDogActivity.this);
//                        builderInner.setMessage(strName);
//                        builderInner.setTitle("Your Selected Pet is");
//                        builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog,int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                        builderInner.show();
//                    }
//                });
//                builderSingle.show();

//                if (curUser != null) {
//                    String userId = curUser.getUid();
//                    DocumentReference userRef = db.collection("users").document(curUser.getUid());
//                    userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                        @Override
//                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//                            User user = documentSnapshot.toObject(User.class);
//                            List<String> dislikeDogs = user.getDislikeDog();
//
//                            // TODO pop up a dialog to choose user's dog to match
//                            System.out.println("user dog number " + user.getDogs().size());
////                            if (user.getDogs().size()>=2) {
////
////                            }
//                            String dogId = user.getDogs().get(0);
//                            if (dislikeDogs.contains(curDog.getDog_id())) {
//                                userRef.update("dislikeDog", FieldValue.arrayRemove(curDog.getDog_id()));
//                            }
////                            // TODO add to sent match list with current dog
//                            DocumentReference myDogRef = db.collection("dogs").document(dogId);
//                            myDogRef.update("sentMatch", FieldValue.arrayUnion(curDog.getDog_id()));
////                            // TODO add received match with the other dog
//                            curDogRef.update("receivedMatch", FieldValue.arrayUnion(curDog.getDog_id()));
////                            // TODO check if sent and received
//
//                        }
//                    });
//                    finish();
//
//                } else {
//                    Toast.makeText(SingleDogActivity.this, "Please log in to match a playdate", Toast.LENGTH_SHORT).show();
//                }
            }
        });

    }
    void updateDislikeDogList(String dogId) {

    }

    void getMyDogList() {
        myDogtoMatch = new ArrayList<>();
        DocumentReference userRef = db.collection("users").document(curUser.getUid());
        CollectionReference dogRef = db.collection("dogs");
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                for (String dogId: user.getDogs()) {
                    myDogtoMatch.add(dogId);
                }
                System.out.println("my dog to match" + myDogtoMatch);
            }
        });
    }

    void showMatchDialog() {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(SingleDogActivity.this);

            builderSingle.setTitle("Select One Pet:-");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SingleDogActivity.this, android.R.layout.select_dialog_singlechoice);
            arrayAdapter.add("Hardik");
            arrayAdapter.add("Archit");
            arrayAdapter.add("Jignesh");
            arrayAdapter.add("Umang");
            arrayAdapter.add("Gatti");

            builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String strName = arrayAdapter.getItem(which);
                    AlertDialog.Builder builderInner = new AlertDialog.Builder(SingleDogActivity.this);
                    builderInner.setMessage(strName);
                    builderInner.setTitle("Your Selected Pet is");
                    builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,int which) {
                            dialog.dismiss();
                        }
                    });
                    builderInner.show();
                }
            });
            builderSingle.show();


    }
}