package edu.neu.madcourse.pettin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.neu.madcourse.pettin.Classes.Dogs;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_dog);

        // post section
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
        dogAge.setText(intent.getStringExtra("age"));
        dogGender.setText(intent.getStringExtra("gender"));
        dogBreed.setText(intent.getStringExtra("breed"));
        dogCity.setText(intent.getStringExtra("city"));

        // firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        // retrieve data from firebase for the single post
        DocumentReference postRef = db.collection("dogs").document(dog_id);
        postRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                curDog = documentSnapshot.toObject(Dogs.class);

                // set data to post section
                dogCity.setText("Notes: " + curDog.getLocation());
                dogAge.setText("Age: " + curDog.getAge());
                dogGender.setText("Gender: " + curDog.getGender());
                String imageUrl = curDog.getImg();
                Glide.with(getApplicationContext()).load(imageUrl).apply(new RequestOptions().override(150, 150)).centerCrop().into(dogPhoto);
            }
        });
    }
}