package com.example.lecoin.lib;

public class User {
    public String name;
    public int status;
    public double lat;
    public double lng;
    public int[] bookmarks;

    public User(){}

    public User(String name, int status, double lat, double lng, int[] bookmarks){
        this.name = name;
        this.status = status;
        this.lat = lat;
        this.lng = lng;
        this.bookmarks = bookmarks;
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public int[] getBookmarks() {
        return bookmarks;
    }
}
