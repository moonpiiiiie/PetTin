package edu.neu.madcourse.pettin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class PostActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;
    //Declaration of FirebaseAuth class,which helps in Authentication
    private FirebaseAuth mAuth;
    //Declaration of FirebaseFirestore which helps in storing data and url of the images
    private FirebaseFirestore firebaseFirestore;
    //Declaration of current_user_id String
    private String current_user_id;

    //Declaration of Floating Action Button
    private FloatingActionButton addPostBtn;

    //Fragments which are to be used to replace the default fragment in PostActivity
    private PostFragment postFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        //Add button Initialization
        addPostBtn = findViewById(R.id.add_post_btn);
        //Fragments Initialization
        postFragment = new PostFragment();

        //Firstly on OnCreate() we will replace the fragment with homeFragment in MainActivity
        replaceFragment(postFragment);

        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_chat);
        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_post:
                    return true;
                case R.id.nav_playdate:
                    startActivity(new Intent(getApplicationContext(), PlayDateActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_chat:
                    startActivity(new Intent(getApplicationContext(), PostActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_profile:
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });

        //When we click on post button(Floating Action Button) then it will send an Explict Intent to PostActicity
        addPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Starts an Explict Intent
                startActivity(new Intent(PostActivity.this, AddPostActivity.class));
            }
        });
    }

    //This method will occur on start of the activity
    @Override
    protected void onStart() {
        super.onStart();
        //Initialization of FirebaseUser and getting the current user from firebase
        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();

        //If user is not logged in then send him to the zloginActivity
        if (currentuser == null) {
            sendToLogin();

        } else {
            //Retrieve the current user from firebase by id
            current_user_id = mAuth.getCurrentUser().getUid();
            //We are retriving the documents that in the Users collection and added onCompleteListener
            firebaseFirestore.collection("Users").document(current_user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    //If the task is successful then ...
                    if (task.isSuccessful()) {
                        if (!task.getResult().exists()) {
//                            //Start thr Explict Intent to setUpActivity
//                            Intent setupIntent = new Intent(MainActivity.this, SetupActivity.class);
//                            startActivity(setupIntent);

                        }
                    }
                    //Show the errors in the form of toasts
                    else{
                        String error = task.getException().getMessage();
                        Toast.makeText(PostActivity.this, "Error"+ error, Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }
    }

    private void sendToLogin() {

        //Declaration of explict Intent from PostActivity to LoginActivity
        Intent intent = new Intent(PostActivity.this, LoginActivity.class);
        //Starting of the Intent
        startActivity(intent);
        finish();
    }

    //This methos is used to replace fragment by another fragment
    private void replaceFragment(Fragment fragment){

        //Initiaization and declaration of FragmentTransaction class and begin the transaction of fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //Replace the fragment by given fragment which was passed as arguement
        fragmentTransaction.replace(R.id.main_content_fragment,fragment);
        //We must commit the transaction so that it can be worked properly
        fragmentTransaction.commit();

    }



}