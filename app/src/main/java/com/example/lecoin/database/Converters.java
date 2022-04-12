package com.example.lecoin.database;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import com.example.lecoin.lib.AdType;

import java.util.Date;
import java.util.StringJoiner;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static AdType fromTypeID(int id) {
        try {
            return AdType.fromID(id);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @TypeConverter
    public static int fromAdType(AdType type) {
        return type.getID();
    }

    @TypeConverter
    public static int[] fromStringIntArray(String raw) {
        String[] rawArray = raw.split(";");
        int[] result = new int[rawArray.length];

        for(int i = 0; i < rawArray.length; i++) result[i] = Integer.parseInt(rawArray[i]);
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @TypeConverter
    public static String fromIntArray(int[] array) {
       StringJoiner joiner = new StringJoiner(";", "", "");

       for(int i : array) joiner.add(Integer.toString(i));

        return joiner.toString();
    }
}

