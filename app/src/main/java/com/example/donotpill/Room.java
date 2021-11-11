package com.example.donotpill;

public class Room {
    String Id;
    int hour;
    int min;
    int boundary;
    boolean []day;
    int dist;

    public String getId() {
        return Id;
    }

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

    public boolean[] getDay() {
        return day;
    }

    public void setDay(boolean[] day) {
        this.day = day;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public Room(String id, int hour, int min, int boundary, boolean[] day, int dist) {
        Id = id;
        this.hour = hour;
        this.min = min;
        this.boundary = boundary;
        this.day = day;
        this.dist = dist;
    }
}
