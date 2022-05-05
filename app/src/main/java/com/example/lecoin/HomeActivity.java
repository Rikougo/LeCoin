package com.example.lecoin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.lecoin.fragment.LoginFragment;
import com.example.lecoin.fragment.SearchFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore mDB;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseFirestore.getInstance();
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
                    SwitchTo(LoginFragment.class);
                } else {
                    SwitchTo(SearchFragment.class);
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

    public void LoginUser(String mail, String password) {
        if (mAuth.getCurrentUser() != null) {
            System.err.println("User already connected");
            return;
        }

        mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    System.out.println("Logged as : " + mail);
                } else {
                    System.err.println("Error login : " + task.toString());
                }
            }
        });
    }

    public void SwitchTo(Class<? extends Fragment> fragmentClass) {
        mFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out)
                .replace(R.id.mainViewContainer, fragmentClass, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }
}