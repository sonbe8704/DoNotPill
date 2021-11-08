package com.example.donotpill;

import java.util.ArrayList;

public class User {
    private String Id;
    private ArrayList<String> friends;

    public User() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public User(String id, ArrayList<String> friends) {
        Id = id;
        this.friends = friends;
    }
}
