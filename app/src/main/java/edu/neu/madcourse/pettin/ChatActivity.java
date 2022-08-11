package edu.neu.madcourse.pettin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ChatActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        // creates the nav bar
        createNavBar();

        // creates the tab layout
        setTabLayout();

        // sets up the users at the top - to be matched users
        retrieveUsers();

        fabCreateGroup = findViewById(R.id.create_group);


        fabCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createGroupIntent = new Intent(ChatActivity.this, CreateGroupChatActivity.class);
                startActivity(createGroupIntent);
            }
        });


    }

//    /**
//     * Method sets up the tab layout.
//     */
    private void setTabLayout() {
        tabLayout = findViewById(R.id.chats_tab_layout);
        viewPager2 = findViewById(R.id.view_pager);
        viewPageAdapter = new ViewPageAdapter(this);
        viewPager2.setAdapter(viewPageAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
    }

    /**


    }

    /**
     * Method sets up the tab layout.
//     */
//    private void setTabLayout() {
//        tabLayout = findViewById(R.id.chats_tab_layout);
//        viewPager2 = findViewById(R.id.view_pager);
//        viewPageAdapter = new ViewPageAdapter(this);
//        viewPager2.setAdapter(viewPageAdapter);
//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager2.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//
//        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                tabLayout.getTabAt(position).select();
//            }
//        });
//    }

    /**

     * Method retrieves users from the FireStore database.
     * TODO: need to update so that it gets matched users only
     */
    private void retrieveUsers() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        recyclerView = findViewById(R.id.matched_users);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        db = FirebaseFirestore.getInstance();
        listOfUsers = new ArrayList<User>();
        userAdapter = new UserAdapter(ChatActivity.this, this.listOfUsers, this);
        new ItemTouchHelper(userCallback).attachToRecyclerView(recyclerView);

        // load data from Firestore into array
        DocumentReference currentUserDocRef = db.collection("users").document(currentUser.getUid());
        currentUserDocRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()) {
                    User user = value.toObject(User.class);
                    for (User matchedUser : user.getMatchedUsers()) {
                        listOfUsers.add(matchedUser);
                    }
                    userAdapter.notifyDataSetChanged();
                }
            }
        });

        recyclerView.setAdapter(userAdapter);
//        db.collection("users").orderBy("username", Query.Direction.ASCENDING)
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        if (error != null) {
//                            // there is an error
//                            Log.v("Firestore error", error.getMessage());
//                            return;
//                        }
//                        // get all the data from the firestore
//                        // TODO - want to retrieve matched users - will need to get the field matchedUsers which is an array
//                        for (DocumentChange document : value.getDocumentChanges()) {
//                            if (document.getType() == DocumentChange.Type.ADDED) {
//                                // fetch data
//                                User user = document.getDocument().toObject(User.class);
//                                if (user != null && !user.getUserId().equals(currentUser.getUid())) {
//                                    listOfUsers.add(user);
//                                }
//                            }
//                            userAdapter.notifyDataSetChanged();
//                        }
//                    }
//                });

    }

    /**
     * Method creates the navigation bar to get to the other activities.
     */
    public void createNavBar() {

        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_chat);
        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_chat:
                    return true;
                case R.id.nav_playdate:
                    startActivity(new Intent(getApplicationContext(), PlayDateActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_post:
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
    }
}