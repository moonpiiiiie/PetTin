package edu.neu.madcourse.pettin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import edu.neu.madcourse.pettin.Classes.Dogs;

public class MatchReceivedAdapter extends RecyclerView.Adapter<MatchReceivedAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private List<Dogs> dogs;
    FirebaseFirestore db;
    FirebaseAuth auth;
    FirebaseUser curUser;
    final String TAG = "My Dog Adapater";


    MatchReceivedAdapter(Context context, List<Dogs> dogs) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dogs = dogs;
    }



    @NonNull
    @Override
    public MatchReceivedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = layoutInflater.inflate(R.layout.match_received_view, parent, false);
        return new MatchReceivedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchReceivedAdapter.ViewHolder viewHolder, int i) {
        if(!dogs.isEmpty()) {
            Dogs dog = dogs.get(i);
            if(dog!=null) {
                viewHolder.name.setText(dog.getName());
                viewHolder.age.setText(String.valueOf(dog.getAge()));
                viewHolder.gender.setText(" " + dog.getGender());
                viewHolder.breed.setText(dog.getBreed());
                viewHolder.position = viewHolder.getAdapterPosition();
            }
            }

    }

    @Override
    public int getItemCount() {
        return dogs.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, age, gender, breed;
        int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView_matchDogname);
            age = itemView.findViewById(R.id.textView_matchDogage);
            gender = itemView.findViewById(R.id.textView_matchDoggender);
            breed = itemView.findViewById(R.id.textView_matchDogbreed);
//            itemView.findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    deleteItem(position);
//                }
//            });
        }


        public void deleteItem(int position) {
            Dogs dog = dogs.get(position);
            auth = FirebaseAuth.getInstance();
            curUser = auth.getCurrentUser();
            db = FirebaseFirestore.getInstance();
            db.collection("dogs").document(dog.getDog_id()).delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "deleted");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "failed to delete");
                        }
                    });
            dogs.remove(position);
            notifyItemRemoved(position);
            if (curUser !=null ) {
                String userId = curUser.getUid();
                DocumentReference userRef = db.collection("users").document(userId);
                userRef.update("dogs", FieldValue.arrayRemove(dog.getDog_id()));
            }
        }
    }}