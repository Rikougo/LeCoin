package com.example.lecoin.lib;

import com.google.firebase.firestore.GeoPoint;

public class User {
    public String name;
    public boolean status;
    public GeoPoint localisation;
    public int[] bookmarks;

    public User(){}

    public User(String name, boolean status, GeoPoint localisation, int[] bookmarks){
        this.name = name;
        this.status = status;
        this.localisation = localisation;
        this.bookmarks = bookmarks;
    }

    public String getName() {
        return name;
    }

    public boolean getStatus() {
        return status;
    }

    public GeoPoint getLocalisation() {
        return localisation;
    }

    public int[] getBookmarks() {
        return bookmarks;
    }

    /*public String getPlace() {
        return place;
    }*/
}
