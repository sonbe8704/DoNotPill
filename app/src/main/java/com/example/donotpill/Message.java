package com.example.donotpill;

import android.location.Address;

import com.google.firebase.Timestamp;

public class Message {
    private String Id_room;
    private String Id_tx;
    private Address address;
    private String content;
    private Timestamp timestamp;

    public Message(String id_room, String id_tx, Address address, String content, Timestamp timestamp) {
        Id_room = id_room;
        Id_tx = id_tx;
        this.address = address;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getId_room() {
        return Id_room;
    }

    public void setId_room(String id_room) {
        Id_room = id_room;
    }

    public String getId_tx() {
        return Id_tx;
    }

    public void setId_tx(String id_tx) {
        Id_tx = id_tx;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
