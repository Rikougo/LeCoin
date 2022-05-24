package com.example.lecoin.lib;

import androidx.annotation.Nullable;

import java.util.Date;

public class Offer {
    private String mTitle;
    private Date mCreatedAt;
    private String mDescription;

    public Offer(@Nullable String title, @Nullable Date created_at, @Nullable String description) {
        mTitle = title != null ? title : "Default title";
        mCreatedAt = created_at != null ? created_at : new Date();
        mDescription = description != null ? description : "";
    }

    public String GetTitle() { return mTitle; }
    public Date GetCreationDate() { return mCreatedAt; }
    public String GetDescription() { return mDescription; }
}