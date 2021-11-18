package com.example.donotpill;

import java.util.ArrayList;

public class Room {
    String Id;
    String title;
    int hour;
    int min;
    int boundary;
    ArrayList<Boolean> day;
    int dist;
    boolean on;

    public Room(String id,String title, int hour, int min, int boundary, ArrayList<Boolean> day, int dist, boolean on) {
        this.Id = id;
        this.title = title;
        this.hour = hour;
        this.min = min;
        this.boundary = boundary;
        this.day = day;
        this.dist = dist;
        this.on = on;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean onOff) {
        this.on = on;
    }

    public String getId() { return Id; }

    public void setId(String id) {
        Id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getBoundary() {
        return boundary;
    }

    public void setBoundary(int boundary) {
        this.boundary = boundary;
    }

    public ArrayList<Boolean> getDay() {
        return day;
    }

    public void setDay(ArrayList<Boolean> day) {
        this.day = day;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }


}
