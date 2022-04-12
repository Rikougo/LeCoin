package com.example.lecoin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.lecoin.lib.AdType;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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