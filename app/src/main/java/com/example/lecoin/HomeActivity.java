package com.example.lecoin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.lecoin.database.LeCoinDatabase;
import com.example.lecoin.database.User;
import com.example.lecoin.lib.AdType;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        LeCoinDatabase database = Room.databaseBuilder(
                getApplicationContext(),
                LeCoinDatabase.class,
                "Test"
        ).createFromAsset("databases/lecoin.db").allowMainThreadQueries().build();

        User u0 = User.Create("Samuel", "coolmdp", "sam@oui.non", 0, "MaisonCool");

        database.userDao().insertAll(u0);

        List<User> users = database.userDao().getAll();

        for(User user : users) {
            System.out.println(user.toString());
        }

        // populate spinner with known categories
        Spinner categorySelector = (Spinner)findViewById(R.id.CategorySelector);

        AdType[] types = AdType.values();

        ArrayAdapter<AdType> adapter = new ArrayAdapter<>(
                this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                types
        );

        categorySelector.setAdapter(adapter);
    }
}