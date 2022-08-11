package edu.neu.madcourse.pettin;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import edu.neu.madcourse.pettin.Classes.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Post> posts;
    private RecyclerViewClickListener listener;
    FirebaseFirestore db;

    public PostAdapter(Context context, ArrayList<Post> postList, RecyclerViewClickListener listener) {
        this.context = context;
        this.posts = postList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Post post = posts.get(position);
        String username = post.getUsername();
        String postId = post.getPost_id();
        String title = post.getTitle();
//        String likes = post.getLikes();
        holder.title.setText(title.length() <= 20? title: title.substring(0,20) + "...");
//        holder.likes.setText(likes);
        holder.username.setText(username);
        Glide.with(context).load(post.getImage()).into(holder.image);


//         Click the username, got some bugs here
//        holder.username.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, ShowUserDetailActivity.class);
//                intent.putExtra("username", username);
//                context.startActivity(intent);
//            }
//        });


        // Click the Likes
//        holder.heart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("postId " + postId);
//                System.out.println( "collections" + db.collection("posts"));
//                DocumentReference postRef = db.collection("posts").document(postId);
//                System.out.println("postRef " + postRef);
//                postRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        Post curPost = documentSnapshot.toObject(Post.class);
//                        String likesThisPost = String.valueOf(Integer.valueOf(likes)+1);
//                        curPost.setLikes(likesThisPost);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("post adapter", "failed to like");
//                    }
//                });
//
//            }
//        });

    }



    @Override
    public int getItemCount() {
        return posts.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, likes, username;
        ImageView heart, image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
//            likes = itemView.findViewById(R.id.likes);
            username = itemView.findViewById(R.id.post_username);
//            heart = itemView.findViewById(R.id.heart);
            image = itemView.findViewById(R.id.image);
            image.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(itemView, getAdapterPosition());
        }
    }



    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        // background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Post> filteredList = new ArrayList<>();
            if(charSequence.toString().isEmpty()) {
                filteredList.addAll(posts);
            } else {
                String pattern = charSequence.toString().toLowerCase(Locale.ROOT).trim();
                // Search for title
                for(Post post: posts) {
                    if(post.getTitle().toLowerCase().contains(pattern) || (post.getLocation() != null && post.getLocation().toLowerCase().contains(pattern))) {
                        filteredList.add(post);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        // UI thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            posts.clear();
            posts.addAll((Collection<? extends Post>) filterResults.values);
            notifyDataSetChanged();
        }
    };


    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

}