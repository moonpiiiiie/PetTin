package edu.neu.madcourse.pettin.GroupChat;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;

import edu.neu.madcourse.pettin.Classes.User;
import edu.neu.madcourse.pettin.R;

public class UserAdapter extends FirestoreRecyclerAdapter<User, UserAdapter.UserViewHolder> {

    private Context context;
    private ArrayList<User> listOfUsers;
    private static final String TAG = "UserAdapter";

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public UserAdapter(@NonNull FirestoreRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull User model) {
        holder.username.setText(model.getUsername());
        Log.v(TAG + " onBindViewHolder", holder.username.getText().toString());
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_card_chat, parent, false);
        return new UserViewHolder(view);
    }


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

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView username;
        public ImageView image;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            this.username = itemView.findViewById(R.id.chat_username);
            Log.v(TAG + " UserViewHolder", username.getText().toString());
            this.image = itemView.findViewById(R.id.user_image);
        }
    }

}
