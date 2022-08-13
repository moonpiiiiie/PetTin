package edu.neu.madcourse.pettin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import edu.neu.madcourse.pettin.Classes.Post;

public class ShowPostDetailActivity extends AppCompatActivity {
    Post curPost;
    private String image;

    private String post_id;

    // firebase
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;

    private ImageView postDetailImg;
    private TextView postDetailUsername;
    private TextView postDetailTitle;
    private TextView postDetailContent;
//    private TextView postDetailLikes;
    private TextView postDetailLocation;
    private TextView postDetailTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post_detail);
        post_id = getIntent().getStringExtra("postId");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        postDetailImg = findViewById(R.id.postDetailImg);
        postDetailUsername = findViewById(R.id.postDetailUsername);
        postDetailTitle = findViewById(R.id.postDetailTitle);
        postDetailContent = findViewById(R.id.postDetailContent);
//        postDetailLikes = findViewById(R.id.postDetailLikes);
        postDetailLocation = findViewById(R.id.postDetailLocation);
        postDetailTime = findViewById(R.id.postDetailTime);



        DocumentReference postRef = db.collection("posts").document(post_id);
        postRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                curPost = documentSnapshot.toObject(Post.class);

                postDetailUsername.setText(curPost.getUsername());
                postDetailTitle.setText(curPost.getTitle());
                postDetailContent.setText(curPost.getContent());
//                postDetailLikes.setText(curPost.getLikes());
                postDetailLocation.setText(curPost.getLocation());
                postDetailTime.setText(curPost.getTime());
                image = curPost.getImage();
                Glide.with(ShowPostDetailActivity.this).load(image).into(postDetailImg);
            }
        });


    }
}