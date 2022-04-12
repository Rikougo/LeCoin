package com.example.lecoin.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.lecoin.lib.AdType;

import java.util.Date;

@Entity(tableName = "Advertise")
public class Advertise {
    @PrimaryKey
    public int aid;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "city")
    public String city;

    @ColumnInfo(name = "price")
    public int price;

    @ColumnInfo(name = "creation")
    public Date creation;

    @ColumnInfo(name = "type")
    public AdType type;

    @ColumnInfo(name = "owner")
    public int owner;
}
