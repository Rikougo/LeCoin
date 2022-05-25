package com.example.lecoin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.lecoin.fragment.ListFragment;
import com.example.lecoin.fragment.LoginFragment;
import com.example.lecoin.fragment.ProfileFragment;
import com.example.lecoin.fragment.SearchFragment;
import com.example.lecoin.lib.Offer;
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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.GeoPoint;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    public enum ActivityTabs {
        HOME,
        SEARCH,
        PROFILE,
        BOOKMARKS
    }

    static public class OnItemSelectedListener implements NavigationBarView.OnItemSelectedListener {
        private HomeActivity mParent;

        public OnItemSelectedListener(HomeActivity parent) {
            mParent = parent;
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();

            switch(id) {
                case R.id.homePage:
                    if (mParent.mCurrentTab == ActivityTabs.HOME) break;
                    mParent.SwitchTo(SearchFragment.class, ActivityTabs.HOME);
                    break;
                case R.id.addPage:
                    if (mParent.mCurrentTab == ActivityTabs.SEARCH) break;
                    mParent.SwitchTo(ListFragment.class, ActivityTabs.SEARCH);
                    break;
                case R.id.accountPage:
                    if (mParent.mCurrentTab == ActivityTabs.PROFILE) break;
                    if (mParent.GetAuth().getCurrentUser() != null) {
                        mParent.SwitchTo(ProfileFragment.class, ActivityTabs.PROFILE);
                    } else {
                        mParent.SwitchTo(LoginFragment.class, ActivityTabs.PROFILE);
                    }
                    break;
                case R.id.bookmarkPage:
                    if (mParent.mCurrentTab == ActivityTabs.BOOKMARKS) break;
                    break;
            }

            return true;
        }
    }

    private FirebaseAuth mAuth;
    private FirebaseFirestore mDB;
    private FragmentManager mFragmentManager;

    private ActivityTabs mCurrentTab = ActivityTabs.HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseFirestore.getInstance();
        mFragmentManager = getSupportFragmentManager();

        // debug purpose
        // mAuth.signOut();
    }

    @Override
    protected void onStart() {
        super.onStart();

        BottomNavigationView bottomNavigation = findViewById(R.id.user_navigation);

        // Handle navigation here*
        bottomNavigation.setOnItemSelectedListener(new HomeActivity.OnItemSelectedListener(this));


        // Track auth state change to handle specific actions on change
        // may be moved to specific fragment that need this
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // specific behavior if on profile page
                if (mCurrentTab == ActivityTabs.PROFILE) {
                    if (firebaseAuth.getCurrentUser() == null) {
                        SwitchTo(LoginFragment.class, null);
                    } else {
                        SwitchTo(ProfileFragment.class, null);
                    }
                }

                if (mAuth.getCurrentUser() != null) bottomNavigation.getMenu().getItem(2).setIcon(R.drawable.account);
                else bottomNavigation.getMenu().getItem(2).setIcon(R.drawable.login);
            }
        });

        RequestAllOfferByTags(new String[] { "cat", "cool"}).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                System.out.println(queryDocumentSnapshots.toObjects(Offer.class).toString());
            }
        });
        RequestAllOfferByTags(new String[] { "cat", "danger"}).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                System.out.println(queryDocumentSnapshots.toObjects(Offer.class).toString());
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
                    user.put("name", name);
                    user.put("place", place);
                    user.put("status", status);


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

    /**
     * Switch the current main fragment view to the given one.
     * Simple replace action.
     * @param fragmentClass Fragment class you want to put
     * @param tab Tabs ID of current page, if no change you can pass null
     */
    public void SwitchTo(Class<? extends Fragment> fragmentClass, ActivityTabs tab) {
        if (tab != null) mCurrentTab = tab;

        mFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out)
                .replace(R.id.mainViewContainer, fragmentClass, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    public FragmentManager GetFragmentManager() { return mFragmentManager; }

    /**
     * Request an offer with given id.
     * @param ID The ID of the wanted document
     * @return Add listener (Complete or Success or Failure) to get result of the task.
     */
    public Task<DocumentSnapshot> RequestOffer(String ID){
        return mDB.collection("Offers").document(ID).get();
    }

    /**
     * Request all offers with no filters
     * @return Add listener (Complete or Success or Failure) to get result of the task.
     */
    public Task<com.google.firebase.firestore.QuerySnapshot> RequestAllOffer() {
        return mDB.collection("Offers").whereEqualTo("active", true).get();
    }

    /**
     * Request all offers with tags filters (document must contains one of the tags to be returned)
     * @param tags collection of tags you want your documents to have
     * @return Add listener (Complete or Success or Failure) to get result of the task.
     */
    public Task<com.google.firebase.firestore.QuerySnapshot> RequestAllOfferByTags(String[] tags) {
        Query query = mDB.collection("Offers").whereEqualTo("active", true);

        query = query.whereArrayContainsAny("tags", Arrays.asList(tags));
        return query.get();
    }

    //lecture firebase
    public Task<DocumentSnapshot> getOffer(String ID){
        return mDB.collection("Offers").document(ID).get();
    }

    public Task<DocumentReference> PostOffer(Offer newOffer) {
        newOffer.created_at = new Date();
        newOffer.active = true;
        newOffer.author = mDB.collection("User").document(Objects.requireNonNull(mAuth.getUid()));

        return mDB.collection("Offers").add(newOffer);
    }

    public Task<com.google.firebase.firestore.QuerySnapshot> getAllOffer(){
        return mDB.collection("Offers").get();
    }

    public Task<com.google.firebase.firestore.QuerySnapshot> getAllOfferByUser(String ID){
        return mDB.collection("Offers").whereEqualTo("author", getUserRef(ID)).get();
    }

    public Task<com.google.firebase.firestore.QuerySnapshot> getAllOfferBySearch(String query){
        return mDB.collection("Offers").whereGreaterThanOrEqualTo("title", query).whereLessThanOrEqualTo("title", query+'\uf8ff').get();
    }

    public DocumentReference getUserRef(String ID){
        return mDB.collection("User").document(ID);
    }

    public Task<DocumentSnapshot> getUser(){
        return mDB.collection("User").document(Objects.requireNonNull(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())).get();
    }

    public String authMail(){
        return Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();
    }



    // --- UPDATE USER INFORMATION --- //
    public void updateMail(String newMail){
        mAuth.getCurrentUser().updateEmail(newMail).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                System.out.println("User email updated.");
            }
        });
    }

    public void updateName(String newName){
        DocumentReference updateUser = mDB.collection("User").document(Objects.requireNonNull(GetAuth().getUid()));

        updateUser
                .update("name", newName)
                .addOnSuccessListener(aVoid -> System.out.println("User name succesfully updated!"))
                .addOnFailureListener(e -> System.err.println("Error updating user name"));
    }

    public void updateStatus(boolean status){
        DocumentReference updateUser = mDB.collection("User").document(Objects.requireNonNull(GetAuth().getUid()));

        updateUser
                .update("status", status)
                .addOnSuccessListener(aVoid -> System.out.println("User status succesfully updated!"))
                .addOnFailureListener(e -> System.err.println("Error updating user status"));
    }

    public void updateBookmarks(int bookmark, boolean addRemove) {
        DocumentReference updateUser = mDB.collection("User").document(Objects.requireNonNull(GetAuth().getUid()));

        if(addRemove){
            updateUser.update("bookmarks", FieldValue.arrayUnion("bookmark"));
        }
        else{
            updateUser.update("bookmarks", FieldValue.arrayRemove("bookmark"));
        }
    }

    public void updateLocalisation(GeoPoint localisation){
        DocumentReference updateUser = mDB.collection("User").document(Objects.requireNonNull(GetAuth().getUid()));

        updateUser
                .update("localisation",  localisation)
                .addOnSuccessListener(aVoid -> System.out.println("User localisation succesfully updated!"))
                .addOnFailureListener(e -> System.err.println("Error updating user localisation"));
    }

    public FirebaseFirestore GetDatabase() { return mDB; }
    public FirebaseAuth GetAuth() { return mAuth; }
}