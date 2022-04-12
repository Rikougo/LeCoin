package com.example.lecoin.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lecoin.lib.AdType;

import java.util.List;

@Dao
public interface AdvertiseDao {
    @Query("SELECT * FROM advertise")
    List<Advertise> getAll();

    @Query("SELECT * FROM advertise WHERE :uid = owner")
    List<Advertise> getByOwner(int uid);

    @Query("SELECT * FROM advertise WHERE aid in (:bookmarks)")
    List<Advertise> getBookmarks(int[] bookmarks);

    @Query("SELECT * FROM advertise WHERE :type = type")
    List<Advertise> getByType(AdType type);

    @Query("SELECT * FROM advertise WHERE :aid = aid LIMIT 1")
    Advertise getByID(int aid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Advertise... ads);

    @Update
    void updateUsers(Advertise... ads);

    @Delete
    void delete(Advertise advertise);
}
