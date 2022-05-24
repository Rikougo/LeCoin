package com.example.lecoin.lib;

import androidx.annotation.Nullable;

import java.util.Date;

public class Offer {
    private String mTitle;
    private Date mCreatedAt;
    private String mDescription;

    public Offer(){}

    public Offer(@Nullable String title, @Nullable Date created_at, @Nullable String description) {
        mTitle = title != null ? title : "Default title";
        mCreatedAt = created_at != null ? created_at : new Date();
        mDescription = description != null ? description : "Some cool default description about your cool product. Cool you.";
    }

    public String GetTitle() { return mTitle; }
    public Date GetCreationDate() { return mCreatedAt; }
    public String GetDescription() { return mDescription; }

    public Date getmCreatedAt() {
        return mCreatedAt;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmTitle() {
        return mTitle;
    }
}
