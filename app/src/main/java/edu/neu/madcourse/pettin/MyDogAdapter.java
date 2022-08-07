package edu.neu.madcourse.pettin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import edu.neu.madcourse.pettin.Classes.Dogs;

public class MyDogAdapter extends RecyclerView.Adapter<MyDogAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private List<Dogs> dogs;
    FirebaseFirestore db;
    final String TAG = "My Dog Adapater";


    MyDogAdapter(Context context, List<Dogs> dogs) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dogs = dogs;

    }



    @NonNull
    @Override
    public MyDogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = layoutInflater.inflate(R.layout.my_dog_view, parent, false);
        return new MyDogAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyDogAdapter.ViewHolder viewHolder, int i) {
        Dogs dog = dogs.get(i);
        viewHolder.name.setText(dog.getName());
        viewHolder.age.setText(String.valueOf(dog.getAge()));
        viewHolder.gender.setText(" "+dog.getGender());

        viewHolder.energyLevel.setText(String.valueOf(dog.getEnergyLevel()));
        viewHolder.weight.setText(Double.toString(dog.getWeight()) + " lbs");
        viewHolder.spayed.setText("Spayed: " + dog.getSpayed());
        viewHolder.breed.setText(dog.getBreed());
        viewHolder.city.setText(dog.getLocation());


    }

    @Override
    public int getItemCount() {
        return dogs.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, age, gender, energyLevel, weight, spayed, breed, city;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView_myDogname);
            age = itemView.findViewById(R.id.textView_myDogage);
            gender = itemView.findViewById(R.id.textView_myDoggender);

            energyLevel = itemView.findViewById(R.id.textView_myDogenergyLevel);
            weight = itemView.findViewById(R.id.textView_myDogweight);
            spayed = itemView.findViewById(R.id.textView_myDogspayed);
            breed = itemView.findViewById(R.id.textView_myDogbreed);
            city = itemView.findViewById(R.id.textView_myDogloc);

        }


    public void deleteItem(int position) {
        Dogs dog = dogs.get(position);
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
    }
}}

