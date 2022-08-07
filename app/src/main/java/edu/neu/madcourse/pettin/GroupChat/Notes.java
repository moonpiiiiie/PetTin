//package edu.neu.madcourse.pettin.GroupChat;
//
//package edu.neu.madcourse.pettin;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.lifecycle.Lifecycle;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.viewpager.widget.ViewPager;
//import androidx.viewpager2.adapter.FragmentStateAdapter;
//import androidx.viewpager2.widget.ViewPager2;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.android.material.tabs.TabLayout;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import java.util.ArrayList;
//
//import edu.neu.madcourse.pettin.GroupChat.Adapter.ViewPagerAdapter;
//import edu.neu.madcourse.pettin.PlayDateActivity;
//import edu.neu.madcourse.pettin.PostActivity;
//import edu.neu.madcourse.pettin.ProfileActivity;
//import edu.neu.madcourse.pettin.R;
//
//public class ChatActivity extends AppCompatActivity {
//
//    private static final String TAG = "ChatActivity";
//
//    BottomNavigationView bottomNav;
//    private RecyclerView recyclerView;
//
//    private FirebaseFirestore dbInstance = FirebaseFirestore.getInstance();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat);
//
//        createNavBar();
//
//        // tab layout and view pager
//        TabLayout tabLayout = findViewById(R.id.chat_user_tab);
//        Log.v(TAG, "tabLayout created");
//        ViewPager2 viewPager = findViewById(R.id.view_pager);
//        ViewPagerAdapter viewPageAdapter = new ViewPagerAdapter(this);
//        viewPager.setAdapter(viewPageAdapter);
//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
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
//        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                tabLayout.getTabAt(position).select();
//            }
//        });
//
//    }
//
//    public void createNavBar() {
//        bottomNav = findViewById(R.id.bottom_nav);
//        bottomNav.setSelectedItemId(R.id.nav_chat);
//        bottomNav.setOnItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.nav_chat:
//                    return true;
//                case R.id.nav_playdate:
//                    startActivity(new Intent(getApplicationContext(), PlayDateActivity.class));
//                    overridePendingTransition(0, 0);
//                    return true;
//                case R.id.nav_post:
//                    startActivity(new Intent(getApplicationContext(), PostActivity.class));
//                    overridePendingTransition(0, 0);
//                    return true;
//                case R.id.nav_profile:
//                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
//                    overridePendingTransition(0, 0);
//                    return true;
//            }
//            return false;
//        });
//    }
//
//
//
//}









///////////////

//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import edu.neu.madcourse.pettin.R;
//
//private Context context;
//private ArrayList<User> listOfUsers;
//private static final String TAG = "UserAdapter";
//
///**
// * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
// * com.firebase.ui.firestore.FirestoreRecyclerOptions} for configuration options.
// *
// * @param options
// */
//public UserAdapter(@NonNull FirestoreRecyclerOptions<User> options) {
//        super(options);
//        }
//
//@Override
//protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull User model) {
//        holder.username.setText(model.getUsername());
//        Log.v(TAG + " onBindViewHolder", holder.username.getText().toString());
//        }
//
//@NonNull
//@Override
//public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//        .inflate(R.layout.user_card_chat, parent, false);
//        return new UserViewHolder(view);
//        }


//    public UserAdapter(Context context, ArrayList<User> listOfUsers) {
//        this.context = context;
//        this.listOfUsers = listOfUsers;
//    }
//
//    @NonNull
//    @Override
//    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new UserViewHolder(LayoutInflater
//                                    .from(context)
//                                    .inflate(R.layout.user_card_chat, null));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
//        User user = listOfUsers.get(position);
//        Log.v(TAG, String.valueOf(position));
//        holder.username.setText(user.getUsername());
//        Log.v(TAG + "onBindViewHolder", holder.username.getText().toString());
//
//        if (user.getUsername().equals("default")) {
//            holder.image.setImageResource(R.mipmap.ic_launcher_round);
//        } else {
//              // TODO : need to determine what to display - image of dog or image of user
////            Glide.with(context).load(user.getImage()).into(holder.image);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return listOfUsers.size();
//    }
//
//
//
//public static class UserViewHolder extends RecyclerView.ViewHolder {
//
//    public TextView username;
//    public ImageView image;
//
//    public UserViewHolder(@NonNull View itemView) {
//        super(itemView);
//
//        this.username = itemView.findViewById(R.id.chat_username);
//        Log.v(TAG + " UserViewHolder", username.getText().toString());
//        this.image = itemView.findViewById(R.id.user_image);
//    }
//}
