package edu.neu.madcourse.pettin.Classes;


import com.google.firebase.firestore.FieldValue;


public class GroupMessage {

    private String id;
    private String sender;
    private String message;
    private Object timestamp;

    public GroupMessage() {
    }

    public GroupMessage(String sender, String message, Object timestamp) {
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
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

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }

}