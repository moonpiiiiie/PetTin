package edu.neu.madcourse.pettin.GroupChat.UserMatches;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.neu.madcourse.pettin.Classes.User;
import edu.neu.madcourse.pettin.GroupChat.Messages.MessageActivity;
import edu.neu.madcourse.pettin.R;

public class MatchedUsersAdapter extends RecyclerView.Adapter<MatchedUsersAdapter.MatchedUserViewHolder> {

    private static final String TAG = "MatchedUserAdapter ";

    private Context context;
    private ArrayList<User> listOfUsers;
    private ArrayList<User> groupMembers;
    private User user;

    public MatchedUsersAdapter(Context context, ArrayList<User> listOfUsers) {
        this.context = context;
        this.listOfUsers = listOfUsers;
        this.groupMembers = new ArrayList<>();
    }

    @NonNull
    @Override
    public MatchedUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MatchedUserViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_create_group_user_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MatchedUserViewHolder holder, int position) {
        user = listOfUsers.get(position);
        holder.username.setText(user.getUsername());
        holder.userId = user.getUserId();
    }

    @Override
    public int getItemCount() {
        return listOfUsers.size();
    }

    public class MatchedUserViewHolder extends RecyclerView.ViewHolder {

        private TextView username;
        private String userId;

        public MatchedUserViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username_current);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.v(TAG + " user clicked", username.getText().toString());
                    Log.v(TAG + " username onClick: ", userId);
                    // need id - need to pass the id of the user
                    Log.v(TAG, "Starting Message Activity " + userId);
                    groupMembers.add(user);
                    Log.v(TAG, String.valueOf(groupMembers.contains(user)));
                    Log.v(TAG + "groupMembers size", String.valueOf(groupMembers.size()));
                }
            });
        }
    }

    public ArrayList<User> getGroupMembers() {
        return groupMembers;
    }

}
