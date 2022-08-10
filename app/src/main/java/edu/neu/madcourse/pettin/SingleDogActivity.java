package edu.neu.madcourse.pettin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.neu.madcourse.pettin.Classes.Dogs;
import edu.neu.madcourse.pettin.Classes.User;

public class SingleDogActivity extends AppCompatActivity {
    final String TAG = "single dog activity";
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
    List<String> myDog;
    List<Task<DocumentSnapshot>> task;
    Map<String, String> myDogToMatch;
    DogToMatchAdapter dogToMatchAdapter;
    RecyclerView myDogToMatchRV;
    int dogNum;
    DocumentReference curDogRef;

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
        curDogRef = db.collection("dogs").document(dog_id);
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
        match = findViewById(R.id.button_match);


        // get dog number
        dogNum = 0;
        myDog = new ArrayList<>();
        myDogToMatch = new HashMap<>();
        dogNum = ownedDogs();
        System.out.println("dog " + dogNum);

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
                // part I
                if (dogNum == 0) {
                    Toast.makeText(SingleDogActivity.this, "Please add your dog to match another dog", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (dogNum >= 1) {
                    getMyDogList();
                    showMatchDialog();
                }
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
                // part II
            }
        });

    }

    void updateDislikeDogList(String dogId) {

    }

    void getMyDogList() {
        CollectionReference dogRef = db.collection("dogs");
        for (String dogId: myDog) {
            dogRef.document(dogId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    String dogName= document.getString("name");
                    if (!myDogToMatch.containsKey(dogName)) {
                        myDogToMatch.put(dogName, dogId);
                    }

                }
            });
        }
    }

    // get how many dogs the current user owned
    int ownedDogs() {
        DocumentReference userRef = db.collection("users").document(curUser.getUid());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                myDog = user.getDogs();
                dogNum = myDog.size();
            }
        });

//        List<Task<DocumentSnapshot>> tasks = new ArrayList<>();
//        System.out.println("my dog" + myDog);
//        for (String path : myDog) {
//            tasks.add(db.document(path).get());
//        }
//        System.out.println("tasks " + tasks);
//        Task<List<DocumentSnapshot>> finalTask = Tasks.whenAllSuccess(tasks);
//        finalTask.addOnSuccessListener(new OnSuccessListener<List<DocumentSnapshot>>() {
//            @Override
//            public void onSuccess(List<DocumentSnapshot> documentSnapshots) {
//                System.out.println("onSuccess?");
//                for (DocumentSnapshot snapshot : documentSnapshots) {
//                    System.out.println(snapshot.toObject(Dogs.class).getName());
//                    myDogToMatch.add(snapshot.toObject(Dogs.class).getName());
//                }
//
//            }
//        });
//        Task<List<DocumentSnapshot>> task = Tasks.whenAllComplete(myDog);

//        System.out.println("my dog " + myDog);
//        for (String dogId: myDog) {
//           dogRef.document(dogId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                @Override
//                public void onSuccess(DocumentSnapshot documentSnapshot) {
//                    System.out.println("get name " + documentSnapshot.toObject(Dogs.class).getName());
//                    myDogToMatch.add(documentSnapshot.toObject(Dogs.class).getName());
//                }
//            });
//        }

        return dogNum;
    }

    void showMatchDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(SingleDogActivity.this);
        builderSingle.setTitle("Select One Pet:-");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SingleDogActivity.this, android.R.layout.select_dialog_singlechoice);
        for (String dogName: myDogToMatch.keySet()) {
            arrayAdapter.add(dogName);
        }

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
                String myDogId = myDogToMatch.get(strName);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(SingleDogActivity.this);
                builderInner.setMessage(strName);
                builderInner.setTitle("Your Selected Pet is");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        matchDog(myDogId);
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();


    }

    void matchDog(String myDogId) {
        if (curUser != null) {
            String userId = curUser.getUid();
            DocumentReference userRef = db.collection("users").document(curUser.getUid());
            CollectionReference otherUserRef = db.collection("users");
            userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User user = documentSnapshot.toObject(User.class);
                    List<String> dislikeDogs = user.getDislikeDog();

                    if (dislikeDogs.contains(curDog.getDog_id())) {
                        userRef.update("dislikeDog", FieldValue.arrayRemove(curDog.getDog_id()));
                    }
//
                    DocumentReference myDogRef = db.collection("dogs").document(myDogId);
                    // TODO check if sent and received
                    myDogRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            List<String> myDogReceivedMatch = (List<String>)task.getResult().get("receivedMatch");
                            if (myDogReceivedMatch.contains(curDog.getDog_id())) {
                                otherUserRef.document(curDog.getUserID()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        User otherUser = task.getResult().toObject(User.class);
                                        userRef.update("matchedUsers", FieldValue.arrayUnion(otherUser));

                                    }
                                });
                            }
                        }
                    });
                    otherUserRef.document(curDog.getUserID()).update("matchedUsers", FieldValue.arrayUnion(user));
                    myDogRef.update("sentMatch", FieldValue.arrayUnion(curDog.getDog_id()));
//
                    curDogRef.update("receivedMatch", FieldValue.arrayUnion(myDogId));
//

                }
            });
            finish();

        } else {
            Toast.makeText(SingleDogActivity.this, "Please log in to match a playdate", Toast.LENGTH_SHORT).show();
        }
    }
//    class fireStoreMatch implements Runnable {
//
//        @Override
//        public void run() {
//
//        }
//    }
}
