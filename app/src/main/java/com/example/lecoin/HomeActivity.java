package com.example.lecoin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.lecoin.fragment.LoginFragment;
import com.example.lecoin.fragment.SearchFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onStart() {
        super.onStart();

        UpdateUI(mAuth.getCurrentUser());
    }

    void UpdateUI(FirebaseUser user) {
        mFragmentManager.beginTransaction()
                .replace(R.id.mainViewContainer, SearchFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();

        // STATUS BAR
        TabLayout statusBar = (TabLayout) findViewById(R.id.downNav);

        statusBar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println(tab.getText());
                if (tab.getText().toString().equals("Profile")) {
                    mFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out)
                            .replace(R.id.mainViewContainer, LoginFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                } else {
                    mFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out)
                            .replace(R.id.mainViewContainer, SearchFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}