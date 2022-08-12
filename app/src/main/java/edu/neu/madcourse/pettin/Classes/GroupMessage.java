package edu.neu.madcourse.pettin.Classes;


import com.google.firebase.firestore.FieldValue;

import java.time.LocalDateTime;


public class GroupMessage {

    private String id;
    private String sender;
    private String message;
    private String timestamp;

    public GroupMessage() {
    }

    public GroupMessage(String sender, String message) {
        this.sender = sender;
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}

