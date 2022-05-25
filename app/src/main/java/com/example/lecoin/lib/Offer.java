package com.example.lecoin.lib;

import androidx.annotation.Nullable;

import java.util.Date;
import java.util.List;

import com.google.firebase.firestore.DocumentReference;

public class Offer {
    public String title;
    public Date created_at;
    public String content;
    public boolean active;
    public List<String> images;
    public List<String> tags;
    public DocumentReference author;
    public float price;

    public Offer(){}

    public Offer(@Nullable String title, @Nullable Date created_at, @Nullable String description) {
        this.title = title != null ? title : "Default title";
        this.created_at = created_at != null ? created_at : new Date();
        this.content = description != null ? description : "Some cool default description about your cool product. Cool you.";
    }

    public Date GetCreationDate() {
        return created_at;
    }

    public String GetDescription() {
        return content;
    }

    public String GetTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
