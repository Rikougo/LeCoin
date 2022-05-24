package com.example.lecoin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.example.lecoin.fragment.ListFragment;
import com.example.lecoin.fragment.LoginFragment;
import com.example.lecoin.fragment.ProfileFragment;
import com.example.lecoin.fragment.SearchFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore mDB;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getActionBar() != null) getActionBar().hide();

        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseFirestore.getInstance();
        mFragmentManager = getSupportFragmentManager();

        mAuth.signOut();
    }

    @Override
    protected void onStart() {
        super.onStart();

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch(id) {
                    case R.id.homePage:
                        System.out.println("Home");
                        SwitchTo(SearchFragment.class);
                        break;
                    case R.id.addPage:
                        System.out.println("Add");
                        SwitchTo(ListFragment.class);
                        break;
                    case R.id.accountPage:
                        System.out.println("Account");

                        if (mAuth.getCurrentUser() != null) {
                            SwitchTo(ProfileFragment.class);
                        } else {
                            SwitchTo(LoginFragment.class);
                        }
                        break;
                    case R.id.bookmarkPage:
                        System.out.println("Bookmark");
                        break;
                };

                return true;
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
                    System.err.println("Error login : " + task.getException().getMessage());
                }
            }
        });
    }

    public void SignupUser(String mail, String password, String name, String place, boolean status) {
        if (mAuth.getCurrentUser() != null) {
            System.err.println("User already connected");
            return;
        }

        mAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // récupérer l'uid de l'utilisateur créé : mAuth.getCurrentUser().getUid();
                    // posé les informations supplémentaires dans un nouveau docuent dans
                    // la collection user
                    Map<String, Object> user = new HashMap<>();
                    user.put("mail", mail);
                    user.put("name", name);
                    user.put("place", place);
                    user.put("Status", status);


                    mDB.collection("User").document(mAuth.getCurrentUser().getUid())
                            .set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    System.out.println("DocumentSnapshot successfully written!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.err.println("Error writing document");
                                }
                            });

                    System.out.println("Registered and logged as : " + mail);
                } else {
                    System.err.println("Error registering : " + task.getException().getMessage());
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

    public FirebaseAuth getAuth() {
        return mAuth;
    }

    public FirebaseFirestore getDataBase() {
        return mDB;
    }

    public Task<DocumentSnapshot> getOffer(String ID){
        return mDB.collection("Offer").document(ID).get();
    }

    public Task<com.google.firebase.firestore.QuerySnapshot> getAllOffer(){
        return mDB.collection("Offer").get();
    }
}