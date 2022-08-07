package edu.neu.madcourse.pettin.GroupChat;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.neu.madcourse.pettin.Classes.User;
import edu.neu.madcourse.pettin.R;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    private ArrayList<User> listOfUsers;

    public UserAdapter(Context context, ArrayList<User> listOfUsers) {
        this.context = context;
        this.listOfUsers = listOfUsers;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_card_chat, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = listOfUsers.get(position);
        holder.username.setText(user.getUsername());
    }

    @Override
    public int getItemCount() {
        return listOfUsers.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        TextView username;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
        }
    }
}
