package edu.neu.madcourse.pettin.Classes;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;



public class PostId {
    //This PostId is used in implementing the Post timestamp
    @Exclude
    public  String PostId;

    public <T extends PostId> T withId(@NonNull final String id){
        this.PostId = id;
        return (T) this;
    }
}
