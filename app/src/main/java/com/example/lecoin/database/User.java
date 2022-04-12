package com.example.lecoin.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "User")
public class User {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "status")
    public int status;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "mail")
    public String mail;

    @ColumnInfo(name = "localisation")
    public String localisation;

    @ColumnInfo(name = "bookmarks")
    public int[] bookmarks;

    @Override
    @NonNull
    public String toString() {
        return "[USER] name: " + name + ", status: " + status + ", pwd: " + password +
                ", mail: " + mail + ", localisation: " + localisation + ".";
    }

    public static User Create(String name, String password, String mail, int status, String localisation) {
        User result = new User();
        result.name = name;
        result.password = password;
        result.mail = mail;
        result.status = status;
        result.localisation = localisation;

        return result;
    }
}
