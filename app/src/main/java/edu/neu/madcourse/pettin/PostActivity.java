package edu.neu.madcourse.pettin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import edu.neu.madcourse.pettin.Classes.Post;
import edu.neu.madcourse.pettin.Classes.User;

public class PostActivity extends AppCompatActivity {
//    BottomNavigationView bottomNav;
    private RecyclerView recyclerView;
    private PostAdapter.RecyclerViewClickListener listener;
    private ImageView addButton;
    private ImageView homeButton;
    private ImageView followButton;

    private DatabaseReference database;
    private PostAdapter postAdapter;
    private ArrayList<User> userList;
    private ArrayList<Post> postList;
    private ArrayList<Post> newPostList;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        recyclerView = findViewById(R.id.postList);
        database = FirebaseDatabase.getInstance().getReference("Users");

        addButton = findViewById(R.id.add);
        homeButton = findViewById(R.id.home);
        followButton = findViewById(R.id.follower);
        System.out.println(getIntent().hasExtra("username"));

        if (getIntent().hasExtra("username")) {

            username = getIntent().getStringExtra("username");
        }

        System.out.println("Username in onCreate");
        System.out.println(username);
        PostActivity.this.setTitle(username);

        postList = new ArrayList<>();
        userList = new ArrayList<>();
        newPostList = new ArrayList<>();

        setPostItemOnClickListener();
        postAdapter = new PostAdapter(this, postList, newPostList, listener);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(postAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                postList.clear();
                newPostList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    if(user.getPosts() != null) {
                        userList.add(user);
                    }
                }
                getPosts(userList, postList, newPostList);
                postAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // add post activity
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostActivity.this, AddPostActivity.class);
                intent.putExtra("username", username);
                System.out.println("Username in add post");
                System.out.println(username);
                startActivity(intent);
            }
        });

        // reload the page
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent refresh = new Intent(PostActivity.this, PostActivity.class);
                refresh.putExtra("username", username);
                startActivity(refresh);
                PostActivity.this.finish();
            }
        });

        // to follower page
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toFollowPage = new Intent(PostActivity.this, ShowFollowActivity.class);
                toFollowPage.putExtra("username", username);
                startActivity(toFollowPage);
            }
        });


//        bottomNav = findViewById(R.id.bottom_nav);
//        bottomNav.setSelectedItemId(R.id.nav_post);
//        bottomNav.setOnItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.nav_chat:
//                    startActivity(new Intent(getApplicationContext(), ChatActivity.class));
//                    overridePendingTransition(0, 0);
//                    return true;
//                case R.id.nav_playdate:
//                    startActivity(new Intent(getApplicationContext(), PlayDateActivity.class));
//                    overridePendingTransition(0, 0);
//                    return true;
//                case R.id.nav_post:
//                    return true;
//                case R.id.nav_profile:
//                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
//                    overridePendingTransition(0, 0);
//                    return true;
//            }
//            return false;
//        });


    }

    /**
     * The click listener for each item in the recycler list
     */
    private void setPostItemOnClickListener() {
        listener = new PostAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Toast.makeText(PostActivity.this, "Title is:" + postList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                Intent toPostDetailPage = new Intent(getApplicationContext(), ShowPostDetailActivity.class);
                toPostDetailPage.putExtra("content", postList.get(position).getContent());
                toPostDetailPage.putExtra("image", postList.get(position).getImage());
                toPostDetailPage.putExtra("likes", postList.get(position).getLikes());
                toPostDetailPage.putExtra("location", postList.get(position).getLocation());
                toPostDetailPage.putExtra("time", postList.get(position).getTime());
                toPostDetailPage.putExtra("title", postList.get(position).getTitle());
                toPostDetailPage.putExtra("bloggerName", postList.get(position).getUsername());
                toPostDetailPage.putExtra("username", username);
                startActivity(toPostDetailPage);
            }
        };
    }


    /**
     * Get all the posts from the user and update the post list
     * @param userList
     * @param postList
     */
    private void getPosts(ArrayList<User> userList, ArrayList<Post> postList, ArrayList<Post> newPostList) {
        for(User user: userList) {
            String username = user.getUsername();
            for(Map.Entry<String, Object> entry: user.getPosts().entrySet()) {
                Map<String, String> map = (Map<String, String>)entry.getValue();
                String image = map.get("image");
                String title = map.get("title");
                String content = map.get("content");
                String location = map.get("location");
                String time = map.get("time");
                String likes = map.get("likes");
                postList.add(new Post(image, title, content, location, time, likes, username));
                newPostList.add(new Post(image, title, content, location, time, likes, username));
            }
        }
        postList.sort((post1, post2) -> Integer.valueOf(post2.getLikes()) - Integer.valueOf(post1.getLikes()));
        newPostList.sort((post1, post2) -> Integer.valueOf(post2.getLikes()) - Integer.valueOf(post1.getLikes()));
    }

}