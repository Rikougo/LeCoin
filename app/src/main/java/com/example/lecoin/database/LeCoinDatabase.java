package com.example.lecoin.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {User.class, Advertise.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class LeCoinDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract AdvertiseDao advertiseDao();
}
